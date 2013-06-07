package com.redpois0n.threads;

import com.redpois0n.ui.frames.FramePerformance;
import com.redpois0n.utils.Util;

public class ThreadLocalRAM extends Thread {

	public void run() {
		try {
			Thread.sleep(100L);
			while (FramePerformance.instance != null) {		
				FramePerformance frame = FramePerformance.instance;
				Runtime rt = Runtime.getRuntime();
				long MB = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
				long MAX = rt.totalMemory() / 1024L / 1024L;

				frame.ramMeter.values.add(0, Integer.parseInt(MB + ""));
				if (Integer.parseInt(MB + "") > frame.ramMeter.maxram) {
					frame.ramMeter.maxram = Integer.parseInt(MAX + "");
				}
				frame.panel.image = frame.ramMeter.generate(frame.panel.getWidth(), frame.panel.getHeight());
				frame.panel.paintComponent(frame.panel.getGraphics());
				frame.barRAM.setValue(Util.getPercentFromTotal((int) MB, (int) MAX));
				frame.lblMaxRam.setText("Max ram: " + MAX + " mb");
				frame.lblUsedRam.setText("Used ram: " + MB + " mb");
				Thread.sleep(100L);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
