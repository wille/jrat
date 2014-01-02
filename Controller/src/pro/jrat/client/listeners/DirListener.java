package pro.jrat.client.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet41SpecialDirectory;

public class DirListener implements ActionListener {

	private Slave sl;

	public DirListener(Slave slave) {
		this.sl = slave;
	}

	public void actionPerformed(ActionEvent arg0) {
		JMenuItem item = (JMenuItem) arg0.getSource();
		sl.addToSendQueue(new Packet41SpecialDirectory(item.getText().toUpperCase()));
	}

}
