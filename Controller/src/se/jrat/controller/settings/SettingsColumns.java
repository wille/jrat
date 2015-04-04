package se.jrat.controller.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import se.jrat.controller.Globals;
import se.jrat.controller.ui.Columns;


public class SettingsColumns extends AbstractStoreable {

	private static final SettingsColumns instance = new SettingsColumns();

	private final Map<String, Boolean> columns = new HashMap<String, Boolean>();

	public static SettingsColumns getGlobal() {
		return instance;
	}

	public boolean isSelected(String column) {
		Boolean v = columns.get(column);
		
		if (v == null) {
			v = true;
		}
		
		return v;
	}

	public void setColumn(String name, boolean show) {
		columns.put(name, show);
	}

	public void load() throws Exception {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
			
			reader.readLine();
			int len = Integer.parseInt(reader.readLine());
			
			for (int i = 0; i < len; i++) {
				String data = reader.readLine();

				String[] split = data.split("=");
				
				if (split.length == 2) {
					String key = split[0];
					String rawValue = split[1];
					
					columns.put(key, rawValue.equalsIgnoreCase("true"));
				}
			}
	
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (columns.size() == 0) {
			for (Columns c : Columns.values()) {
				setColumn(c.getName(), c.isDefault());
			}
		}
	}

	public void save() throws Exception {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
			
			pw.println("Column settings");
			pw.println(columns.size());
			
			for (String s : columns.keySet()) {
				pw.println(s + "=" + columns.get(s).toString());
			}
			
			pw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".columns");
	}
}
