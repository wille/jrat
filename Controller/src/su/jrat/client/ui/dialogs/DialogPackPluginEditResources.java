package su.jrat.client.ui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import su.jrat.client.ui.frames.FramePackPlugin;

@SuppressWarnings("serial")
public class DialogPackPluginEditResources extends JDialog {

	private FramePackPlugin frame;
	private JTable table;
	private DefaultTableModel model;

	public DialogPackPluginEditResources(FramePackPlugin frame) {
		super();
		this.frame = frame;
	}

	@SuppressWarnings("unused")
	private DialogPackPluginEditResources() {
		setTitle("Plugin Resources");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnClose = new JButton("Close");
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();
				
				if (file != null) {					
					model.addRow(new Object[] { file.getAbsolutePath(), "/" + file.getName()  });		
					frame.updateResources(table.getRowCount());
				}
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.removeRow(table.getSelectedRow());
				frame.updateResources(table.getRowCount());
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(268, Short.MAX_VALUE)
					.addComponent(btnAdd)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRemove)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClose)
					.addGap(13))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnAdd)
						.addComponent(btnRemove))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"File location", "Plugin location"
			}
		));
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);

	}
	
	public List<File> getResources() {
		List<File> list = new ArrayList<File>();
		
		for (int i = 0; i < table.getRowCount(); i++)  {
			String value = model.getValueAt(i, 0).toString();
			
			File file = new File(value);
			
			if (file.exists()) {
				list.add(file);
			}
		}
		
		return list;
	}

}
