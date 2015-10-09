package io.jrat.controller.ui.panels;

import io.jrat.controller.addons.Plugins;
import io.jrat.controller.ui.components.PluginTable;
import io.jrat.controller.ui.components.TableModel;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import jrat.api.Plugin;

@SuppressWarnings("serial")
public class PanelMainPlugins extends JScrollPane {

	private PluginTable table;

	public TableModel getModel() {
		return (TableModel) table.getModel();
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
