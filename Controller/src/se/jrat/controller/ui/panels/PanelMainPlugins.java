package se.jrat.controller.ui.panels;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import jrat.api.Plugin;
import se.jrat.controller.addons.Plugins;
import se.jrat.controller.ui.components.PluginTable;

@SuppressWarnings("serial")
public class PanelMainPlugins extends JScrollPane {

	private PluginTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelMainPlugins() {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table = new PluginTable();
		setViewportView(table);

		reload();
	}

	public void reload() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}

		for (int i = 0; i < Plugins.getPlugins().size(); i++) {
			Plugin p = Plugins.getPlugins().get(i);
			getModel().addRow(new Object[] { p });
		}
	}
}
