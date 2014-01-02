package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameSystem;

public class Packet32SystemProperties extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameSystem frame = FrameSystem.instances.get(slave);
		String key = slave.readLine();
		String value = slave.readLine();
		if (frame != null) {
			frame.model.addRow(new Object[] { key, value });
		}
	}

}
