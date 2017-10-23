package jrat.api;

import jrat.controller.AbstractSlave;

public abstract class ControlPanelListener extends ClientMenuListener {

    public abstract void onClick(AbstractSlave slave);

    public final void onClick(AbstractSlave[] slaves) {

    }
}
