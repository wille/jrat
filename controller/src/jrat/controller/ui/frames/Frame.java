package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.common.Version;
import jrat.common.utils.DataUnits;
import jrat.common.utils.IOUtils;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

		JMenuItem mntmClientSettings = new JMenuItem("Settings");
		mntmClientSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameSettings().setVisible(true);
			}
		});

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
		mntmClientSettings.setIcon(Resources.getIcon("toolbox"));
		mnMain.add(mntmClientSettings);
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

		JMenu mnKeys = new JMenu("Keys");
		mnMain.add(mnKeys);

		JMenuItem mntmImportKey = new JMenuItem("Import key");
		mnKeys.add(mntmImportKey);
		mntmImportKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);

				File file = chooser.getSelectedFile();

				if (file != null) {
					try {
						FileInputStream fis = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream(new File(Globals.getFileDirectory(), "jrat.key"));
						IOUtils.copy(fis, fos);
						fis.close();
						fos.close();

						JOptionPane.showMessageDialog(null, "Imported key, please restart", "Import key", JOptionPane.WARNING_MESSAGE);
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorDialog.create(ex);
					}
				}
			}
		});
		mntmImportKey.setIcon(Resources.getIcon("key-arrow"));

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

		JMenuItem mntmGarbageCollector = new JMenuItem("Garbage Collector");
		mntmGarbageCollector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long start = Runtime.getRuntime().freeMemory();
				System.gc();
				long end = Runtime.getRuntime().freeMemory();
				long result = end - start;
				String r = DataUnits.getAsString(result);
				JOptionPane.showMessageDialog(null, "Saved " + r + "  memory", "Garbage Collector", JOptionPane.INFORMATION_MESSAGE);
			}
		});

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
		mntmGarbageCollector.setIcon(Resources.getIcon("garbage"));
		mnTools.add(mntmGarbageCollector);

		JMenu mnServers = new JMenu("Clients");
		menuBar.add(mnServers);

		JMenuItem mntmSelectAll = new JMenuItem("Select all");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
			}
		});
		mntmSelectAll.setIcon(Resources.getIcon("select-all"));
		mnServers.add(mntmSelectAll);

		JMenuItem mntmSelectX = new JMenuItem("Select X");
		mntmSelectX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Main.connections.size() == 0) {
					return;
				}
				String howmany = Utils.showDialog("Select X", "Select how many clients you want to select\n" + Main.connections.size() + " available");
				if (howmany == null || howmany != null && howmany.length() == 0) {
					return;
				}
				int many;
				try {
					many = Integer.parseInt(howmany.trim());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (many > Main.connections.size()) {
					JOptionPane.showMessageDialog(null, "Not enough clients connected", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (int i = 0; i < Main.connections.size() && i < many; i++) {
					Main.connections.get(i).setSelected(true);
				}
				repaint();
			}
		});
		mntmSelectX.setIcon(Resources.getIcon("select-x"));
		mnServers.add(mntmSelectX);

		JMenuItem mntmUnselectAll = new JMenuItem("Unselect all");
		mntmUnselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unselectAll();
			}
		});
		mnServers.add(mntmUnselectAll);
		mnServers.addSeparator();

		JMenuItem mntmRestartAll = new JMenuItem("Restart all");
		mntmRestartAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
				List<AbstractSlave> list = panelClients.getSelectedSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm restarting all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave) sl).addToSendQueue(new Packet37RestartJavaProcess());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		mntmRestartAll.setIcon(Resources.getIcon("refresh"));
		mnServers.add(mntmRestartAll);

		JMenuItem mntmDisconnectAll = new JMenuItem("Disconnect all");
		mntmDisconnectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
				List<AbstractSlave> list = panelClients.getSelectedSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm disconnecting all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave) sl).addToSendQueue(new Packet11Disconnect());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		});

		JMenuItem mntmReconnectAll = new JMenuItem("Reconnect all");
		mntmReconnectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
				List<AbstractSlave> list = panelClients.getSelectedSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm reconnecting all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave) sl).addToSendQueue(new Packet45Reconnect());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		mntmReconnectAll.setIcon(Resources.getIcon("refresh-blue"));
		mnServers.add(mntmReconnectAll);
		mntmDisconnectAll.setIcon(Resources.getIcon("delete"));
		mnServers.add(mntmDisconnectAll);

		JMenuItem mntmUninstallAll = new JMenuItem("Uninstall all");
		mntmUninstallAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
				List<AbstractSlave> list = panelClients.getSelectedSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm uninstalling all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave) sl).addToSendQueue(new Packet36Uninstall());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
		mntmUninstallAll.setIcon(Resources.getIcon("exit"));
		mnServers.add(mntmUninstallAll);
		mnServers.addSeparator();

		JMenuItem mntmUpdateAllOutdated = new JMenuItem("Update all outdated (red)");
		mntmUpdateAllOutdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Main.connections.size() == 0) {
					return;
				}
				
				String result = Utils.showDialog("Update from URL", "Input URL to update with");
				if (result == null) {
					return;
				}
				if (!NetUtils.isURL(result)) {
					JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
					return;
				}

				result = result.trim().replace(" ", "%20");

				int clients = 0;
				for (AbstractSlave slave : Main.connections) {
					if (slave instanceof Slave && !slave.getVersion().equals(Version.getVersion())) {
						((Slave) slave).addToSendQueue(new Packet18Update(result));
						++clients;
					}

				}
				JOptionPane.showMessageDialog(null, "Updated on " + clients + " outdated connections");
			}
		});
		mntmUpdateAllOutdated.setIcon(Resources.getIcon("update"));
		mnServers.add(mntmUpdateAllOutdated);

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
				panelNetwork.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Network Usage"));
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