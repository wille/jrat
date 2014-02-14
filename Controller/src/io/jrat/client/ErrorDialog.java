package io.jrat.client;

import io.jrat.client.ui.frames.FrameErrorDialog;

public class ErrorDialog {

	public static void create(Exception ex) {
		FrameErrorDialog frame = new FrameErrorDialog(ex);
		frame.setVisible(true);
		frame.setSize(frame.originalSize);
	}

}
