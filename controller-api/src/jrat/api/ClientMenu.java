package jrat.api;

import jrat.controller.ui.frames.Frame;

import javax.swing.*;

public class ClientMenu {

    /**
     * Client menu categories
     */
    public enum Category {
        QUICK_OPEN
    }

    /**
     * Adds an item to the specified category
     * @param category
     * @param menuItem
     */
    public static void addItem(Category category, ClientMenuItem menuItem) {
        JMenuItem item = menuItem.getJMenuItem();

        JMenu menu = null;

        switch (category) {
            case QUICK_OPEN:
                menu = Frame.panelClients.mnQuickOpen;
                break;
        }

        menu.add(item);
    }
}
