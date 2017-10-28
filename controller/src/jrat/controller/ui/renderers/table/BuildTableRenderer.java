package jrat.controller.ui.renderers.table;

import jrat.api.Resources;
import jrat.controller.build.BuildStatus;
import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


@SuppressWarnings("serial")
public class BuildTableRenderer extends DefaultJTableCellRenderer {

	private Map<String, BuildStatus> statuses;

	public static final ImageIcon ICON_INFO = Resources.getIcon("log-info");
	public static final ImageIcon ICON_ERROR = Resources.getIcon("log-error");
	public static final ImageIcon ICON_TICK = Resources.getIcon("tick");

	public BuildTableRenderer(Map<String, BuildStatus> statuses) {
		this.statuses = statuses;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		BuildStatus status = statuses.get(label.getText());

		if (status == BuildStatus.INFO) {
			label.setIcon(ICON_INFO);
		} else if (status == BuildStatus.CHECK) {
			label.setIcon(ICON_TICK);
		} else if (status == BuildStatus.ERROR) {
			label.setIcon(ICON_ERROR);
		} else if (status == BuildStatus.FINISH) {
			label.setIcon(ICON_TICK);
		}

		return label;
	}
}
