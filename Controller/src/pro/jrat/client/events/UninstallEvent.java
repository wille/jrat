package pro.jrat.client.events;

import javax.swing.ImageIcon;

import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet36Uninstall;
import pro.jrat.client.utils.IconUtils;
import pro.jrat.client.utils.Utils;

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
			sl.addToSendQueue(new Packet36Uninstall());
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
		return Utils.yesNo("Confirm", "This will uninstall all connections, are you sure?");
	}

	@Override
	public String getIcon() {
		return "exit";
	}

}
