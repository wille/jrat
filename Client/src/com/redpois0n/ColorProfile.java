package com.redpois0n;

import java.io.Serializable;

import com.redpois0n.ui.components.JColorBox;

public class ColorProfile implements Serializable {

	private static final long serialVersionUID = 7819760998736045294L;
	
	private int index;
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public static ColorProfile generate(String color) {
		ColorProfile profile = new ColorProfile();
		for (int i = 0; i < JColorBox.colors.length; i++) {
			if (color.equalsIgnoreCase(JColorBox.colors[i])) {
				profile.setIndex(i);
				break;
			}
		}
		return profile;
	}
	
}
