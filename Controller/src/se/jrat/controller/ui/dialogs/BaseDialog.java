package se.jrat.controller.ui.dialogs;

import javax.swing.JDialog;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public abstract class BaseDialog extends JDialog {

	public BaseDialog() {

	}

	public void setVisible(boolean t) {
		super.setVisible(t);

		if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}

}
