package se.jrat.common;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public abstract class SoundWriter implements Runnable {

	public static SoundWriter instance;

	private AudioFormat format;
	private DataLine.Info info;
	private TargetDataLine line;
	public boolean running;

	public SoundWriter() {
		instance = this;
	}

	public void enable() throws Exception {
		if (line != null) {
			line.close();
		}
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
		info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine) AudioSystem.getLine(info);
		line.open();
		line.start();
	}

	public void disable() throws Exception {
		if (line != null) {
			line.close();
		}
	}

	@Override
	public void run() {
		try {
			enable();
			running = true;
			while (running) {
				byte[] data = new byte[line.getBufferSize() / 5];

				int read = line.read(data, 0, data.length);

				onRead(data, read);
			}
			disable();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public abstract void onRead(byte[] data, int read) throws Exception;

}
