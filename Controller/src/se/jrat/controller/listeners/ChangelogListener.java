package se.jrat.controller.listeners;

import se.jrat.common.Version;
import se.jrat.controller.Constants;
import se.jrat.controller.ErrorDialog;
import se.jrat.controller.net.WebRequest;
import se.jrat.controller.ui.frames.FrameChangelog;

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
