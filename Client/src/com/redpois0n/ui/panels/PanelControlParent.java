package com.redpois0n.ui.panels;

import javax.swing.JPanel;

import com.redpois0n.Slave;


@SuppressWarnings("serial")
public abstract class PanelControlParent extends JPanel {

	public Slave slave;

	public PanelControlParent(Slave slave) {
		setSize(600, 350);
		this.slave = slave;
	}

}
