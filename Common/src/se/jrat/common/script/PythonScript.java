package se.jrat.common.script;

import java.io.File;

public class PythonScript extends Script {

	@Override
	public void perform(File file) throws Exception {
		Runtime.getRuntime().exec(new String[] { "python", file.getAbsolutePath() });
	}

	@Override
	public String getExtension() {
		return "py";
	}

}
