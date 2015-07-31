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
import javax.swing.JToolBar;

import jrat.api.Plugin;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnDisableEvent;
import se.jrat.controller.Globals;
import se.jrat.controller.addons.Plugins;
import se.jrat.controller.ui.components.PluginTable;

@SuppressWarnings("serial")
public class FramePlugins extends BaseFrame {

	private PluginTable table;

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

		table = new PluginTable();
		
		scrollPane.setViewportView(table);
		setLayout(new BorderLayout(0, 0));
		add(toolBar, BorderLayout.SOUTH);
		add(scrollPane);

		addPlugins();
	}

	public void reload() {
		new Thread(new Runnable() {
			public void run() {
				while (table.getPluginModel().getRowCount() > 0) {
					table.getPluginModel().removeRow(0);
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
			Plugin p = Plugins.getPlugins().get(i);
			table.getPluginModel().addRow(new Object[] { p });
		}
	}
}
