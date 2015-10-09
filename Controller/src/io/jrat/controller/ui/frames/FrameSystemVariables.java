package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.controller.Constants;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet90SystemProperties;
import io.jrat.controller.packets.outgoing.Packet96EnvironmentVariables;
import io.jrat.controller.ui.components.DefaultJTable;
import io.jrat.controller.ui.components.TableModel;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class FrameSystemVariables extends BaseFrame {

	public static final Map<Slave, FrameSystemVariables> INSTANCES = new HashMap<Slave, FrameSystemVariables>();

	private JPanel contentPane;
	private JTable table;
	private TableModel model;
	private Constants.VariableMode mode;

	public FrameSystemVariables(Constants.VariableMode mode, Slave s) {
		super(s);
		this.mode = mode;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		INSTANCES.put(slave, this);
		
		setResizable(false);
		
		if (mode == Constants.VariableMode.ENVIRONMENT_VARIABLES) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(FrameSystemVariables.class.getResource("/icons/categories.png")));
			setTitle("Environment Variables - " + "[" + slave.getDisplayName() + "] - " + slave.getIP());
		} else if (mode == Constants.VariableMode.SYSTEM_PROPERTIES) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(FrameSystemVariables.class.getResource("/icons/properties.png")));
			setTitle("System Properties - " + "[" + slave.getDisplayName() + "] - " + slave.getIP());
		} else {
			throw new RuntimeException("Invalid mode");
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
				reload();
			}
		});
		btnReload.setIcon(IconUtils.getIcon("update"));

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
		btnSave.setIcon(IconUtils.getIcon("save"));

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		btnClose.setIcon(IconUtils.getIcon("delete"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReload, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnSave).addComponent(btnClose)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(163);
		table.getColumnModel().getColumn(1).setPreferredWidth(268);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

		reload();
	}
	
	/**
	 * Clears the table and sends packet for retrieving the data
	 */
	public void reload() {
		if (mode == Constants.VariableMode.SYSTEM_PROPERTIES) {
			clear();
			slave.addToSendQueue(new Packet90SystemProperties());
		} else if (mode == Constants.VariableMode.ENVIRONMENT_VARIABLES) {
			clear();
			slave.addToSendQueue(new Packet96EnvironmentVariables());
		}
	}

	public void exit() {
		INSTANCES.remove(slave);
		setVisible(false);
		dispose();
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	/**
	 * Add this values to the table
	 * @param k first
	 * @param v second
	 */
	public void add(String k, String v) {
		model.addRow(new Object[] { k, v });
	}
}
