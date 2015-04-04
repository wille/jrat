package se.jrat.controller.listeners;

import se.jrat.controller.ui.dialogs.DialogEula;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		DialogEula frame = new DialogEula(true);
		frame.setVisible(true);
	}

}
