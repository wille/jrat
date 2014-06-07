package su.jrat.client;

import su.jrat.client.ui.dialogs.DialogErrorDialog;

public class ErrorDialog {

	public static void create(Exception ex) {
		DialogErrorDialog frame = new DialogErrorDialog(ex);
		frame.setVisible(true);
		frame.setSize(frame.originalSize);
	}

}
