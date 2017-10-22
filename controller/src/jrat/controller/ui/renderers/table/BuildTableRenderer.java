package jrat.controller.ui.renderers.table;

import iconlib.IconUtils;
import jrat.controller.build.BuildStatus;
import java.awt.Component;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import jrat.api.ui.DefaultJTableCellRenderer;


@SuppressWarnings("serial")
public class BuildTableRenderer extends DefaultJTableCellRenderer {

	private Map<String, BuildStatus> statuses;

	public static final ImageIcon ICON_INFO = IconUtils.getIcon("log-info");
	public static final ImageIcon ICON_ERROR = IconUtils.getIcon("log-error");
	public static final ImageIcon ICON_TICK = IconUtils.getIcon("tick");

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
