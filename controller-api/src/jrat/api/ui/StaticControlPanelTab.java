package jrat.api.ui;

import javax.swing.*;

/**
 * Control panel tab with a static panel
 */
public class StaticControlPanelTab extends ControlPanelItem<ClientPanel> {

    public StaticControlPanelTab(ControlPanel.Category category, String text, ImageIcon icon, ClientPanel panel) {
        super(category, text, icon, panel);
    }

    public ClientPanel getPanel() {
        return this.item;
    }
}
