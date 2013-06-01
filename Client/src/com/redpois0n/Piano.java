package com.redpois0n;

import javax.sound.sampled.SourceDataLine;

public enum Piano {

	REST, A4, A4$, B4, C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$, A5;
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
	public static final int SECONDS = 2;
	private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

	Piano() {
		int n = this.ordinal();
		if (n > 0) {
			double exp = ((double) n - 1) / 12d;
			double f = 440d * Math.pow(2d, exp);
			for (int i = 0; i < sin.length; i++) {
				double period = (double) SAMPLE_RATE / f;
				double angle = 2.0 * Math.PI * i / period;
				sin[i] = (byte) (Math.sin(angle) * 127f);
			}
		}
	}

	public byte[] data() {
		return sin;
	}

	public static void play(SourceDataLine line, Piano note, int ms) {
		ms = Math.min(ms, Piano.SECONDS * 1000);
		int length = Piano.SAMPLE_RATE * ms / 1000;
		line.write(note.data(), 0, length);
	}
}