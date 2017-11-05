package jrat.module.fs.ui;

import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;

import javax.swing.*;

public abstract class FileSubPanel extends ClientPanel {

    public FileSubPanel(Slave slave, String text, ImageIcon icon) {
        super(slave, text, icon);
    }

    /**
     * Returns the parent panel for this subpanel
     * @return
     */
    protected PanelFileSystem getParentPanel() {
        return (PanelFileSystem) slave.getPanel(PanelFileSystem.class);
    }
}
