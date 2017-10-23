package jrat.controller.ui.frames;

import jrat.controller.Slave;
import oslib.OperatingSystem;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


@SuppressWarnings("serial")
public abstract class BaseFrame extends JFrame implements WindowListener {

	protected Slave slave;
	
	public BaseFrame() {
		this(null);
	}
	
	public BaseFrame(Slave slave) {
		this.slave = slave;

		super.addWindowListener(this);

		if (slave != null) {
            slave.addFrame(getClass(), this);
        }
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

    public void windowClosing(WindowEvent event) {
        if (slave != null) {
            slave.removeFrame(getClass());
        }
    }

    public void windowOpened(WindowEvent e) { }

    public void windowClosed(WindowEvent e) { }

    public void windowIconified(WindowEvent e) { }

    public void windowDeiconified(WindowEvent e) { }

    public void windowActivated(WindowEvent e) { }

    public void windowDeactivated(WindowEvent e) { }
}
