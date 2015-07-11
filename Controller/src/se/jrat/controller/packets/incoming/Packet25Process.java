package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.frames.FrameRemoteProcess;
import se.jrat.controller.ui.panels.PanelControlRemoteProcess;

public class Packet25Process extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		try {
			FrameRemoteProcess frame = FrameRemoteProcess.INSTANCES.get(slave);
			FrameControlPanel framecp = FrameControlPanel.instances.get(slave);

			int count = dis.readByte();

			String[] data = new String[count];

			for (int i = 0; i < count; i++) {
				data[i] = slave.readLine();
			}

			if (frame != null) {
				frame.getPanel().getModel().addRow(new Object[] { data[0], data[1], data[2], data[3] });
			}

			if (framecp != null) {
				PanelControlRemoteProcess panel = (PanelControlRemoteProcess) framecp.panels.get("remote process");
				panel.getModel().addRow(new Object[] { data[0], data[1], data[2], data[3] });
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
