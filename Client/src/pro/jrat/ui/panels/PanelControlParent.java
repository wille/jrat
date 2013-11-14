package pro.jrat.ui.panels;

import javax.swing.JPanel;

import pro.jrat.Slave;

@SuppressWarnings("serial")
public abstract class PanelControlParent extends JPanel {

	public Slave slave;

	public PanelControlParent(Slave slave) {
		setSize(600, 350);
		this.slave = slave;
	}

}
