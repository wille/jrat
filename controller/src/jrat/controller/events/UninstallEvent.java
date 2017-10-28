package jrat.controller.events;

import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet36Uninstall;
import jrat.controller.utils.Utils;

import javax.swing.*;


public class UninstallEvent extends Event {

	public ImageIcon icon = Resources.getIcon(getIcon());

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
