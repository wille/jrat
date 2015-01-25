package se.jrat.client.webpanel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class WebPanelConnection implements Runnable {

	private WebPanelListener parent;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private BufferedReader br;
	private PrintWriter pw;
	
	public WebPanelConnection(WebPanelListener parent, Socket socket) throws Exception {
		this.parent = parent;
		this.socket = socket;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void run() {
		try {
			assert dis.readByte() == WebPanelPackets.PACKET_AUTH;
			String pass = readLine();
			
			if (!pass.equals(parent.getHashedPassword())) {
				throw new Exception("Failed to auth, got password " + pass);
			}

			byte packet = dis.readByte();

			if (packet == WebPanelPackets.PACKET_LIST) {

			}
						
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public String readLine() throws Exception {
		return br.readLine().trim();
	}
	
	public int readInt() throws Exception {
		return Integer.parseInt(readLine());
	}
}
