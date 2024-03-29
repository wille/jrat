package jrat.controller.settings;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import jrat.common.crypto.Crypto;
import jrat.common.crypto.KeyUtils;
import jrat.controller.exceptions.InvalidSocketTypeException;
import jrat.controller.net.PortListener;
import jrat.controller.net.ServerListener;
import jrat.controller.ui.Columns;

import javax.swing.*;
import java.io.*;
import java.net.BindException;
import java.util.HashMap;
import java.util.Map;


public class Settings extends AbstractStorable {

	public static final String KEY_HOSTS = "hosts";
	public static final String KEY_RECONNECT_RATE = "reconnect_rate";
	public static final String KEY_BUILD_ID = "build_id";
	public static final String KEY_BUILD_PASSWORD = "build_password";
	public static final String KEY_TRACK_STATISTICS = "track_statistics";
	public static final String KEY_USE_TRAY_ICON = "use_tray_icon";
	public static final String KEY_START_REMOTE_SCREEN_DIRECTLY = "start_remote_screen_directly";
	public static final String KEY_INSTALLATION_NAME = "installation_name";
	public static final String KEY_REQUEST_DIALOG = "request_dialog";
	public static final String KEY_MAXIMUM_CONNECTIONS = "maximum_connections";
	public static final String KEY_USE_COUNTRY_DB = "use_country_db";
	public static final String KEY_TRANSFER_PLUGINS = "transfer_plugins_on_connect";
	public static final String KEY_ROW_HEIGHT = "row_height";
	public static final String KEY_COLUMNS = "columns";
	public static final String KEY_SOCKETS = "sockets";
	public static final String KEY_HAS_SHOWN_EULA = "eula_shown";
	public static final String KEY_STATE_DELAY = "state_delay";

	private static final Settings instance = new Settings();

	private JsonObject settings = new JsonObject();

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
		return obj == null ? -1 : ((Number) obj).intValue();
	}

	public boolean getBoolean(String key) {
		Object obj = settings.get(key);
		return obj != null && obj.toString().equalsIgnoreCase("true");
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

	public void setBuildPassword(String password) {
        settings.put(KEY_BUILD_PASSWORD, password);
	}

	public String getBuildPassword() {
        return (String) settings.get(KEY_BUILD_PASSWORD);
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

				Map<String, Object> socketData = new HashMap<>();
				sockets.put(pl.getName(), socketData);

				socketData.put("port", pl.getPort());
				socketData.put("timeout", pl.getTimeout());
				socketData.put("type", pl.getType());
				socketData.put("pass", pl.getPass());
			}

			String[] themeargs = UIManager.getLookAndFeel().toString().split(" - ");
			String className = themeargs[1].substring(0, themeargs[1].length() - 1);

			// If theme is system default, set null
			if (className.equals(UIManager.getSystemLookAndFeelClassName())) {
				className = null;
			}

			Map args = new HashMap();
			args.put(JsonWriter.TYPE, false);
			pw.print(JsonWriter.formatJson(JsonWriter.objectToJson(settings, args)));

			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addDefault() {
		set(KEY_HOSTS, new String[0]);
		set(KEY_BUILD_ID, "Client");
		setBuildPassword("pass");
		set(KEY_RECONNECT_RATE, 15);
		set(KEY_USE_TRAY_ICON, true);
		set(KEY_TRACK_STATISTICS, true);
		set(KEY_INSTALLATION_NAME, "File");
		set(KEY_START_REMOTE_SCREEN_DIRECTLY, false); // TODO implement again
		set(KEY_REQUEST_DIALOG, true);
		set(KEY_MAXIMUM_CONNECTIONS, -1); // TODO make sure it's implemented
		set(KEY_USE_COUNTRY_DB, true);

		set(KEY_TRANSFER_PLUGINS, false);
		set(KEY_ROW_HEIGHT, 30);

		Map<String, Boolean> columns = new HashMap<>();
		set(KEY_COLUMNS, columns);

		for (Columns c : Columns.values()) {
			columns.put(c.getName(), c.isDefault());
		}

		settings.put(KEY_SOCKETS, new HashMap());

		settings.put(KEY_STATE_DELAY, 2000);
	}

	@Override
	public File getFile() {
		return new File("settings.json");
	}

	public void loadSockets() {
		Map<String, Map<String, Object>> sockets = (HashMap<String, Map<String, Object>>) Settings.getGlobal().get(KEY_SOCKETS);

		for (String key : sockets.keySet()) {
			try {
				Map<String, Object> socket = sockets.get(key);

				int port = ((Long) socket.get("port")).intValue();
				int timeout = ((Long) socket.get("timeout")).intValue();
				int type = ((Long) socket.get("type")).intValue();
				String pass = (String) socket.get("pass");

				Settings.SocketEntry se = new Settings.SocketEntry(key, port, timeout, pass, type);
				se.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class SocketEntry {

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
				if (type == SocketType.SOCKET_NORMAL) {
					ServerListener connection = new ServerListener(name, port, timeout, pass);
					connection.start();
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
		public static final int SOCKET_NORMAL = 0;
		public static final int SOCKET_WEB_PANEL = 1;
	}
}
