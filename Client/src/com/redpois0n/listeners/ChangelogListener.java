package com.redpois0n.listeners;

import com.redpois0n.Constants;
import com.redpois0n.common.Version;
import com.redpois0n.ui.frames.FrameChangelog;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
