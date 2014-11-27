package io.jrat.client.events;

import io.jrat.client.AbstractSlave;
import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet14VisitURL;
import io.jrat.client.utils.IconUtils;
import io.jrat.client.utils.Utils;

import javax.swing.ImageIcon;


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
	public void perform(AbstractSlave sl) {
		if (sl instanceof Slave) {
			((Slave)sl).addToSendQueue(new Packet14VisitURL(url));
		}
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
