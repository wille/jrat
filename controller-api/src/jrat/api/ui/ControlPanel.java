package jrat.api.ui;

import jrat.api.Resources;

import javax.swing.*;

public class ControlPanel {

    /**
     * A category in the control panel tree view
     */
    public enum Category {

        SYSTEM("System", Resources.getIcon("control-system")),
        USER("Desktop", Resources.getIcon("control-desktop"));

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
