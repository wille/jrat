package se.jrat.stub.modules.startup;

import java.io.File;
import java.util.Map;

import se.jrat.stub.Main;

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
		});
	}

}
