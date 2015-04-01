package se.jrat.client.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet102PauseServerUpload;
import se.jrat.client.packets.outgoing.Packet103CompleteServerUpload;
import se.jrat.client.packets.outgoing.Packet105CancelServerDownload;
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

	private JLabel label;
	private JMenuItem btnPause;
	private JMenuItem btnCancel;

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

		table = new DefaultJTable();
		
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "File Path", "Info", "Progress" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		table.setDefaultRenderer(Object.class, new FileTransferTableRenderer());

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		
		add(label, BorderLayout.SOUTH);
		add(progressBar, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.CENTER);
		
		JPopupMenu menu = new JPopupMenu();
		
		btnPause = new JMenuItem("Pause");
		btnPause.setIcon(IconUtils.getIcon("pause"));
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				
				for (int i : rows) {
					TransferData d = (TransferData) table.getValueAt(i, 0);
					
					if (d.getState() == State.PAUSED) {
						d.setState(State.IN_PROGRESS);
					} else {
						d.setState(State.PAUSED);
					}
					
					if (d.isUpload()) {
						d.getRunnable().pause(); 
					} else {					
						((Slave)d.getObject()).addToSendQueue(new Packet102PauseServerUpload(d.getRemoteFile()));
					}
				}
			}
		});
		
		menu.add(btnPause);
		
		btnCancel = new JMenuItem("Cancel");
		btnCancel.setIcon(IconUtils.getIcon("delete"));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				
				for (int i : rows) {
					TransferData d = (TransferData) table.getValueAt(i, 0);
					
					d.setState(State.ERROR);
					
					if (d.isUpload()) {
						d.getRunnable().interrupt();
						((Slave)d.getObject()).addToSendQueue(new Packet103CompleteServerUpload(d.getRemoteFile()));
					} else {
						((Slave)d.getObject()).addToSendQueue(new Packet105CancelServerDownload(d.getRemoteFile()));
					}
				}
			}
		});
		
		menu.add(btnCancel);
		
		menu.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				TransferData[] selected = getSelectedItems();
				
				if (selected.length == 1) {
					if (selected[0].getState() == State.PAUSED) {
						btnPause.setIcon(IconUtils.getIcon("stop"));
						btnPause.setText("Stop");
					} else {
						btnPause.setIcon(IconUtils.getIcon("pause"));
						btnPause.setText("Pause");
					}
					
					btnPause.setEnabled(selected[0].getState() != State.COMPLETED && selected[0].getState() != State.ERROR);
				} else {
					btnPause.setEnabled(false);
					btnPause.setIcon(IconUtils.getIcon("pause"));
					btnPause.setText("Pause");
				}
			}
		});
		
		Utils.addPopup(table, menu);
	}
	
	public TransferData[] getSelectedItems() {
		int[] rows = table.getSelectedRows();
		
		TransferData[] td = new TransferData[rows.length];
		
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			td[i] = (TransferData) table.getValueAt(row, 0);
		}
		
		return td;
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
			
			if (column == 0) {
				setText(data.getRemoteFile());
				setIcon(IconUtils.getFileIconFromExtension(data.getRemoteFile(), false));
			} else {
				setIcon(null);
			}
			
			if (column == 1) {
				setText(DataUnits.getAsString(data.getRead()) + "/" + DataUnits.getAsString(data.getTotal()));
			}
			
			if (column == 2) {
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
