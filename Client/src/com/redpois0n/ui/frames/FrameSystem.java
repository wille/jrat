package com.redpois0n.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.Constants;
import com.redpois0n.Slave;
import com.redpois0n.packets.OutgoingHeader;

@SuppressWarnings("serial")
public class FrameSystem extends BaseFrame {

	private JPanel contentPane;
	public JTable table;
	public static HashMap<Slave, FrameSystem> instances = new HashMap<Slave, FrameSystem>();
	public DefaultTableModel model;
	public Slave slave;

	public FrameSystem(final int mode, Slave slave) {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		instances.put(slave, this);
		final Slave sl = slave;
		this.slave = slave;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameSystem.class.getResource("/icons/properties.png")));
		setTitle("System Properties - " + slave.getIP() + " - " + slave.getComputerName());
		if (mode == Constants.MODE_ENV) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(FrameSystem.class.getResource("/icons/categories.png")));
			setTitle("Environment Variables - " + slave.getIP() + " - " + slave.getComputerName());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mode == Constants.MODE_PROP) {
					clear();
					sl.addToSendQueue(OutgoingHeader.SYSTEM_PROPERTIES);
				} else if (mode == Constants.MODE_ENV) {
					clear();
					sl.addToSendQueue(OutgoingHeader.ENVIRONMENT_VARIABLES);
				}
			}
		});
		btnReload.setIcon(new ImageIcon(FrameSystem.class.getResource("/icons/update.png")));

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.getRowCount() > 0) {
					JFileChooser fi = new JFileChooser();
					fi.showSaveDialog(null);
					File file = fi.getSelectedFile();
					if (file != null) {
						try {
							FileWriter wr = new FileWriter(file);
							for (int i = 0; i < model.getRowCount(); i++) {
								wr.write(model.getValueAt(i, 0) + ": " + model.getValueAt(i, 1) + "\n");
							}
							wr.close();
							JOptionPane.showMessageDialog(null, "Saved table to: " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Failed saving table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnSave.setIcon(new ImageIcon(FrameSystem.class.getResource("/icons/save.png")));

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameSystem.class.getResource("/icons/delete.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReload, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnSave).addComponent(btnClose)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		table = new JTable();
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(163);
		table.getColumnModel().getColumn(1).setPreferredWidth(268);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		if (mode == Constants.MODE_PROP) {
			sl.addToSendQueue(OutgoingHeader.SYSTEM_PROPERTIES);
		} else if (mode == Constants.MODE_ENV) {
			sl.addToSendQueue(OutgoingHeader.ENVIRONMENT_VARIABLES);
		}
	}

	public void exit() {
		instances.remove(slave);
		setVisible(false);
		dispose();
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

}
