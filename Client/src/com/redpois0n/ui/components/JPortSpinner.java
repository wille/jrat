package com.redpois0n.ui.components;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class JPortSpinner extends JSpinner {
	
	public JPortSpinner(int defaultPort) {
		super();
		super.setModel(new SpinnerNumberModel(defaultPort, 1, 65535, 1));
	}

}
