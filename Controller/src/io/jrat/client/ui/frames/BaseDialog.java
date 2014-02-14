package io.jrat.client.ui.frames;

import io.jrat.common.OperatingSystem;

import javax.swing.JDialog;


@SuppressWarnings("serial")
public abstract class BaseDialog extends JDialog {

	public BaseDialog() {

	}

	public void setVisible(boolean t) {
		super.setVisible(t);

		if (OperatingSystem.getOperatingSystem() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}

}
