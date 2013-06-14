package com.redpois0n.events;

import javax.swing.ImageIcon;

import com.redpois0n.Slave;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.utils.IconUtils;
import com.redpois0n.utils.Util;


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
		sl.addToSendQueue(new PacketBuilder(OutgoingHeader.VISIT_URL, url));
	}

	@Override
	public String toString() {
		return "Open Website";
	}

	@Override
	public boolean add() {
		String result = Util.showDialog("Visit URL", "Input URL to visit");
		
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
