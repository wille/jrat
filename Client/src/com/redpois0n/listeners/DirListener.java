package com.redpois0n.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.redpois0n.Slave;
import com.redpois0n.packets.outgoing.Packet41SpecialDirectory;

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
