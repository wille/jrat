package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameControlPanel;
import pro.jrat.client.ui.panels.PanelControlHostsFile;

public class Packet38HostFile extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String host = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlHostsFile panel = (PanelControlHostsFile) frame.panels.get("hosts file");
			panel.getPane().setText(host);
		}
	}

}
