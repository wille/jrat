package pro.jrat.ui.panels;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import pro.jrat.plugins.Plugin;
import pro.jrat.plugins.PluginLoader;
import pro.jrat.ui.renderers.table.PluginsTableRenderer;


@SuppressWarnings("serial")
public class PanelMainPlugins extends JPanel {
	
	private JTable table;
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelMainPlugins() {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		
		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Author", "Description", "Version"
			}
		) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

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
