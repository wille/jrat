package jrat.common.listeners;

public interface ConnectionListener {

    /**
     * Triggered when a socket connection is lost
     * @param ex
     */
    void onDisconnect(Exception ex);
}
