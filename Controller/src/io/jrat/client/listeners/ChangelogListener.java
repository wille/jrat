package io.jrat.client.listeners;

import io.jrat.client.Constants;
import io.jrat.client.ui.frames.FrameChangelog;
import io.jrat.common.Version;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
