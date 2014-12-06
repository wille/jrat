package se.jrat.client.listeners;

import se.jrat.client.net.PortListener;

public abstract interface SocketComboBoxListener {

	public abstract void onChange(PortListener pl);

}
