package se.jrat.controller.ui.panels;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.controller.addons.Plugin;
import se.jrat.controller.addons.PluginLoader;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.renderers.table.PluginsTableRenderer;


@SuppressWarnings("serial")
public class PanelMainPlugins extends JScrollPane {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelMainPlugins() {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table = new DefaultJTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Author", "Description", "Version" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		setViewportView(table);

		reload();
	}

	public void reload() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}

		for (int i = 0; i < PluginLoader.plugins.size(); i++) {
			Plugin p = PluginLoader.plugins.get(i);
			getModel().addRow(new Object[] { p.getName(), p.getAuthor(), p.getDescription(), p.getVersion() });
		}
	}
}
