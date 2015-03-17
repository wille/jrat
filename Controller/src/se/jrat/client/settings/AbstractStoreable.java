package se.jrat.client.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStoreable {

	private static final List<AbstractStoreable> globalSettings = new ArrayList<AbstractStoreable>();
	
	public static void loadAllGlobals() {
		if (globalSettings.size() == 0) {
			globalSettings.add(StatisticsCountry.getGlobal());
			globalSettings.add(StatisticsOperatingSystem.getGlobal());
			globalSettings.add(SettingsCustomID.getGlobal());
			globalSettings.add(SettingsSockets.getGlobal());
			globalSettings.add(StoreageFileBookmarks.getGlobal());
			globalSettings.add(SettingsColors.getGlobal());
			globalSettings.add(StoreageOfflineSlaves.getGlobal());
		}

		for (AbstractStoreable setting : globalSettings) {
			try {
				setting.load();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void saveAllGlobals() {
		for (AbstractStoreable setting : globalSettings) {
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
