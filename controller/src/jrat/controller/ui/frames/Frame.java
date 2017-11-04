package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.common.Version;
import jrat.controller.*;
import jrat.controller.packets.outgoing.*;
import jrat.controller.settings.StoreOfflineSlaves;
import jrat.controller.ui.MainView;
import jrat.controller.ui.components.DraggableTabbedPane;
import jrat.controller.ui.dialogs.DialogAbout;
import jrat.controller.ui.panels.*;
import jrat.controller.utils.BasicIconUtils;
import jrat.controller.utils.NetUtils;
import jrat.controller.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("serial")
public class Frame extends BaseFrame {

	private JTabbedPane tabbedPane;

	private int pingMode;
	private TrayIcon trayIcon;

	public static PanelMainClients panelClients;
	private PanelMainStats panelStats;
	private PanelMainNetwork panelNetwork;
	private PanelMainSockets panelSockets;
	private PanelMainLog panelLog;

	private JMenu mnPlugins;
	private JCheckBoxMenuItem chckbxmntmTransferPluginsIf;
	private JMenu mnView;
	private List<JMenuItem> configMenu;

	public Frame() {
		setIconImages(BasicIconUtils.getFrameIconList("icon"));
		setTitle(Main.formatTitle());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 450);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMain = new JMenu("Main");
		menuBar.add(mnMain);

