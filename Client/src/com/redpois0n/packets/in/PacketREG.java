package com.redpois0n.packets.in;

import java.io.DataInputStream;

import javax.swing.ImageIcon;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteRegistry;


public class PacketREG extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int count = slave.readInt();
		String[] args = new String[count];
		for (int i = 0; i < count; i++) {
			args[i] = slave.readLine();
		}
		
		if (args[0].length() == 0) {
			return;
		}
		
		FrameRemoteRegistry frame = FrameRemoteRegistry.instances.get(slave);
		
		if (frame != null) {
			if (args.length == 3) {
				ImageIcon icon;
				if (args[1].equalsIgnoreCase("REG_SZ")) {
					icon = frame.regsz;
				} else {
					icon = frame.reg01;
				}
				frame.getModel().addRow(new Object[] { icon, args[0], args[2], args[1] });
			} else {
				int toInsert = 0;
				if (frame.getModel().getRowCount() > 0) {
					for (int i = 0; i < frame.getModel().getRowCount(); i++) {
						if (frame.getModel().getValueAt(i, 0).toString().toLowerCase().contains("folder")) {
							toInsert = i;
						} else {
							break;
						}
					}
				}
				frame.getModel().insertRow(toInsert, new Object[] { frame.folder, args[0] });
			}
		}	
		
	}

}
