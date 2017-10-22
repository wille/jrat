package jrat.controller;

import jrat.controller.ui.dialogs.DialogErrorDialog;

public class ErrorDialog {

	public static void create(Exception ex) {
		DialogErrorDialog frame = new DialogErrorDialog(ex);
		frame.setVisible(true);
		frame.setSize(frame.originalSize);
	}

}
