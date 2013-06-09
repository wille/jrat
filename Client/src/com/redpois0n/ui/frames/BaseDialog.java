package com.redpois0n.ui.frames;

import javax.swing.JDialog;

import com.redpois0n.common.OperatingSystem;

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
