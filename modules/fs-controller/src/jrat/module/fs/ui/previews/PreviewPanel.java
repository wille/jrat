package jrat.module.fs.ui.previews;

import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;

import javax.swing.*;

public abstract class PreviewPanel<T> extends ClientPanel {

    public PreviewPanel(Slave slave, String title, ImageIcon icon) {
        super(slave, title, icon);
    }

    /**
     * Add preview data to this preview panel
     * @param data
     */
    public abstract void addData(T data);
}
