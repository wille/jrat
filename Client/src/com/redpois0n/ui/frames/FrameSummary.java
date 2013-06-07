package com.redpois0n.ui.frames;

import java.awt.Color;
import java.io.File;
import java.util.LinkedHashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.BuildStatus;
import com.redpois0n.common.codec.Md5;
import com.redpois0n.common.codec.Sha1;
import com.redpois0n.ui.components.Table;
import com.redpois0n.ui.renderers.table.BuildTableRenderer;
import com.redpois0n.utils.IconUtils;

@SuppressWarnings("serial")
public class FrameSummary extends BaseDialog {

	private File file;
	private String host;
	private int port;
	private String pass;
	private String key;
	private String id;
	private LinkedHashMap<String, BuildStatus> statuses;
	
	private JPanel contentPane;
	private Table table;
	private DefaultTableModel infomodel;
	private DefaultTableModel generalmodel;
	private DefaultTableModel logmodel;
	private Table table_1;
	private Table table_2;

	public FrameSummary(File file, String host, int port, String pass, String key, String id, LinkedHashMap<String, BuildStatus> statuses) {
		this.file = file;
		this.host = host;
		this.port = port;
		this.pass = pass;
		this.key = key;
		this.id = id;
		this.statuses = statuses;
		
		setModal(true);
		setTitle("Build Summary");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("General", IconUtils.getIcon("id"), panel_1, null);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		
		table_1 = new Table();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Property", "Value"
			}
		));
		table_1.setRowHeight(25);
		generalmodel = (DefaultTableModel) table_1.getModel();
		scrollPane_1.setViewportView(table_1);
		
		JPanel panel = new JPanel();
		
		tabbedPane.addTab("File Information", IconUtils.getIcon("file"), panel, null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new Table();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Property", "Value"
			}
		));
		table.setRowHeight(25);
		table.setGridColor(new Color(205, 205, 205));
		
		infomodel = (DefaultTableModel)table.getModel();
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Log", IconUtils.getIcon("log"), panel_2, null);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2);
		
		table_2 = new Table();
		table_2.setDefaultRenderer(Object.class, new BuildTableRenderer(statuses));
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Action"
			}
		));
		table_2.setRowHeight(25);
		logmodel = (DefaultTableModel) table_2.getModel();
		scrollPane_2.setViewportView(table_2);
		
		setLocationRelativeTo(null);
		
		reload();
	}

	public void reload() {
		try {
			infomodel.addRow(new Object[] { "MD5", Md5.md5(file) });
			infomodel.addRow(new Object[] { "SHA1", Sha1.sha1(file) });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		infomodel.addRow(new Object[] { "Name", file.getName() });
		infomodel.addRow(new Object[] { "Type", file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toUpperCase() + " File" });
		infomodel.addRow(new Object[] { "File Location", file.getAbsolutePath() });
		infomodel.addRow(new Object[] { "Folder", file.getParent() });
		infomodel.addRow(new Object[] { "File size", (file.length() / 1024L) + " kB" });
		
		generalmodel.addRow(new Object[] { "Host", host });
		generalmodel.addRow(new Object[] { "Port", port });
		generalmodel.addRow(new Object[] { "Password", pass });
		generalmodel.addRow(new Object[] { "Encryption Key", key });
		generalmodel.addRow(new Object[] { "ID", id });
		
		for (String str : statuses.keySet()) {
			logmodel.addRow(new Object[] { str });
		}
	}
}
