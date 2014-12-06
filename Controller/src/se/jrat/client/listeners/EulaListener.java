package se.jrat.client.listeners;

import se.jrat.client.ui.dialogs.DialogEula;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		DialogEula frame = new DialogEula(true);
		frame.setVisible(true);
	}

}
