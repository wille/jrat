package se.jrat.controller.listeners;

import se.jrat.controller.net.PortListener;

public abstract interface SocketComboBoxListener {

	public abstract void onChange(PortListener pl);

}
