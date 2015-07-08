package se.jrat.controller.ui.panels;

import iconlib.FileIconUtils;
import iconlib.IconUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.common.io.TransferData.State;
import se.jrat.common.utils.DataUnits;
import se.jrat.common.utils.MathUtils;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet102PauseServerUpload;
import se.jrat.controller.packets.outgoing.Packet103CompleteServerUpload;
import se.jrat.controller.packets.outgoing.Packet105CancelServerDownload;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.components.TableModel;
import se.jrat.controller.ui.renderers.table.DefaultJTableCellRenderer;
import se.jrat.controller.utils.Utils;


@SuppressWarnings("serial")
public class PanelFileTransfers extends JPanel {

	public static PanelFileTransfers instance;
	
	static {
		instance = new PanelFileTransfers();
	}

	private JTable table;
	private JScrollPane scrollPane;
	private TableModel model;

	private JMenuItem btnPause;
	private JMenuItem btnCancel;

	public void load(String name) {
		Icon icon = FileIconUtils.getIconFromExtension(name);
		String status = "0";

		model.addRow(new Object[] { icon, name, status });
	}

	public PanelFileTransfers() {
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
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
		btnPause.setIcon(IconUtils.getIcon("pause"));
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
		
		btnCancel = new JMenuItem("Cancel");
		btnCancel.setIcon(IconUtils.getIcon("delete"));
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
						btnPause.setIcon(IconUtils.getIcon("start"));
						btnPause.setText("Continue");
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

	public List<TransferData> getTransfers() {
		List<TransferData> list = new ArrayList<TransferData>();
		
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
					setIcon(IconUtils.getIcon("error"));
					setForeground(Color.red);
					setText("Terminated");
				} else if (data.getState() == State.IN_PROGRESS) {
					if (data.isUpload()) {
						setIcon(IconUtils.getIcon("arrow-up"));
					} else {
						setIcon(IconUtils.getIcon("arrow-down"));
					}
					setForeground(Color.black);
					if (data.isUpload()) {
						setText("Uploading");
					} else {
						setText("Downloading");
					}
				} else if (data.getState() == State.PAUSED) {
					setIcon(IconUtils.getIcon("pause"));
					setText("Paused");
				} else if (data.getState() == State.COMPLETED) {
					setIcon(IconUtils.getIcon("tick"));
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
