package io.jrat.client.listeners;

import io.jrat.client.Constants;
import io.jrat.client.ErrorDialog;
import io.jrat.client.net.WebRequest;
import io.jrat.client.ui.frames.FrameChangelog;
import io.jrat.common.Version;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame;
		try {
			frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), Version.getVersion());
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

}
