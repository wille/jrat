package pro.jrat.client.ui.panels;

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
import javax.swing.LayoutStyle.ComponentPlacement;

import pro.jrat.client.extensions.PluginList;
import pro.jrat.client.extensions.StubPlugin;
import pro.jrat.client.ui.components.JCheckBoxList;
import pro.jrat.client.ui.components.JCheckBoxList.Entry;
import pro.jrat.client.ui.renderers.table.PluginsTableRenderer;

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
				if (entry.getValue().toString().equals(stubPlugin.name) && entry.isChecked()) {
					plugins.plugins.add(stubPlugin);
				}
			}
		}

		return plugins.plugins.size() == 0 ? null : plugins;
	}

	public PanelBuildPlugins() {
		list = new PluginList();
		JScrollPane scrollPane = new JScrollPane();

		JButton btnAddServerPlugin = new JButton("Add server plugin (Not client)");
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
		btnAddServerPlugin.setIcon(new ImageIcon(PanelBuildPlugins.class.getResource("/icons/plugin_add.png")));

		chckbxDoNotLoad = new JCheckBox("Do not load class");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(10).addComponent(chckbxDoNotLoad).addPreferredGap(ComponentPlacement.RELATED, 120, Short.MAX_VALUE).addComponent(btnAddServerPlugin)).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnAddServerPlugin).addComponent(chckbxDoNotLoad)).addContainerGap(20, Short.MAX_VALUE)));

		File[] files = new File("plugins/stubs/").listFiles();

		Object[] obj = new Object[] {};

		if (files != null) {
			obj = new Object[files.length];

			for (int i = 0; i < files.length; i++) {
				obj[i] = files[i].getAbsolutePath();
				StubPlugin p = new StubPlugin(files[i].getAbsolutePath(), false);
				list.plugins.add(p);
			}
		}

		table = new JCheckBoxList(obj);
		model = ((JCheckBoxList.CheckBoxListModel) table.getModel());
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
}
