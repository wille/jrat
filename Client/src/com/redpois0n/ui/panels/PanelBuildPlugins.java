package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.plugins.ExternalPlugin;
import com.redpois0n.plugins.PluginList;
import com.redpois0n.ui.renderers.table.PluginTableRenderer;

@SuppressWarnings("serial")
public class PanelBuildPlugins extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	private PluginList list;
	private JCheckBox chckbxDoNotLoad;

	public DefaultTableModel getModel() {
		return model;
	}

	public PluginList getList() {
		if (list.plugins.size() == 0) {
			return null;
		} else {
			return list;
		}
	}

	public PanelBuildPlugins() {
		list = new PluginList();
		JScrollPane scrollPane = new JScrollPane();

		JButton btnAddServerPlugin = new JButton("Add server plugin (Not client)");
		btnAddServerPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser ch = new JFileChooser();
				ch.showOpenDialog(null);
				File file = ch.getSelectedFile();
				if (file != null) {
					if (file.isFile() && file.exists() && file.getName().endsWith(".jar")) {
						ExternalPlugin p = new ExternalPlugin(file.getAbsolutePath(), !chckbxDoNotLoad.isSelected());
						list.plugins.add(p);
						model.addRow(new Object[] { p.name });
					}

				}
			}
		});
		btnAddServerPlugin.setIcon(new ImageIcon(PanelBuildPlugins.class.getResource("/icons/plugin_add.png")));

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if (row != -1) {
					String n = model.getValueAt(row, 1).toString();

					for (ExternalPlugin p : list.plugins) {
						if (p.name.equals(n)) {
							list.plugins.remove(p);
							model.removeRow(row);
							break;
						}
					}
				}
			}
		});
		btnRemove.setIcon(new ImageIcon(PanelBuildPlugins.class.getResource("/icons/plugin_delete.png")));
		
		chckbxDoNotLoad = new JCheckBox("Do not load class");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(chckbxDoNotLoad)
							.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
							.addComponent(btnRemove)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddServerPlugin))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddServerPlugin)
						.addComponent(btnRemove)
						.addComponent(chckbxDoNotLoad))
					.addContainerGap(20, Short.MAX_VALUE))
		);

		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new PluginTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Plugin Name" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
