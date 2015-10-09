package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlServices;

import java.io.DataInputStream;


public class Packet52WindowsService extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String name = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlServices panel = (PanelControlServices) frame.panels.get("windows services");
			panel.getModel().addRow(new Object[] { name });
		}
	}

}
