package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.redpois0n.Settings;
import com.redpois0n.Sound;
import com.redpois0n.Statistics;
import com.redpois0n.common.Version;
import com.redpois0n.listeners.AboutListener;
import com.redpois0n.listeners.ChangelogListener;
import com.redpois0n.listeners.EulaListener;
import com.redpois0n.listeners.Performable;
import com.redpois0n.ui.panels.PanelSettingsFlags;
import com.redpois0n.ui.panels.PanelSettingsMain;
import com.redpois0n.ui.panels.PanelSettingsSound;
import com.redpois0n.ui.panels.PanelSettingsStats;
import com.redpois0n.ui.panels.PanelSettingsTheme;
import com.redpois0n.ui.renderers.JTreeIconsRenderer;
import com.redpois0n.util.IconUtils;

@SuppressWarnings("serial")
public class FrameSettings extends BaseFrame {

	private JPanel contentPane;
	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	public HashMap<String, Performable> actions = new HashMap<String, Performable>();
	private JPanel panel;

	public FrameSettings() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				save();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameSettings.class.getResource("/icons/toolbox.png")));
		setTitle("Settings - " + Version.getVersion());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

		JTree tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				try {
					String path = arg0.getPath().getPath()[1].toString();
					if (panel(path)) {
						panel.removeAll();
						panel.add(panels.get(path.toLowerCase()));
						panel.revalidate();
						panel.repaint();
					} else {
						perform(path);
					}
				} catch (Exception ex) {

				}
			}
		});
		tree.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Settings") {
			{
				addNodes(this);
			}
		}));

		JTreeIconsRenderer renderer = new JTreeIconsRenderer();
		renderer.icons.put("main", IconUtils.getIcon("tab_settings", true));
		renderer.icons.put("themes", IconUtils.getIcon("themes", true));
		renderer.icons.put("editor", IconUtils.getIcon("list", true));
		renderer.icons.put("flags", IconUtils.getIcon("errorflag"));
		renderer.icons.put("stats", IconUtils.getIcon("statistics", true));
		renderer.icons.put("sound", IconUtils.getIcon("sound", true));
		renderer.icons.put("eula", IconUtils.getIcon("gavel", true));
		renderer.icons.put("changelog", IconUtils.getIcon("changelog", true));
		renderer.icons.put("about", IconUtils.getIcon("info", true));
		tree.setCellRenderer(renderer);
		reload();
		panel.add(panels.get("main"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tree, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tree, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void reload() {
		panels.clear();
		panels.put("main", new PanelSettingsMain());
		panels.put("themes", new PanelSettingsTheme(this));
		panels.put("flags", new PanelSettingsFlags());
		panels.put("stats", new PanelSettingsStats());
		panels.put("sound", new PanelSettingsSound());

		actions.clear();
		actions.put("eula", new EulaListener());
		actions.put("changelog", new ChangelogListener());
		actions.put("about", new AboutListener());
	}

	public void save() {
		PanelSettingsMain main = (PanelSettingsMain) panels.get("main");
		PanelSettingsFlags flags = (PanelSettingsFlags) panels.get("flags");
		PanelSettingsTheme themes = (PanelSettingsTheme) panels.get("themes");
		PanelSettingsStats stats = (PanelSettingsStats) panels.get("stats");
		PanelSettingsSound sound = (PanelSettingsSound) panels.get("sound");

		Settings.setVal("traynote", main.useTrayIcon());
		Settings.setVal("soundonc", sound.onConnect());
		Settings.setVal("soundondc", sound.onDisconnect());
		Settings.setVal("stats", stats.trackStats());
		Settings.setVal("remotescreenstartup", main.useAutoScreen());
		Settings.setVal("theme", themes.getTheme());
		Settings.setVal("askurl", main.askOnConnect());
		Settings.setVal("max", main.getMaximum());
		Settings.setVal("geoip", flags.useWeb());
		if (!stats.trackStats()) {
			Statistics.list.clear();
			Statistics.saveEmpty();
		}
		Settings.save();
		Settings.load();
		Sound.initialize();
	}

	public boolean panel(String str) {
		return panels.containsKey(str.toLowerCase());
	}

	public void perform(String str) {
		actions.get(str.toLowerCase()).perform();
	}
	
	public void addNodes(DefaultMutableTreeNode root) {
		root.add(new DefaultMutableTreeNode("Main"));
		root.add(new DefaultMutableTreeNode("Themes"));
		root.add(new DefaultMutableTreeNode("Editor"));
		root.add(new DefaultMutableTreeNode("Flags"));
		root.add(new DefaultMutableTreeNode("Stats"));
		root.add(new DefaultMutableTreeNode("Sound"));
		root.add(new DefaultMutableTreeNode("EULA"));
		root.add(new DefaultMutableTreeNode("Changelog"));
		root.add(new DefaultMutableTreeNode("About"));
	}
}
