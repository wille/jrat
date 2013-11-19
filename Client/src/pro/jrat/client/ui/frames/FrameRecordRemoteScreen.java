package pro.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameRecordRemoteScreen extends BaseFrame {

	private JPanel contentPane;
	private JTextField textField;
	private FrameRemoteScreen frame;
	private int count;
	private JComboBox<String> comboBox;

	public int getCount() {
		return count++;
	}

	public String getPath() {
		return textField.getText().trim();
	}

	public String getExtension() {
		return comboBox.getSelectedItem().toString().trim().toLowerCase();
	}

	public FrameRecordRemoteScreen(FrameRemoteScreen f) {
		super();
		this.frame = f;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRecordRemoteScreen.class.getResource("/icons/record.png")));
		setTitle("Record remote screen");
		setBounds(100, 100, 401, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JButton btnRecord = new JButton("Record");
		final JButton btnStopRecord = new JButton("Stop record");
		btnStopRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.record = false;
				btnStopRecord.setEnabled(false);
				btnRecord.setEnabled(true);
			}
		});
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.record = true;
				btnStopRecord.setEnabled(true);
				btnRecord.setEnabled(false);
			}
		});
		btnRecord.setIcon(new ImageIcon(FrameRecordRemoteScreen.class.getResource("/icons/record.png")));

		JLabel lblSave = new JLabel("Save:");

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.showSaveDialog(null);
				File file = c.getSelectedFile();
				if (file != null) {
					textField.setText(file.getAbsolutePath() + File.separator);
				}
			}
		});
		button.setIcon(new ImageIcon(FrameRecordRemoteScreen.class.getResource("/icons/folder_go.png")));

		JLabel lblFormat = new JLabel("Format:");

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "png", "gif", "jpg" }));

		btnStopRecord.setEnabled(false);
		btnStopRecord.setIcon(new ImageIcon(FrameRecordRemoteScreen.class.getResource("/icons/record.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(lblSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(textField, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(button)).addGroup(gl_contentPane.createSequentialGroup().addComponent(lblFormat).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(btnRecord).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStopRecord))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSave).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(button)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblFormat).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnRecord).addComponent(btnStopRecord)).addContainerGap(81, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
