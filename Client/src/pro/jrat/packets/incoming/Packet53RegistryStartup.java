package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlRegStart;

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
