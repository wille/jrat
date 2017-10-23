package jrat.api;

import jrat.controller.AbstractSlave;

public abstract class ClientEventListener {

    /**
     * Called when a single client is selected
     * or each client if emit is not overridden
     * @param slave
     */
    public abstract void emit(AbstractSlave slave);

    /**
     * Called when multiple clients are selected
     * @param slaves
     */
    public void emit(AbstractSlave[] slaves) {
        for (AbstractSlave slave : slaves) {
            emit(slave);
        }
    }
}
