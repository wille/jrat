package su.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import su.jrat.client.ui.renderers.table.FileTransferTableRenderer;
import su.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class FrameFileTransfer extends BaseFrame {

	public static FrameFileTransfer instance;

	private JPanel contentPane;
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

	public FrameFileTransfer() {
		super();
		if (instance != null) {
			instance.setVisible(false);
			instance.dispose();
		}
		instance = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameFileTransfer.class.getResource("/icons/transfer.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setTitle("File Transfer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		progressBar = new JProgressBar();

		JButton btnHide = new JButton("Hide");
		btnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				exit();
			}
		});

		label = new JLabel("...");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(label, GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnHide)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE).addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnHide).addComponent(label))));

		table = new JTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return Icon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { " ", "File Path", "Progress" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(388);
		table.getColumnModel().getColumn(2).setPreferredWidth(133);
		table.setDefaultRenderer(Object.class, new FileTransferTableRenderer());

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}

	public void exit() {

	}

	public void reset() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void reportProgress(String path, int i, int bytes, int all) {
		try {
			progressBar.setMaximum(100);
			progressBar.setValue(i);
			model.setValueAt(i, getRow(path), 2);
		} catch (Exception ex) {
		}
		label.setText("Transferring " + new File(path).getName() + " " + (bytes / 1024) + "/" + (all / 1024) + " kB");
	}

	public void done(String path, String bytes) {
		try {
			model.setValueAt("100", getRow(path), 2);
		} catch (Exception ex) {
		}
		label.setText("Finished " + new File(path).getName());
		progressBar.setValue(progressBar.getMaximum());
	}
}
