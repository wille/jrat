package se.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import se.jrat.client.Drive;
import se.jrat.client.Slave;
import se.jrat.client.io.FileSystem;
import se.jrat.client.packets.outgoing.Packet15ListFiles;
import se.jrat.client.packets.outgoing.Packet16DeleteFile;
import se.jrat.client.packets.outgoing.Packet43CreateDirectory;
import se.jrat.client.ui.components.FileTable;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;

@SuppressWarnings("serial")
public class FrameRemoteFiles extends BaseFrame {
	
	public static final Map<Slave, FrameRemoteFiles> INSTANCES = new HashMap<Slave, FrameRemoteFiles>();

	private Slave slave;
	public RemoteFileTable remoteTable;

	public FrameRemoteFiles(Slave slave) {
		this.slave = slave;
		INSTANCES.put(slave, this);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		
		setBounds(100, 100, 760, 500);
		setLayout(new BorderLayout(0, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteFilesOld.class.getResource("/icons/folder-tree.png")));

		setTitle("File manager - " + "[" + slave.formatUserString() + "] - " + slave.getIP());
				
		JSplitPane sp = new JSplitPane();
		LocalFileTable localTable = new LocalFileTable();
		sp.setLeftComponent(localTable);
		remoteTable = new RemoteFileTable();
		sp.setRightComponent(remoteTable);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", sp);
		
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void exit() {
		INSTANCES.remove(this);
	}
	
	public class LocalFileTable extends FileTable {
		
		public LocalFileTable() {
			super();
		
			driveComboBox.setRenderer(renderer);
		
			listRoots();
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
			File[] f = File.listRoots();
			for (File fi : f) {
				tableRenderer.icons.put(fi.getAbsolutePath(), IconUtils.getFileIcon(fi));
				tableModel.addRow(new Object[] { fi.getAbsolutePath(), "" });
			}
		}
	}
	
	public class RemoteFileTable extends FileTable {
		
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
