package se.jrat.stub.packets.incoming;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;


public class Packet10Messagebox extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean theme = Connection.instance.readBoolean();

		if (theme) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		final int mode = Connection.instance.readInt();
		final String title = Connection.instance.readLine();
		final String message = Connection.instance.readLine();

		new Thread() {
			public void run() {
				Connection.instance.status(Constants.STATUS_DISPLAYED_MSGBOX);
				JOptionPane.showMessageDialog(null, message, title, mode);
			}
		}.start();
	}

}
