package io.jrat.controller.listeners;

import io.jrat.controller.net.PortListener;

public abstract interface SocketComboBoxListener {

	public abstract void onChange(PortListener pl);

}
