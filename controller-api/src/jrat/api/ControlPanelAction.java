package jrat.api;

import javax.swing.*;

public class ControlPanelAction extends ControlPanelItem<ControlPanelListener> {

    public ControlPanelAction(ControlPanel.Category category, String text, ImageIcon icon) {
        super(category, text, icon);
    }
}
