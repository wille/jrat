package com.redpois0n.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.io.Files;
import com.redpois0n.net.PortListener;

public class Sockets extends AbstractSettings implements Serializable {

	private static final long serialVersionUID = -4638162646507652405L;
	
	private static final Sockets instance = new Sockets();

	public static Sockets getGlobal() {
		return instance;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFile()));
		List<SocketEntry> list = (ArrayList<SocketEntry>) in.readObject();

		for (SocketEntry socket : list) {
			socket.start();
		}

		in.close();
	}

	@Override
	public void save() throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFile()));
		List<SocketEntry> list = new ArrayList<SocketEntry>();

		for (int i = 0; i < PortListener.servers.size(); i++) {
			PortListener connection = PortListener.servers.get(i);
			SocketEntry save = new SocketEntry(connection.getName(), connection.getServer().getLocalPort(), connection.getTimeout(), connection.getKey(), connection.getPass());
			list.add(save);
		}

		out.writeObject(list);
		out.close();

	}

	public class SocketEntry implements Serializable {

		private static final long serialVersionUID = -2000514812654090252L;

		private String key;
		private String pass;
		private int timeout;
		private int port;
		private String name;

		public SocketEntry(String name, int port, int timeout, String key, String pass) {
			this.name = name;
			this.port = port;
			this.timeout = timeout;
			this.key = key;
			this.pass = pass;
		}

		public String getKey() {
			return key;
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
				PortListener connection = new PortListener(name, port, timeout, key, pass);
				connection.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public File getFile() {
		return new File(Files.getSettings(), ".sockets");
	}

}
