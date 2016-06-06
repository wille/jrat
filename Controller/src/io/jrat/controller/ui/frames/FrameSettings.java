package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.common.Version;
import io.jrat.controller.Constants;
import io.jrat.controller.ErrorDialog;
import io.jrat.controller.listeners.Performable;
import io.jrat.controller.net.WebRequest;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.StatisticsCountry;
import io.jrat.controller.ui.dialogs.DialogAbout;
import io.jrat.controller.ui.dialogs.DialogEula;
import io.jrat.controller.ui.panels.PanelSettingsFlags;
import io.jrat.controller.ui.panels.PanelSettingsMain;
import io.jrat.controller.ui.panels.PanelSettingsProxy;
import io.jrat.controller.ui.panels.PanelSettingsStats;
import io.jrat.controller.ui.panels.PanelSettingsTheme;
import io.jrat.controller.ui.renderers.JTreeIconsRenderer;
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


@SuppressWarnings("serial")
public class FrameSettings extends BaseFrame {

	private JPanel contentPane;
	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	public HashMap<String, Performable> actions = new HashMap<String, Performable>();
	private JPanel panel;

	public FrameSettings() {
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
					if (isPanel(path)) {
						panel.removeAll();
						panel.add(panels.get(path.toLowerCase()));
						panel.revalidate();
						panel.repaint();
					} else {
						perform(path);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
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

		renderer.getIconMap().put("main", IconUtils.getIcon("tab-settings"));
		renderer.getIconMap().put("themes", IconUtils.getIcon("themes"));
		renderer.getIconMap().put("flags", IconUtils.getIcon("/flags/unknown.png", false));
		renderer.getIconMap().put("stats", IconUtils.getIcon("statistics"));
		renderer.getIconMap().put("eula", IconUtils.getIcon("gavel"));
		renderer.getIconMap().put("changelog", IconUtils.getIcon("changelog"));
		renderer.getIconMap().put("about", IconUtils.getIcon("info"));
		renderer.getIconMap().put("proxy", IconUtils.getIcon("server"));

		tree.setCellRenderer(renderer);
		reload();
		panel.add(panels.get("main"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(tree, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE).addGap(10).addComponent(panel, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tree, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE).addComponent(panel, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE));
		contentPane.setLayout(gl_contentPane);
	}

	public void reload() {
		panels.clear();
		panels.put("main", new PanelSettingsMain());
		panels.put("themes", new PanelSettingsTheme(this));
		panels.put("flags", new PanelSettingsFlags());
		panels.put("stats", new PanelSettingsStats());
		panels.put("proxy", new PanelSettingsProxy());

		actions.clear();
		actions.put("eula", new Performable() {
			@Override
			public void perform() {
				DialogEula frame = new DialogEula(true);
				frame.setVisible(true);
			}
		});
		actions.put("changelog", new Performable() {
			@Override
			public void perform() {
				try {
					FrameChangelog frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), Version.getVersion());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					ErrorDialog.create(e);
				}				
			}
			
		});
		actions.put("about", new Performable() {
			@Override
			public void perform() {
				new DialogAbout().setVisible(true);
			}		
		});
	}

	public void save() {
		PanelSettingsMain main = (PanelSettingsMain) panels.get("main");
		PanelSettingsFlags flags = (PanelSettingsFlags) panels.get("flags");
		PanelSettingsTheme themes = (PanelSettingsTheme) panels.get("themes");
		PanelSettingsStats stats = (PanelSettingsStats) panels.get("stats");
		PanelSettingsProxy proxy = (PanelSettingsProxy) panels.get("proxy");

		Settings.getGlobal().set(Settings.KEY_USE_TRAY_ICON, main.useTrayIcon());
		Settings.getGlobal().set("stats", stats.trackStats());
		Settings.getGlobal().set("remotescreenstartup", main.useAutoScreen());
		Settings.getGlobal().set("askurl", main.askOnConnect());
		Settings.getGlobal().set("max", main.getMaximum());
		Settings.getGlobal().set("geoip", flags.useWeb());
		Settings.getGlobal().set("proxy", proxy.useProxy());
		Settings.getGlobal().set("proxyhost", proxy.getHost());
		Settings.getGlobal().set("proxyport", proxy.getPort());
		Settings.getGlobal().set("proxysocks", proxy.useSocks());

		Settings.getGlobal().set("theme", themes.getTheme());

		if (!stats.trackStats()) {
			StatisticsCountry.getGlobal().getList().clear();
		}
	}

	public boolean isPanel(String str) {
		return panels.containsKey(str.toLowerCase());
	}

	public void perform(String str) {
		actions.get(str.toLowerCase()).perform();
	}

	public void addNodes(DefaultMutableTreeNode root) {
		root.add(new DefaultMutableTreeNode("Main"));
		root.add(new DefaultMutableTreeNode("Themes"));
		root.add(new DefaultMutableTreeNode("Flags"));
		root.add(new DefaultMutableTreeNode("Stats"));
		/* Hidden
		 * root.add(new DefaultMutableTreeNode("Proxy"));
		 */
		root.add(new DefaultMutableTreeNode("EULA"));
		root.add(new DefaultMutableTreeNode("Changelog"));
		root.add(new DefaultMutableTreeNode("About"));
	}
}
