package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.io.Files;

public class ServerID {

	public static List<ServerIDEntry> list = new ArrayList<ServerIDEntry>();

	@SuppressWarnings("unchecked")
	public static void load() {
		try {
			list.clear();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(Files.getSettings(), "id.dat")));
			list = (ArrayList<ServerIDEntry>) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "id.dat")));
			out.writeObject(list);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ServerIDEntry findEntry(String ip) {
		for (int i = 0; i < list.size(); i++) {
			ServerIDEntry entry = list.get(i);
			if (entry.ip.equals(ip)) {
				return entry;
			}
		}
		return null;
	}

	public static void remove(String ip) {
		ServerIDEntry entry = findEntry(ip);
		if (entry != null) {
			list.remove(entry);
		}
	}

	public static void add(String name, String realname, String ip) {
		ServerIDEntry entry = new ServerIDEntry();
		entry.name = name;
		entry.ip = ip;
		entry.realname = realname;
		list.add(entry);
	}

}
