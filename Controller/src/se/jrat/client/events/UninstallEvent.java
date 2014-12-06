package se.jrat.client.events;

import javax.swing.ImageIcon;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet36Uninstall;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;


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
	public void perform(AbstractSlave sl) {
		try {
			if (sl instanceof Slave) {
				((Slave)sl).addToSendQueue(new Packet36Uninstall());
			}
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
