package jrat.api.ui;

import javax.swing.*;

public abstract class ControlPanelItem<T> {

    public final ControlPanel.Category category;
    public final String text;
    public final ImageIcon icon;

    private T action;

    public ControlPanelItem(ControlPanel.Category category, String text, ImageIcon icon) {
        this.category = category;
        this.text = text;
        this.icon = icon;
    }

    public void setAction(T action) {
        this.action = action;
    }

    public T getAction() {
        return this.action;
    }
}
