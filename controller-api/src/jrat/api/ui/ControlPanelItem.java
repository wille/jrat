package jrat.api.ui;

import javax.swing.*;

public abstract class ControlPanelItem<T> {

    public final ControlPanel.Category category;
    public final String text;
    public final ImageIcon icon;

    public final T item;

    public ControlPanelItem(ControlPanel.Category category, String text, ImageIcon icon, T item) {
        this.category = category;
        this.text = text;
        this.icon = icon;
        this.item = item;
    }
}
