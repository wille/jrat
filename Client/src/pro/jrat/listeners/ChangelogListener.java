package pro.jrat.listeners;

import pro.jrat.Constants;
import pro.jrat.ui.frames.FrameChangelog;

import com.redpois0n.common.Version;

public class ChangelogListener extends Performable {

	@Override
	public void perform() {
		FrameChangelog frame = new FrameChangelog(Constants.CHANGELOG_URL, Version.getVersion());
		frame.setVisible(true);
	}

}
