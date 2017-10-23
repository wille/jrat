package jrat.api;

import jrat.controller.AbstractSlave;

public abstract class ClientMenuListener {

    /**
     * Called when a single client is selected
     * or each client if onClick is not overridden
     * @param slave
     */
    public abstract void onClick(AbstractSlave slave);

    /**
     * Called when multiple clients are selected
     * @param slaves
     */
    public void onClick(AbstractSlave[] slaves) {
        for (AbstractSlave slave : slaves) {
            onClick(slave);
        }
    }
}
