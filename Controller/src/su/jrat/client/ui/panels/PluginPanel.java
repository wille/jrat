package su.jrat.client.ui.panels;

import javax.swing.JPanel;

import su.jrat.client.IRemoveable;

@SuppressWarnings("serial")
public class PluginPanel extends JPanel {

	private int index;
	private IRemoveable removeable;
	
	public PluginPanel(IRemoveable removeable) {
		this.removeable = removeable;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}

}
