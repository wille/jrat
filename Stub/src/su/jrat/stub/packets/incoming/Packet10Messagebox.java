package su.jrat.stub.packets.incoming;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import su.jrat.stub.Connection;
import su.jrat.stub.Constants;


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
