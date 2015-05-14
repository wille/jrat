package se.jrat.stub.packets.incoming;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import se.jrat.common.Sound;
import se.jrat.stub.Connection;

public class Packet83ClientDownloadSound extends AbstractIncomingPacket {

	private static DataLine.Info info;
	private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read(Connection con) throws Exception {
		int quality = Connection.instance.readInt();
		
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

		int size = Connection.instance.readInt();
		
		byte[] data = new byte[size];

		Connection.instance.getDataInputStream().readFully(data);

		line.write(data, 0, data.length);
		
	}

	public void close() {
		if (line != null) {
			line.close();
		}
	}

}
