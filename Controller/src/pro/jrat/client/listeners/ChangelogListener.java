package pro.jrat.client.listeners;

import pro.jrat.client.Constants;
import pro.jrat.client.ui.frames.FrameChangelog;
import pro.jrat.common.Version;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
