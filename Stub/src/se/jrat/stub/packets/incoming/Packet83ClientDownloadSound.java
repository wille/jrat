package se.jrat.stub.packets.incoming;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import se.jrat.stub.Connection;

public class Packet83ClientDownloadSound extends AbstractIncomingPacket {

	public static final AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	private static DataLine.Info info;
	private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read() throws Exception {
		if (!initialized) {
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
