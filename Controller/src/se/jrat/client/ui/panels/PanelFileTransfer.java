package se.jrat.client.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet102PauseServerUpload;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.ui.renderers.table.DefaultJTableCellRenderer;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.io.TransferData;
import se.jrat.common.io.TransferData.State;

import com.redpois0n.graphs.utils.DataUnits;


@SuppressWarnings("serial")
public class PanelFileTransfer extends JPanel {

	public static PanelFileTransfer instance;
	
	static {
		instance = new PanelFileTransfer();
	}

	private JTable table;
	private JProgressBar progressBar;
	private JScrollPane scrollPane;
	private DefaultTableModel model;

	public String file;
	private JLabel label;

	public int getRow(String path) {
		for (int i = 0; i < model.getRowCount(); i++) {
			if (model.getValueAt(i, 1).toString().equalsIgnoreCase(path)) {
				return i;
			}
		}

		return -1;
	}

	public void load(String name) {
		Icon icon = IconUtils.getFileIconFromExtension(name, false);
		String status = "0";

		model.addRow(new Object[] { icon, name, status });
		progressBar.setMaximum(model.getRowCount() * 100);
	}

	public PanelFileTransfer() {
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		progressBar = new JProgressBar();

		label = new JLabel("...");

		table = new DefaultJTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return Icon.class;
				}
				return super.getColumnClass(column);
			}
		};
		
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { " ", "File Path", "Info", "Progress" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(2).setPreferredWidth(388);
		table.getColumnModel().getColumn(3).setPreferredWidth(133);
		table.setDefaultRenderer(Object.class, new FileTransferTableRenderer());

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		
		add(label, BorderLayout.SOUTH);
		add(progressBar, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.CENTER);
		
		JPopupMenu menu = new JPopupMenu();
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				
				for (int i : rows) {
					TransferData d = (TransferData) table.getValueAt(i, 0);
					
					if (d.isUpload()) {
						d.getRunnable().pause(); 
					} else {
						if (d.getState() == State.PAUSED) {
							d.setState(State.IN_PROGRESS);
						} else {
							d.setState(State.PAUSED);
						}
						((Slave)d.getObject()).addToSendQueue(new Packet102PauseServerUpload(d.getRemoteFile()));
					}
				}
			}
		});
		
		menu.add(btnPause);
		
		Utils.addPopup(table, menu);
	}

	public void exit() {

	}

	public void reset() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	public void add(TransferData data) {
		model.insertRow(0, new Object[] { data });
	}

	public void remove(TransferData data) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 0) == data) {
				model.removeRow(i);
				break;
			}
		}
	}
	
	/*public void reportProgress(String path, int i, int bytes, int all) {
		try {
			progressBar.setMaximum(100);
			progressBar.setValue(i);
			model.setValueAt(i, getRow(path), 2);
		} catch (Exception ex) {
		
		}
		
		String b = DataUnits.getAsString((long) bytes);
		String a = DataUnits.getAsString((long) all);
		label.setText("Transferring " + new File(path).getName() + " " + b + "/" + a);
	}

	public void done(String path, String bytes) {
		try {
			model.setValueAt("100", getRow(path), 2);
		} catch (Exception ex) {
		}
		label.setText("Finished " + new File(path).getName());
		progressBar.setValue(progressBar.getMaximum());
	}*/

	public class FileTransferTableRenderer extends DefaultJTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			TransferData data = (TransferData) table.getValueAt(row, 0);
			
			if (data.getState() == State.ERROR) {
				setForeground(Color.red);
			} else if (data.getState() == State.IN_PROGRESS) {
				setForeground(Color.black);
			} else if (data.getState() == State.PAUSED) {
				setForeground(Color.blue.brighter());
			} else if (data.getState() == State.COMPLETED) {
				setForeground(Color.green.darker());
			} else if (isSelected) {
				setForeground(Color.black);
			}
			
			if (column == 1) {
				setText(data.getRemoteFile());
			}
			
			if (column == 2) {
				setText(DataUnits.getAsString(data.getRead()) + "/" + DataUnits.getAsString(data.getTotal()));
			}
			
			if (column == 3) {
				JProgressBar bar = new JProgressBar();
				bar.setMaximum((int) data.getTotal());
				bar.setValue(data.getRead());
				return bar;
			}

			return this;
		}
	}

	public void update() {
		table.repaint();
	}
}
