package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.io.Files;

public class ServerID extends AbstractSettings {

	private List<ServerIDEntry> list = new ArrayList<ServerIDEntry>();
	private static final ServerID instance = new ServerID();
	
	public static ServerID getGlobal() {
		return instance;
	}
	
	public List<ServerIDEntry> getIDList() {
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		list.clear();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(getFile()));
		list = (ArrayList<ServerIDEntry>) in.readObject();
		in.close();

	}

	@Override
	public void save() throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFile()));
		out.writeObject(list);
		out.close();
	}

	public ServerIDEntry findEntry(String ip) {
		for (int i = 0; i < list.size(); i++) {
			ServerIDEntry entry = list.get(i);
			if (entry.ip.equals(ip)) {
				return entry;
			}
		}
		return null;
	}

	public void remove(String ip) {
		ServerIDEntry entry = findEntry(ip);
		if (entry != null) {
			list.remove(entry);
		}
	}

	public void add(String name, String realname, String ip) {
		ServerID.ServerIDEntry entry = new ServerID.ServerIDEntry(ip, name, realname);
		list.add(entry);
	}

	public class ServerIDEntry {

		private final String ip;
		private final String name;
		private final String realname;

		public ServerIDEntry(String ip, String name, String realname) {
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
		return new File(Files.getSettings(), "id.dat");
	}
}
