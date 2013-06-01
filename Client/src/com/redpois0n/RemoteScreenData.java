package com.redpois0n;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class RemoteScreenData {

	private BufferedImage bufferedImage;
	private byte[] buffer;
	private boolean createdIcon;

	public RemoteScreenData(Slave slave, int w, int h) {
		bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		createdIcon = false;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public boolean hasCreatedIcon() {
		return createdIcon;
	}

	public void setIcon() {
		createdIcon = true;
	}

}
