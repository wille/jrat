package se.jrat.client.ui.frames;

import javax.swing.JFrame;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {

	public BaseFrame() {

	}

	@Override
	public void setVisible(boolean t) {
		super.setVisible(true);

		if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
			super.pack();
		}
	}

}
