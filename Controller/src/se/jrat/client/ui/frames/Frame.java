package se.jrat.client.ui.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import jrat.api.RATMenuItem;
import jrat.api.RATObject;
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
import se.jrat.client.addons.RATObjectFormat;
import se.jrat.client.events.Event;
import se.jrat.client.events.Events;
import se.jrat.client.listeners.CountryMenuItemListener;
import se.jrat.client.net.WebRequest;
import se.jrat.client.packets.outgoing.Packet11Disconnect;
import se.jrat.client.packets.outgoing.Packet18Update;
import se.jrat.client.packets.outgoing.Packet36Uninstall;
import se.jrat.client.packets.outgoing.Packet37RestartJavaProcess;
import se.jrat.client.packets.outgoing.Packet40Thumbnail;
import se.jrat.client.packets.outgoing.Packet45Reconnect;
import se.jrat.client.settings.Settings;
import se.jrat.client.ui.components.DraggableTabbedPane;
import se.jrat.client.ui.dialogs.DialogEula;
import se.jrat.client.ui.panels.PanelMainClients;
import se.jrat.client.ui.panels.PanelMainLog;
import se.jrat.client.ui.panels.PanelMainNetwork;
import se.jrat.client.ui.panels.PanelMainPlugins;
import se.jrat.client.ui.panels.PanelMainSockets;
import se.jrat.client.ui.panels.PanelMainStats;
import se.jrat.client.ui.renderers.table.PluginsTableRenderer;
import se.jrat.client.utils.FlagUtils;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.NetUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.Version;
import se.jrat.common.utils.DataUnits;
import se.jrat.common.utils.IOUtils;

@SuppressWarnings({ "serial" })
public class Frame extends BaseFrame {

	private JPanel contentPane;
	private JTable onConnectTable;
	public JTabbedPane tabbedPane;

	public DefaultTableModel log;

	public static JTable mainTable;
	public static DefaultTableModel mainModel;
	public static DefaultTableModel onConnectModel;
	public static TrayIcon trayIcon;
	public static int pingmode = Frame.PING_ICON_DOT;
	public static boolean thumbnails = false;
	public static PanelMainStats panelStats;
	public static PanelMainNetwork panelNetwork;
	public static PanelMainSockets panelSockets;
	
	public static PanelMainClients pmc;

	private JPopupMenu popupMenu;
	private JToolBar toolBar;

	public static final int PING_ICON_DOT = 0;
	public static final int PING_ICON_CIRC = 1;

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

		JCheckBoxMenuItem chckbxmntmShowToolbar = new JCheckBoxMenuItem("Show Toolbar");
		mnShow.add(chckbxmntmShowToolbar);
		chckbxmntmShowToolbar.setIcon(IconUtils.getIcon("toolbar"));

		JCheckBoxMenuItem mntmShowThumbnails = new JCheckBoxMenuItem("Show Thumbnails");
		mnShow.add(mntmShowThumbnails);
		mntmShowThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thumbnails = !thumbnails;

