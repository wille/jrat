package jrat.module.fs.ui;

import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;

import javax.swing.*;

public abstract class FilePreview extends ClientPanel {

    public FilePreview(Slave slave, String title, ImageIcon icon) {
        super(slave, title, icon);
    }

}
