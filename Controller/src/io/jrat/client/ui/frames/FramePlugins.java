package io.jrat.client.ui.frames;

import io.jrat.client.Globals;
import io.jrat.client.addons.Plugin;
import io.jrat.client.addons.PluginLoader;
import io.jrat.client.ui.renderers.table.PluginsTableRenderer;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import jrat.api.events.OnDisableEvent;

@SuppressWarnings("serial")
public class FramePlugins extends BaseFrame {

	private JPanel contentPane;
	public JTable table;
	public DefaultTableModel model;

	public FramePlugins() {
		super();
		setTitle("Installed Plugins");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePlugins.class.getResource("/icons/plugin.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnReload = new JButton("Reload all");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reload();
			}
		});
		btnReload.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/plugin.png")));

		JButton btnFolder = new JButton("Open Plugin Folder");
		btnFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(Globals.getPluginDirectory());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnFolder.setIcon(new ImageIcon(FramePlugins.class.getResource("/icons/folder_go.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnFolder).addPreferredGap(ComponentPlacement.RELATED, 211, Short.MAX_VALUE).addComponent(btnReload)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)).addGap(2)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnFolder)).addGap(3)));

		table = new JTable();
		table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.setModel(model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Author", "Description", "Version", "Status"
			}
		));
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

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
			model.addRow(new Object[] {  p.getName(), p.getVersion(), p.getAuthor(), p.getDescription(), Plugin.getStatusString(p.getStatus()) });
		}
	}
}
