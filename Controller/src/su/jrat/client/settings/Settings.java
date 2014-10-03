package su.jrat.client.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import su.jrat.client.Globals;


public class Settings extends AbstractSettings {

	private static final Settings instance = new Settings();

	private transient Map<String, Object> settings = new HashMap<String, Object>();

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
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
			
			reader.readLine();
			int len = Integer.parseInt(reader.readLine());
			
			for (int i = 0; i < len; i++) {
				String data = reader.readLine();
				String[] split = data.split("=");
				String key = split[0];
				String rawValue = split[1];
				
				Object value = null;
				
				try {
					value = Integer.parseInt(rawValue);
				} catch (Exception ex) { }
				
				if (value == null) {
					value = rawValue;
				}
				
				settings.put(key, value);
			}
	
			reader.close();
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
			
			pw.println("jRAT settings");
			pw.println(settings.size());
			
			for (String s : settings.keySet()) {
				pw.println(s + "=" + settings.get(s).toString());
			}
			
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addDefault() {
		setVal("bip", "127.0.0.1");
		setVal("bport", 1336);
		setVal("bid", "Name");
		setVal("bkey", "enckey");
		setVal("bpass", "pass");
		setVal("bcrypt", true);
		setVal("brecat", 10);
		setVal("traynote", true);
		setVal("soundondc", false);
		setVal("soundonc", false);
		setVal("stats", true);
		setVal("jarname", "File");
		setVal("autologin", false);
		setVal("remotescreenstartup", false);
		setVal("askurl", true);
		setVal("max", -1);
		setVal("geoip", true);
		setVal("encryption", true);
		setVal("proxy", false);
		setVal("proxyhost", "127.0.0.1");
		setVal("proxyport", 9050);
		setVal("proxysocks", true);
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".settings");
	}
}