		JMenuItem mntmAddSocket = new JMenuItem("Add Socket                ");
		mntmAddSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAddSocket frame = new FrameAddSocket();
				frame.setVisible(true);
			}
		});
		mntmAddSocket.setIcon(Resources.getIcon("socket-add"));
		mnMain.add(mntmAddSocket);

		mnMain.addSeparator();

		JMenu mnShow = new JMenu("Show");
		mnMain.add(mnShow);

		mnMain.addSeparator();

		JMenu mnServerModule = new JMenu("Stub ControllerModule");
		mnServerModule.setIcon(Resources.getIcon("bug"));
		mnMain.add(mnServerModule);
		mnMain.addSeparator();

		JMenuItem mntmBuildServer = new JMenuItem("Normal Builder");
		mntmBuildServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameBuildMinimal().setVisible(true);
			}
		});
		mntmBuildServer.setIcon(Resources.getIcon("info-button"));
		mnServerModule.add(mntmBuildServer);

		JMenuItem mntmAdvancedBuild = new JMenuItem("Advanced Builder");
		mntmAdvancedBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameBuildAdvanced frame = new FrameBuildAdvanced();
				frame.setVisible(true);
			}
		});
		mntmAdvancedBuild.setIcon(Resources.getIcon("info-button"));
		mnServerModule.add(mntmAdvancedBuild);

		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.setIcon(Resources.getIcon("info-button"));
		mntmHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NetUtils.openUrl(Constants.HOST + "/docs.php");
			}
		});
		mnMain.add(mntmHelp);

		JMenuItem mntmTwitter = new JMenuItem("Twitter");
		mntmTwitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NetUtils.openUrl("https://twitter.com/java_rat");
			}
		});
		mntmTwitter.setIcon(Resources.getIcon("twitter"));
		mnMain.add(mntmTwitter);

		mnMain.addSeparator();

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.instance.setVisible(false);
				Main.instance.dispose();
				System.exit(0);
			}
		});

		JMenuItem mntmUpdate = new JMenuItem("Update");
		mntmUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Updater.runUpdater();
			}
		});
		mntmUpdate.setIcon(Resources.getIcon("update"));
		mnMain.add(mntmUpdate);
		mntmExit.setIcon(Resources.getIcon("exit"));
		mnMain.add(mntmExit);
		
		mnView = new JMenu("View");
		
		for (final PanelMainClients view : MainView.VIEWS) {
			JMenuItem item = new JMenuItem(view.getViewName());
			item.setIcon(view.getIcon());
			
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					updateClientsView(view);
				}
			});
			
			mnView.add(item);
		}
		
		mnView.addSeparator();
		
		menuBar.add(mnView);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		JMenu mnLook = new JMenu("Look");
		mnLook.setIcon(Resources.getIcon("gui"));
		mnTools.add(mnLook);

		mnTools.addSeparator();

		JMenu mnTabPlacement = new JMenu("Tab placement");
		mnLook.add(mnTabPlacement);
		mnTabPlacement.setIcon(Resources.getIcon("application-tabs"));

		JMenuItem mntmTop = new JMenuItem("Top");
		mntmTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.TOP);
			}
		});
		mntmTop.setIcon(Resources.getIcon("ui-tab"));
		mnTabPlacement.add(mntmTop);

		JMenuItem mntmBottom = new JMenuItem("Bottom");
		mntmBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			}
		});
		mntmBottom.setIcon(Resources.getIcon("ui-tab-bottom"));
		mnTabPlacement.add(mntmBottom);

		JMenuItem mntmLeft = new JMenuItem("Left");
		mntmLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.LEFT);
			}
		});
		mntmLeft.setIcon(Resources.getIcon("ui-tab-side"));
		mnTabPlacement.add(mntmLeft);

		JMenuItem mntmRight = new JMenuItem("Right");
		mntmRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
			}
		});
		mntmRight.setIcon(Resources.getIcon("ui-tab-side-right"));
		mnTabPlacement.add(mntmRight);

		JMenuItem mntmGroups = new JMenuItem("Groups");
		mnTools.add(mntmGroups);
		mntmGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameGroups frame = new FrameGroups();
				frame.setVisible(true);
			}
		});
		mntmGroups.setIcon(Resources.getIcon("group"));
		
				JMenuItem mntmReloadAllshowThumbnails = new JMenuItem("Reload all Thumbnails");
				mnTools.add(mntmReloadAllshowThumbnails);
				mntmReloadAllshowThumbnails.setIcon(Resources.getIcon("image"));
				mntmReloadAllshowThumbnails.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						for (int i = 0; i < Main.connections.size(); i++) {
							AbstractSlave sl = Main.connections.get(i);
							if (sl instanceof Slave) {
								((Slave) sl).addToSendQueue(new Packet40Thumbnail());
							}
						}
					}
				});

		JMenuItem mntmSampleMode = new JMenuItem("Sample Mode");
		mnTools.add(mntmSampleMode);
		mntmSampleMode.setIcon(Resources.getIcon("camera"));
		mntmSampleMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Utils.yesNo("Confirm", "This will break " + Constants.NAME + " until restarted and begin a photo friendly session")) {
					SampleMode.start(false);
				}
			}
		});

		JMenu mnAbout = new JMenu("Help");
		mnAbout.setVisible(true);
		menuBar.add(mnAbout);

		JMenuItem menuItem = new JMenuItem("About " + Constants.NAME + " " + Version.getVersion());
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogAbout frame = new DialogAbout();
				frame.setVisible(true);
			}
		});

		JMenuItem mntmWhatsNewIn = new JMenuItem("Changelog");
		mntmWhatsNewIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FrameChangelog frame = new FrameChangelog();
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		mnAbout.add(mntmWhatsNewIn);

		JMenuItem mntmDebugerrorReportInfo = new JMenuItem("Debug Info");
		mntmDebugerrorReportInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameDebugInfo debug = new FrameDebugInfo();
				debug.setVisible(true);
			}
		});
		mnAbout.add(mntmDebugerrorReportInfo);
		mnAbout.addSeparator();
		menuItem.setIcon(Resources.getIcon("info"));
		mnAbout.add(menuItem);

		tabbedPane = new DraggableTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelStats.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Statistics"));
			}
		});
		

		panelClients = MainView.get("Table"); // TODO
		panelStats = new PanelMainStats();
		panelNetwork = new PanelMainNetwork();
		panelSockets = new PanelMainSockets();
		panelLog = new PanelMainLog();

		updateClientsView(panelClients);
		tabbedPane.addTab("Statistics", Resources.getIcon("statistics"), panelStats);
		tabbedPane.addTab("Network Usage", Resources.getIcon("network"), panelNetwork);
		tabbedPane.addTab("Sockets", Resources.getIcon("sockets"), panelSockets);
		tabbedPane.addTab("Log", Resources.getIcon("list"), panelLog);

		getContentPane().add(tabbedPane);
	}
	
	public void updateClientsView(PanelMainClients view) {
		tabbedPane.remove(panelClients);
		panelClients.clear();
		
		panelClients = view;
		
		for (int i = 0; i < Main.connections.size(); i++) {
			view.addSlave(Main.connections.get(i));
		}

		for (int i = 0; i < StoreOfflineSlaves.getGlobal().getList().size(); i++) {
			view.addSlave(StoreOfflineSlaves.getGlobal().getList().get(i));
		}
		
		tabbedPane.insertTab("Clients", view.getIcon(), view, null, 0);
		tabbedPane.setSelectedIndex(0);
		
		if (configMenu != null) {
			for (JMenuItem item : configMenu) {
				mnView.remove(item);
			}
		}
		
		configMenu = view.getConfigMenu();
		
		for (JMenuItem item : configMenu) {
			mnView.add(item);
		}
	}

	public void unselectAll() {
		for (int i = 0; i < Main.connections.size(); i++) {
			Main.connections.get(i).setSelected(false);
		}
		repaint();
	}

	public void selectAll() {
		for (int i = 0; i < Main.connections.size(); i++) {
			Main.connections.get(i).setSelected(true);
		}
		repaint();
	}

	public PanelMainClients getPanelClients() {
		return panelClients;
	}

	public PanelMainStats getPanelStats() {
		return panelStats;
	}

	public PanelMainNetwork getPanelNetwork() {
		return panelNetwork;
	}

	public PanelMainSockets getPanelSockets() {
		return panelSockets;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public PanelMainLog getPanelLog() {
		return panelLog;
	}

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public void setTrayIcon(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	public int getPingMode() {
		return pingMode;
	}

	public void setPingMode(int pingMode) {
		this.pingMode = pingMode;
	}
}