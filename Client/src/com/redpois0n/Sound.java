package com.redpois0n;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private static AudioClip add;
	private static AudioClip remove;

	public static void initialize() {
		if (Settings.getBoolean("soundonc")) {
			add = Applet.newAudioClip(Sound.class.getResource("/files/add.wav"));
		}
		if (Settings.getBoolean("soundondc")) {
			remove = Applet.newAudioClip(Sound.class.getResource("/files/remove.wav"));
		}
	}

	public static void playAdd() {
		if (add != null && Settings.getBoolean("soundonc")) {
			add.play();
		}
	}

	public static void playRemove() {
		if (remove != null && Settings.getBoolean("soundondc")) {
			remove.play();
		}
	}

}
