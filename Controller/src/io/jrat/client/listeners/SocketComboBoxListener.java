package io.jrat.client.listeners;

import io.jrat.client.net.PortListener;

public abstract interface SocketComboBoxListener {

	public abstract void onChange(PortListener pl);

}
