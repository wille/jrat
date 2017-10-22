package jrat.common;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ProcessData {

	private String[] data;
	private BufferedImage image;
	
	public ProcessData(String[] data) {
		this(data, null);
	}
	
	public ProcessData(String[] data, BufferedImage image) {
		this.data = data;
		this.image = image;
	}
	
	public String[] getData() {
		return this.data;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(this.image);
	}
}
