package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameControlPanel;
import pro.jrat.client.ui.panels.PanelControlActivePorts;

public class Packet51ActivePort extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String prot = slave.readLine();
		String la = slave.readLine();
		String ext = slave.readLine();
		String status = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlActivePorts panel = (PanelControlActivePorts) frame.panels.get("active ports");
			panel.getModel().addRow(new Object[] { prot, la, ext, status });
		}

	}

}
