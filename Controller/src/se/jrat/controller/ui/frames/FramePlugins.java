package se.jrat.controller.ui.frames;

import iconlib.IconUtils;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import jrat.api.events.OnDisableEvent;
import se.jrat.controller.Globals;
import se.jrat.controller.addons.Plugin;
import se.jrat.controller.addons.PluginLoader;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.renderers.table.PluginsTableRenderer;

@SuppressWarnings("serial")
public class FramePlugins extends JFrame {

	private JTable table;
	private DefaultTableModel model;

	public FramePlugins() {
		setTitle("Installed Plugins");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePlugins.class.getResource("/icons/plugin.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 356);

		JScrollPane scrollPane = new JScrollPane();

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JButton btnFolder = new JButton("Open Plugin Folder");
		toolBar.add(btnFolder);
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(Globals.getPluginDirectory());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnFolder.setIcon(IconUtils.getIcon("folder-go"));

		JButton btnReload = new JButton("Reload all");
		toolBar.add(btnReload);
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reload();
			}
		});
		btnReload.setIcon(IconUtils.getIcon("plugin"));

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Author", "Description", "Version", "Status" }));
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(new BorderLayout(0, 0));
		add(toolBar, BorderLayout.SOUTH);
		add(scrollPane);

		addPlugins();
	}

	public void reload() {
		new Thread(new Runnable() {
			public void run() {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				for (Plugin p : PluginLoader.plugins) {
					try {
						p.getMethods().get(Plugin.ON_DISABLE).invoke(p.getInstance(), new Object[] { new OnDisableEvent() });
						p.setStatus(Plugin.STATUS_DISABLED);
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
		}).start();
	}

	public void addPlugins() {
		for (int i = 0; i < PluginLoader.plugins.size(); i++) {
			Plugin p = PluginLoader.plugins.get(i);
			model.addRow(new Object[] { p.getName(), p.getAuthor(), p.getDescription(), p.getVersion(), Plugin.getStatusString(p.getStatus()) });
		}
	}
}
