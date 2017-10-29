package jrat.api;

import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanelItem;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerModule {

    protected final List<ControlPanelItem> controlPanelItems = new ArrayList<>();
    protected final List<ClientMenuItem> menuItems = new ArrayList<>();

    public abstract void init() throws Exception;

    public final List<ControlPanelItem> getControlPanelItems() {
        return controlPanelItems;
    }

    public final List<ClientMenuItem> getClientMenuItems() {
        return menuItems;
    }
}
