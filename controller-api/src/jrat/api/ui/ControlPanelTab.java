package jrat.api.ui;

import javax.swing.*;

/**
 * Control panel tab view
 */
public class ControlPanelTab extends ControlPanelItem<JPanel> {

    public ControlPanelTab(ControlPanel.Category category, String text, ImageIcon icon) {
        super(category, text, icon);
    }
}
