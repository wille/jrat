package com.redpois0n.packets;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.redpois0n.Connection;
import com.redpois0n.common.codec.Hex;


public class PacketURLSOUND extends Packet {

	@Override
	public void read(String line) throws Exception {
		String url = Connection.readLine();
		Integer times = Integer.parseInt(Connection.readLine());
		url = Hex.decode(url.substring(2, url.length()));
		URL u = new URL(url);
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(u);
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.loop(times == 0 ? 0 : times - 1);
	}

}
