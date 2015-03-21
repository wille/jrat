package se.jrat.client;

import javax.swing.ImageIcon;

import se.jrat.client.packets.outgoing.Packet35Script;
import se.jrat.client.ui.panels.PanelControlScript;
import se.jrat.client.utils.IconUtils;


public class Script {

	public static final int VB = 0;
	public static final int BAT = 1;
	public static final int JS = 2;
	public static final int HTML = 3;
	public static final int SH = 4;

	public static PanelControlScript getScript(Slave slave, int i) {
		return new PanelControlScript(slave, i);
	}

	public static String getString(int i) {
		if (i == VB) {
			return "script-vb";
		} else if (i == BAT) {
			return "script-bat";
		} else if (i == JS) {
			return "java";
		} else if (i == HTML) {
			return "script-html";
		} else if (i == SH) {
			return "script-sh";
		} else {
			return null;
		}
	}

	public static String getSendString(int i) {
		if (i == VB) {
			return "vbs";
		} else if (i == BAT) {
			return "bat";
		} else if (i == JS) {
			return "js";
		} else if (i == HTML) {
			return "html";
		} else if (i == SH) {
			return "sh";
		} else {
			return null;
		}
	}

	public static ImageIcon getIcon(int i) {
		return getIcon(getString(i));
	}

	public static ImageIcon getIcon(String str) {
		return IconUtils.getIcon(str);
	}

	public static void sendScript(Slave sl, int type, String content) {
		sl.addToSendQueue(new Packet35Script(getSendString(type), content));
	}

}
