package se.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet15ListFiles;
import se.jrat.client.ui.components.FileTable;
import se.jrat.client.ui.renderers.JComboBoxIconRenderer;
import se.jrat.client.utils.IconUtils;

@SuppressWarnings("serial")
public class FrameRemoteFiles extends BaseFrame {
	
	public static final Map<Slave, FrameRemoteFiles> INSTANCES = new HashMap<Slave, FrameRemoteFiles>();

	private Slave slave;

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
		RemoteFileTable remoteTable = new RemoteFileTable();
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
			
			File[] roots = File.listRoots();
			for (File root : roots) {
				renderer.addIcon(root.getAbsolutePath().toLowerCase(), IconUtils.getFileIcon(root));
				comboModel.addElement(root.getAbsolutePath());
			}
			driveComboBox.setRenderer(renderer);
		}

		@Override
		public void onBack() {
			
		}

		@Override
		public void onReload() {
			
		}

		@Override
		public void onDelete() {
			
		}

		@Override
		public void onCreateFolder() {
			
		}

		@Override
		public void onItemClick() {
			
		}
		
	}
	
	public class RemoteFileTable extends FileTable {

		@Override
		public void onBack() {
			
		}

		@Override
		public void onReload() {
			
		}

		@Override
		public void onDelete() {
			
		}

		@Override
		public void onCreateFolder() {
			
		}

		@Override
		public void onItemClick() {
			
		}
		
	}

}
