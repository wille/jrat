package jrat.controller.listeners;

import java.awt.*;

public interface ExtensionInstallerListener {

	void status(Color color, String message, int current, int total);

}
