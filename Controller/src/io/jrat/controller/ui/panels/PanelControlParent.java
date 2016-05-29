package io.jrat.controller.ui.panels;

import io.jrat.controller.Slave;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class PanelControlParent extends JPanel {

	public Slave slave;

	public PanelControlParent(Slave slave) {
		setSize(600, 350);
		this.slave = slave;
	}

}
