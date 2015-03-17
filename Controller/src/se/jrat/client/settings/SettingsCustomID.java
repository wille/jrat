package se.jrat.client.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.Globals;


public class SettingsCustomID extends AbstractStoreable implements Serializable {

	private static final long serialVersionUID = 720261533636222207L;

	private transient List<CustomIDEntry> list = new ArrayList<CustomIDEntry>();

	private static final SettingsCustomID instance = new SettingsCustomID();

	public static SettingsCustomID getGlobal() {
		return instance;
	}

	public List<CustomIDEntry> getIDList() {
		return list;
	}

	@Override
	public void load() throws Exception {	
		list.clear();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
		
		reader.readLine();
		int len = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < len; i++) {
			String ip = reader.readLine();
			String name = reader.readLine();
			String realname = reader.readLine();	
			
			CustomIDEntry se = new CustomIDEntry(ip, name, realname);
			list.add(se);
		}
		
		
		reader.close();

	}

	@Override
	public void save() throws Exception {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
		
		pw.println("Renamed connections list");
		pw.println(list.size());
		
		for (CustomIDEntry en : list) {
			pw.println(en.getIP());
			pw.println(en.getName());
			pw.println(en.getRealName());
		}
		
		pw.close();
	}

	public CustomIDEntry findEntry(String ip) {
		for (int i = 0; i < list.size(); i++) {
			CustomIDEntry entry = list.get(i);
			if (entry.ip.equals(ip)) {
				return entry;
			}
		}
		return null;
	}

	public void remove(String ip) {
		CustomIDEntry entry = findEntry(ip);
		if (entry != null) {
			list.remove(entry);
		}
	}

	public void add(String name, String realname, String ip) {
		for (SettingsCustomID.CustomIDEntry entry : list) {
			if (entry.getIP().equals(ip)) {
				list.remove(entry);
				break;
			}
		}
		
		SettingsCustomID.CustomIDEntry entry = new SettingsCustomID.CustomIDEntry(ip, name, realname);
		list.add(entry);
	}

	public class CustomIDEntry implements Serializable {

		private static final long serialVersionUID = -7849684598729199956L;

		private final String ip;
		private final String name;
		private final String realname;

		public CustomIDEntry(String ip, String name, String realname) {
			this.ip = ip;
			this.name = name;
			this.realname = realname;
		}

		public String getIP() {
			return ip;
		}

		public String getName() {
			return name;
		}

		public String getRealName() {
			return realname;
		}
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".id");
	}
}
