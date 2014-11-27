package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameRawSystemInfo;

import java.io.DataInputStream;


public class Packet57RawComputerInfo extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String info = slave.readLine();

		FrameRawSystemInfo frame = FrameRawSystemInfo.instances.get(slave);

		if (frame != null) {
			frame.append(info);
		}
	}

}
