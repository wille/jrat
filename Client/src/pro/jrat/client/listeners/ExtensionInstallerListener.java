package pro.jrat.client.listeners;

import java.awt.Color;

public abstract interface ExtensionInstallerListener {

	public abstract void status(Color color, String message, int current, int total);

}
