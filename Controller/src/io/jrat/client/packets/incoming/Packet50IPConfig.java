package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlNetGateway;

import java.io.DataInputStream;


public class Packet50IPConfig extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String ipconfig = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		PanelControlNetGateway panel = null;
		if (frame != null) {
			panel = (PanelControlNetGateway) frame.panels.get("net gateway");
			panel.getPane().setText(ipconfig);
		}
	}

}