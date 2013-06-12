package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRawSystemInfo;

public class PacketRAWINFO extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRawSystemInfo frame = FrameRawSystemInfo.instances.get(slave);
		
		String line;
		
		while (!(line = slave.readLine()).equals("END")) {
			if (frame != null) {
				frame.append(line);
			}
		}
	}

}
