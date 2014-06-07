package su.jrat.client.ui.renderers.table;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import su.jrat.client.BuildStatus;
import su.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class BuildTableRenderer extends DefaultTableCellRenderer {

	private HashMap<String, BuildStatus> statuses;

	public static final ImageIcon ICON_INFO = IconUtils.getIcon("log_info");
	public static final ImageIcon LOG_ERROR = IconUtils.getIcon("log_error");
	public static final ImageIcon ICON_TICK = IconUtils.getIcon("tick");

	public BuildTableRenderer(HashMap<String, BuildStatus> statuses) {
		this.statuses = statuses;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		BuildStatus status = statuses.get(lbl.getText());

		if (status == BuildStatus.INFO) {
			lbl.setIcon(ICON_INFO);
		} else if (status == BuildStatus.CHECK) {
			lbl.setIcon(ICON_TICK);
		} else if (status == BuildStatus.ERROR) {
			lbl.setIcon(LOG_ERROR);
		} else if (status == BuildStatus.FINISH) {
			lbl.setIcon(ICON_TICK);
		}

		return lbl;
	}
}
