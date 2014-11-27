package io.jrat.client.ui.frames;

import io.jrat.common.OperatingSystem;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {

	public BaseFrame() {

	}

	@Override
	public void setVisible(boolean t) {
		super.setVisible(true);

		if (OperatingSystem.getOperatingSystem() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}

}
