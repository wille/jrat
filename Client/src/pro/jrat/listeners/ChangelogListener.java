package pro.jrat.listeners;

import pro.jrat.Constants;
import pro.jrat.common.Version;
import pro.jrat.ui.frames.FrameChangelog;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
