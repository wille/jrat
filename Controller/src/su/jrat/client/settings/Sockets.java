package su.jrat.client.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.BindException;

import javax.swing.JOptionPane;

import su.jrat.client.Globals;
import su.jrat.client.net.PortListener;
import su.jrat.common.crypto.Crypto;

public class Sockets extends AbstractSettings implements Serializable {

	private static final long serialVersionUID = -4638162646507652405L;

	private static final Sockets instance = new Sockets();

	public static Sockets getGlobal() {
		return instance;
	}

	@Override
	public void load() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
		
		reader.readLine();
		int len = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < len; i++) {
			String name = reader.readLine();
			int port = Integer.parseInt(reader.readLine());
			int timeout = Integer.parseInt(reader.readLine());
			String pass = Crypto.decrypt(reader.readLine(), AbstractSettings.GLOBAL_KEY);
			
			SocketEntry se = new SocketEntry(name, port, timeout, pass);
			se.start();
		}
		
		
		reader.close();
	}

	@Override
	public void save() throws Exception {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
		
		pw.println("Sockets configuration");
		pw.println(PortListener.listeners.size());
		
		for (int i = 0; i < PortListener.listeners.size(); i++) {
			PortListener pl = PortListener.listeners.get(i);
			pw.println(pl.getName());
			pw.println(pl.getPort());
			pw.println(pl.getTimeout());
			pw.println(Crypto.encrypt(pl.getPass(), AbstractSettings.GLOBAL_KEY));
		}
		
		pw.close();
	}

	public class SocketEntry implements Serializable {

		private static final long serialVersionUID = -2000514812654090252L;

		private String pass;
		private int timeout;
		private int port;
		private String name;

		public SocketEntry(String name, int port, int timeout, String pass) {
			this.name = name;
			this.port = port;
			this.timeout = timeout;
			this.pass = pass;
		}

		public String getPass() {
			return pass;
		}

		public int getTimeout() {
			return timeout;
		}

		public int getPort() {
			return port;
		}

		public String getName() {
			return name;
		}

		public void start() {
			try {
				PortListener connection = new PortListener(name, port, timeout, pass);
				connection.start();
			} catch (BindException e) {
				JOptionPane.showMessageDialog(null, "Port " + port + " is already in use", "jRAT", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".sockets");
	}

}
