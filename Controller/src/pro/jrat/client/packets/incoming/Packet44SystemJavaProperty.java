package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameControlPanel;
import pro.jrat.client.ui.panels.PanelControlJVMProperties;

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