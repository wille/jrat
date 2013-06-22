package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlJVM;


public class Packet44SystemJavaProperty extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String key = slave.readLine();
		String value = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlJVM panel = (PanelControlJVM) frame.panels.get("jvm info");
			panel.getModel().addRow(new Object[] { key, value });
		}
	}

}