				if (thumbnails) {
					for (int i = 0; i < Main.connections.size(); i++) {
						AbstractSlave sl = Main.connections.get(i);
						if (sl.getThumbnail() == null) {
							if (sl instanceof Slave) {
								((Slave)sl).addToSendQueue(new Packet40Thumbnail());
							}
						} else {
							int row = Utils.getRow(sl);
							mainModel.setValueAt(sl.getThumbnail(), row, 0);
						}
					}
					mainTable.setRowHeight(100);
				} else {
					mainTable.setRowHeight(30);
					for (int i = 0; i < Main.connections.size(); i++) {
						AbstractSlave sl = Main.connections.get(i);
						int row = Utils.getRow(sl);
						mainModel.setValueAt(sl.getCountry(), row, 0);
					}
				}
			}
		});
		mntmShowThumbnails.setIcon(IconUtils.getIcon("image"));
		chckbxmntmShowToolbar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toolBar.setVisible(((JCheckBoxMenuItem) arg0.getSource()).isSelected());
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

		mnTools.addSeparator();

		JMenu mnPingIcon = new JMenu("Ping icon");
		mnLook.add(mnPingIcon);
		mnPingIcon.setIcon(IconUtils.getIcon("application-images"));

		JMenuItem mntmMeter = new JMenuItem("Meter");
		mntmMeter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pingmode = Frame.PING_ICON_DOT;
				mainTable.repaint();
			}
		});
		mntmMeter.setIcon(IconUtils.getIcon("ping0"));
		mnPingIcon.add(mntmMeter);

		JMenuItem mntmCircle = new JMenuItem("Circle");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pingmode = Frame.PING_ICON_CIRC;
				mainTable.repaint();
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
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
		});
		mntmResizeOff.setIcon(IconUtils.getIcon("application-resize"));
		mnTableResizeBehaviour.add(mntmResizeOff);

		JMenuItem mntmFit = new JMenuItem("Fit");
		mntmFit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
				mainTable.setRowHeight(h);
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

		JMenu mnServers = new JMenu("Connections");
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
				if (mainModel.getRowCount() == 0) {
					return;
				}
				String howmany = Utils.showDialog("Select X", "Select how many connections you want to select\n" + mainModel.getRowCount() + " available");
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
				if (many > mainModel.getRowCount()) {
					JOptionPane.showMessageDialog(null, "You do not have " + many + " connections", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (int i = 0; i < Main.connections.size() && i < many; i++) {
					Main.connections.get(i).setSelected(true);
				}
				mainTable.repaint();
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
				List<AbstractSlave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm restarting all connections")) {
						for (AbstractSlave sl : list) {							
							if (sl instanceof Slave) {
								try {
									((Slave)sl).addToSendQueue(new Packet37RestartJavaProcess());
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
				List<AbstractSlave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm disconnecting all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave)sl).addToSendQueue(new Packet11Disconnect());
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
				List<AbstractSlave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm reconnecting all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave)sl).addToSendQueue(new Packet45Reconnect());
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
				List<AbstractSlave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm uninstalling all connections")) {
						for (AbstractSlave sl : list) {
							if (sl instanceof Slave) {
								try {
									((Slave)sl).addToSendQueue(new Packet36Uninstall());
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
				if (mainModel.getRowCount() <= 0) {
					return;
				}
				String result = Utils.showDialog("Update from URL", "Input URL to update with");
				if (result == null) {
					return;
				}
				if (!result.startsWith("http://")) {
					JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
					return;
				}

				result = result.trim().replace(" ", "%20");

				int servers = 0;
				for (int i = 0; i < mainModel.getRowCount(); i++) {
					AbstractSlave sl = Utils.getSlave(mainModel.getValueAt(i, 3).toString());
					if (sl != null) {
						if (!sl.getVersion().equals(Version.getVersion())) {
							if (sl instanceof Slave) { 
								((Slave)sl).addToSendQueue(new Packet18Update(result));
							}
							++servers;
						}
					}
				}
				JOptionPane.showMessageDialog(null, "Updated on " + servers + " outdated connections");
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

		JMenu mnOther = new JMenu("Other");
		menuBar.add(mnOther);

		JMenuItem mntmCache = new JMenuItem("Clear cache + thumbs");
		mntmCache.setIcon(IconUtils.getIcon("clear"));
		mntmCache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlagUtils.FLAGS.clear();
				Utils.pingicons.clear();
				for (int i = 0; i < Main.connections.size(); i++) {
					AbstractSlave sl = Main.connections.get(i);
					sl.setThumbnail(null);
				}
				System.gc();
			}
		});
		mnOther.add(mntmCache);

		JMenuItem mntmReloadAllThumbnails = new JMenuItem("Reload all thumbnails");
		mntmReloadAllThumbnails.setIcon(IconUtils.getIcon("image"));
		mntmReloadAllThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < Main.connections.size(); i++) {
					AbstractSlave sl = Main.connections.get(i);
					if (sl instanceof Slave) {
						((Slave)sl).addToSendQueue(new Packet40Thumbnail());
					}
				}
			}
		});
		mnOther.add(mntmReloadAllThumbnails);

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

		JMenuItem mntmDonate = new JMenuItem("Donate");
		mntmDonate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("http://redpois0n.com/donate.php"));
				} catch (Exception ex) {
				}
			}
		});
		mntmDonate.setIcon(IconUtils.getIcon("donate"));
		mnAbout.add(mntmDonate);

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);

		panelStats = new PanelMainStats();
		panelNetwork = new PanelMainNetwork();

		tabbedPane = new DraggableTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				panelStats.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Statistics"));
				panelNetwork.setActive(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).equals("Network Usage"));
			}
		});

		pmc = new PanelMainClients();
		tabbedPane.addTab("Clients", IconUtils.getIcon("tab-main", true), pmc, null);

		popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {

			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				try {
					for (int i = 0; i < popupMenu.getComponents().length; i++) {
						Component child = popupMenu.getComponents()[i];
						if (child instanceof JMenuItem) {
							JMenuItem item = (JMenuItem) child;
							if (item.getText().startsWith("Stub V: ")) {
								popupMenu.remove(child);
								popupMenu.remove(i - 1);
								popupMenu.remove(i - 2);
							} else if (item.getText().startsWith("Keylogger") || item.getText().equals("Offline logs")) {
								item.setEnabled(true);
								if (item.getText().startsWith("Keylogger")) {
									item.setText("Keylogger");
								}
							}
						}
					}
				} catch (Exception ex) {

				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				try {
					List<AbstractSlave> list = Utils.getSlaves();
					if (list.size() == 1) {
						JMenuItem item = new JMenuItem("Stub V: " + list.get(0).getVersion());
						JMenuItem item2 = new JMenuItem("Country: " + list.get(0).getCountry().toUpperCase());

						item2.addActionListener(new CountryMenuItemListener());

						item2.setIcon(list.get(0).getFlag());
						item.setForeground(list.get(0).isUpToDate() ? Color.black : Color.red);
						item.setIcon(list.get(0).isUpToDate() ? IconUtils.getIcon("enabled") : IconUtils.getIcon("warning"));

						popupMenu.addSeparator();
						popupMenu.add(item2);
						popupMenu.add(item);
					}
					for (int i = 0; i < popupMenu.getComponents().length; i++) {
						Component child = popupMenu.getComponents()[i];
						if (child instanceof JMenuItem) {
							JMenuItem item = (JMenuItem) child;
							if (item.getText().equals("Flag/Unflag")) {
								item.setIcon(FlagUtils.getRandomFlag());
							}
						}
					}
					mainTable.repaint();
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		});
		

		JPanel panel = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 602, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 293, Short.MAX_VALUE));
		panel.setLayout(gl_panel);

		tabbedPane.addTab("Statistics", IconUtils.getIcon("statistics", true), new JScrollPane(panelStats), null);
		
		tabbedPane.addTab("Network Usage", IconUtils.getIcon("network"), panelNetwork, null);

		JPanel panel_onconnect = new JPanel();
		tabbedPane.addTab("On Connect", IconUtils.getIcon("calendar", true), panel_onconnect, null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		final JComboBox<Object> boxOnConnect = new JComboBox<Object>();
		boxOnConnect.setModel(new DefaultComboBoxModel<Object>(Events.events.toArray()));

		JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Events.add(boxOnConnect.getSelectedItem().toString());
			}
		});

		btnAdd.setIcon(IconUtils.getIcon("calendar-add"));

		final JButton btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("Delete selected event");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = onConnectModel.getValueAt(onConnectTable.getSelectedRow(), 1).toString();
				if (name != null) {
					Events.remove(name);
					onConnectModel.removeRow(onConnectTable.getSelectedRow());
				}
			}
		});
		btnDelete.setIcon(IconUtils.getIcon("calendar-remove"));

		JButton btnPerform = new JButton("Perform");
		btnPerform.setToolTipText("Perform selected event on all connections");
		btnPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = onConnectModel.getValueAt(onConnectTable.getSelectedRow(), 1).toString();
				if (name != null) {
					Event event = Events.getByName(name);
					if (event != null) {
						for (AbstractSlave sl : Main.connections) {
							event.perform(sl);
						}
					}
				}
			}
		});
		btnPerform.setIcon(IconUtils.getIcon("calendar-perform"));

		JButton btnEdit = new JButton("Edit");
		btnEdit.setToolTipText("Edit selected event");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = onConnectModel.getValueAt(onConnectTable.getSelectedRow(), 1).toString();
				if (name != null) {
					Event event = Events.getByName(name);
					if (event != null) {
						if (event.add()) {
							onConnectModel.removeRow(onConnectTable.getSelectedRow());
							onConnectModel.addRow(event.getDisplayData());
						}
					}
				}
			}
		});
		btnEdit.setIcon(IconUtils.getIcon("calendar-edit.png"));

		GroupLayout gl_panel_onconnect = new GroupLayout(panel_onconnect);
		gl_panel_onconnect.setHorizontalGroup(gl_panel_onconnect.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_onconnect.createSequentialGroup().addContainerGap(56, Short.MAX_VALUE).addComponent(btnEdit).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnPerform).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDelete).addPreferredGap(ComponentPlacement.RELATED).addComponent(boxOnConnect, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE).addContainerGap()).addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE));
		gl_panel_onconnect.setVerticalGroup(gl_panel_onconnect.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_onconnect.createSequentialGroup().addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_onconnect.createParallelGroup(Alignment.BASELINE).addComponent(btnAdd).addComponent(boxOnConnect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnDelete).addComponent(btnPerform, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addComponent(btnEdit)).addContainerGap()));

		onConnectTable = new JTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		onConnectTable.setModel(onConnectModel = new DefaultTableModel(new Object[][] {}, new String[] { " ", "Name", "Type", "", "" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});

		onConnectTable.getTableHeader().setReorderingAllowed(false);
		onConnectTable.getColumnModel().getColumn(0).setPreferredWidth(27);
		onConnectTable.getColumnModel().getColumn(1).setPreferredWidth(133);
		onConnectTable.getColumnModel().getColumn(2).setPreferredWidth(129);
		onConnectTable.getColumnModel().getColumn(3).setPreferredWidth(140);
		onConnectTable.getColumnModel().getColumn(4).setPreferredWidth(175);
		onConnectTable.setRowHeight(20);
		scrollPane_1.setViewportView(onConnectTable);
		panel_onconnect.setLayout(gl_panel_onconnect);

		for (Plugin plugin : PluginLoader.plugins) {
			if (plugin.getItems() != null && plugin.getItems().size() > 0) {
				popupMenu.addSeparator();
				break;
			}
		}
		for (Plugin plugin : PluginLoader.plugins) {
			if (plugin.getItems() != null && plugin.getItems().size() > 0) {
				for (final RATMenuItem en : plugin.getItems()) {
					JMenuItem item = en.getItem();

					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							List<AbstractSlave> list = Utils.getSlaves();
							List<RATObject> servers = new ArrayList<RATObject>();
							for (int i = 0; i < list.size(); i++) {
								servers.add(RATObjectFormat.format(list.get(i)));
							}

							en.getListener().onClick(servers);
						}
					});
					popupMenu.add(item);
				}
			}
		}

		panelSockets = new PanelMainSockets();
		
		tabbedPane.addTab("Sockets", IconUtils.getIcon("sockets"), panelSockets, null);
		tabbedPane.addTab("Log", IconUtils.getIcon("log"), PanelMainLog.getInstance(), null);
		tabbedPane.addTab("Plugins", IconUtils.getIcon("plugin"), PanelMainPlugins.getInstance(), null);

		toolBar = new JToolBar();
		toolBar.setVisible(false);

		for (int i = 0; i < popupMenu.getComponents().length; i++) {
			Component component = popupMenu.getComponents()[i];

			if (component instanceof JMenuItem && !(component instanceof JMenu)) {
				JMenuItem item = (JMenuItem) component;

				JButton button = new JButton();
				button.setIcon(item.getIcon());
				button.setToolTipText(item.getText());

				for (int i1 = 0; i1 < item.getActionListeners().length; i1++) {
					button.addActionListener(item.getActionListeners()[i1]);
				}

				toolBar.add(button);
			} else if (component instanceof JSeparator) {
				toolBar.addSeparator();
			}
		}

		toolBar.setFloatable(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(toolBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)).addGap(0)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE).addGap(0)));
		contentPane.setLayout(gl_contentPane);

		reloadPlugins();
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

		PanelMainPlugins.getInstance().reload();
	}

	public void unselectAll() {
		for (int i = 0; i < Main.connections.size(); i++) {
			Main.connections.get(i).setSelected(false);
		}
		mainTable.repaint();
	}

	public void selectAll() {
		for (int i = 0; i < Main.connections.size(); i++) {
			Main.connections.get(i).setSelected(true);
		}
		mainTable.repaint();
	}
}