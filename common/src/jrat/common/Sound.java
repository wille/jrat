package jrat.common;

import javax.sound.sampled.AudioFormat;

public class Sound {
		
	public static AudioFormat getFormat(int quality) {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, quality, 16, 2, 4, quality, false);
	}

}
