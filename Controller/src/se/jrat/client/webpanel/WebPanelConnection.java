package se.jrat.client.webpanel;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class WebPanelConnection implements Runnable {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private BufferedReader br;
	private PrintWriter pw;
	
	public WebPanelConnection(Socket socket) throws Exception {
		this.socket = socket;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void run() {
		try {
			assert dis.readByte() == 0;
			String pass = br.readLine();
			System.out.println(pass);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
