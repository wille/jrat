package se.jrat.client.webpanel;

import java.net.ServerSocket;
import java.net.Socket;

public class WebPanelListener implements Runnable {

	private boolean running;
	
	@Override
	public void run() {
		running = true;
		try {
			ServerSocket serverSocket = new ServerSocket(1335);
			
			while (isRunning()) {
				Socket socket = serverSocket.accept();

				WebPanelConnection wpc = new WebPanelConnection(socket);
				new Thread(wpc).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}

}
