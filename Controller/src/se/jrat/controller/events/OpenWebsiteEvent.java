package se.jrat.controller.events;

import iconlib.IconUtils;

import javax.swing.ImageIcon;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet14VisitURL;
import se.jrat.controller.utils.NetUtils;
import se.jrat.controller.utils.Utils;


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
