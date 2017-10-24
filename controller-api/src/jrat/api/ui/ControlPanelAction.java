package jrat.api.ui;

import jrat.api.ClientEventListener;

import javax.swing.*;

/**
 * Clickable control panel entry that triggers an action
 */
public class ControlPanelAction extends ControlPanelItem<ClientEventListener> {

    public ControlPanelAction(ControlPanel.Category category, String text, ImageIcon icon, ClientEventListener listener) {
        super(category, text, icon, listener);
    }
}
