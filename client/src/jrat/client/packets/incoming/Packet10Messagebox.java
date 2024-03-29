package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Constants;

import javax.swing.*;


public class Packet10Messagebox implements IncomingPacket {

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
