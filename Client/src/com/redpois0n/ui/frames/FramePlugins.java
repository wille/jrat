package com.redpois0n.ui.frames;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jrat.project.api.events.OnDisableEvent;

import com.redpois0n.plugins.Plugin;
import com.redpois0n.plugins.PluginLoader;
import com.redpois0n.ui.renderers.table.PluginTableRenderer;


@SuppressWarnings("serial")
public class FramePlugins extends BaseFrame {

	private JPanel contentPane;
	public JTable table;
	public DefaultTableModel model;

	public FramePlugins() {
		super();
		setTitle("Plugins");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePlugins.class.getResource("/icons/plugin.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 407, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reload();
			}
		});
		btnReload.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/plugin.png")));

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				for (int i : rows) {
					String version = table.getValueAt(i, 0).toString();
					String name = table.getValueAt(i, 1).toString();
					String author = table.getValueAt(i, 2).toString();
					String desc = table.getValueAt(i, 3).toString();

					Plugin toremove = null;

					for (Plugin p : PluginLoader.plugins) {
						if (p.getVersion().equals(version) && p.getName().equals(name) && p.getAuthor().equals(author) && p.getDescription().equals(desc)) {
							toremove = p;
							break;
						}
					}

					if (toremove != null) {
						PluginLoader.plugins.remove(toremove);
					}
				}
			}
		});
		btnDelete.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/plugin_delete.png")));

		JButton btnFolder = new JButton("Folder");
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(System.getProperty("user.dir") + File.separator + "plugins" + File.separator));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnFolder.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/plugin_go.png")));

		JButton btnInstall = new JButton("Install");
		btnInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameInstallPlugin frame = new FrameInstallPlugin();
				frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnInstall.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/plugin_go.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnFolder).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnInstall, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnReload)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)).addGap(2)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addComponent(btnFolder).addComponent(btnInstall, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)).addGap(3)));

		table = new JTable();
		table.setDefaultRenderer(Object.class, new PluginTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Version", "Plugin name", "Author", "Description" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(46);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(96);
		table.getColumnModel().getColumn(3).setPreferredWidth(138);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

		addPlugins();
	}

	public void reload() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		for (Plugin p : PluginLoader.plugins) {
			try {
				p.getMethods().get("ondisable").invoke(p.getInstance(), new Object[] { new OnDisableEvent() });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			PluginLoader.loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}
		addPlugins();
	}

	public void addPlugins() {
		for (int i = 0; i < PluginLoader.plugins.size(); i++) {
			Plugin p = PluginLoader.plugins.get(i);
			model.addRow(new Object[] { p.getVersion(), p.getName(), p.getAuthor(), p.getDescription() });
		}
	}
}
