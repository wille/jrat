package com.redpois0n.ui.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.StatMeterEntry;
import com.redpois0n.util.Util;


public class StatMeter {

	public List<StatMeterEntry> values = new ArrayList<StatMeterEntry>();

	public int max = 0;

	public BufferedImage generate(int w, int h) {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		int pos = 0;
		for (int i = 0; i < values.size(); i++) {
			StatMeterEntry entry = values.get(i);
			int val = entry.entry.connects;
			val = Util.getPercentFromTotal(val, max) * 2;
			pos += 30;
			g.drawImage(entry.flag.getImage(), pos - 7, h - (val) - 22, 25, 20, null);

			BufferedImage k = new BufferedImage(16, 11, BufferedImage.TYPE_INT_RGB);
			Graphics z = k.createGraphics();
			z.drawImage(entry.flag.getImage(), 0, 0, null);
			Color c = new Color(k.getRGB(16 / 2, 11 / 2));
			z.dispose();
			k.flush();
				
			g.setColor(c);
			g.drawString(entry.scountry, pos - 5, h - (val) - 30);
			g.fillRect(pos, h - (val), 10, h);			
		}
		g.dispose();
		return image;
	}
}
