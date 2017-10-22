package io.jrat.controller.listeners;

import java.awt.Color;

public interface ExtensionInstallerListener {

	void status(Color color, String message, int current, int total);

}
