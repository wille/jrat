package com.redpois0n.ui.renderers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Constants;
import com.redpois0n.utils.Util;


public class Meter {

	public static final int LINE_SPACE = 16;
	
	public List<Integer> values = new ArrayList<Integer>();
	public int maxram = 0;
	public Color color;
	public int mode = Constants.MODE_LINES;
	public int loc = LINE_SPACE;

	public Meter(Color color) {
		this.color = color;
	}

	public void setMode(int i) {
		mode = i;
	}

	public BufferedImage generate(int w, int h) {
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.gray.darker());
		
		if (loc-- <= 0) {
			loc = LINE_SPACE;
		}
		
		for (int p = 0; p < w; p += LINE_SPACE) {				
			g.drawLine(0, p, w, p);
			g.drawLine(p + loc, 0, p + loc, h);
		}
		
		Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
		
		int pos = 0;
		for (int i = 0; i < values.size(); i++) {
			int val = values.get(i);
			if (pos > w) {
				break;
			} else {
				pos += 2;
				g.setColor(color);
				val = Util.getPercentFromTotal(val, maxram);
				
				if (mode == Constants.MODE_LINES) {
					g.drawRect(pos, h - (val), 1, 1);
					g.setColor(transparentColor);
					g.drawRect(pos, h - val + 1, 1, h);
				} else if (mode == Constants.MODE_DOTS) {
					g.drawRect(pos, h - (val), 1, 1);
				}
			}
		}
		g.dispose();
		return image;
	}

}
