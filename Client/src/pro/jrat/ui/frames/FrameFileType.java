package pro.jrat.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameFileType extends JDialog {

	public JTextField txtExtension;

	private JPanel contentPane;
	private JComboBox<String> comboBox;
	private JButton btnOk;

	public FrameFileType() {
		setModal(true);
		setTitle("File type");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameFileType.class.getResource("/icons/application-detail.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblPickAFile = new JLabel("Pick a file type:");

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() == 0) {
					txtExtension.setText("exe");
					txtExtension.setEnabled(false);
				} else if (comboBox.getSelectedIndex() == 1) {
					txtExtension.setText("jar");
					txtExtension.setEnabled(false);
				} else {
					txtExtension.setText("");
					txtExtension.setEnabled(true);
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Windows Executable (.exe)", "Java Archive (.jar)", "Other" }));

		JLabel lblExtension = new JLabel("Extension:");

		txtExtension = new JTextField("exe");
		txtExtension.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				btnOk.setEnabled(txtExtension.getText().length() > 0);
			}
		});
		txtExtension.setEnabled(false);
		txtExtension.setColumns(10);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboBox.setSelectedItem(null);
				setVisible(false);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblPickAFile).addComponent(lblExtension)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(comboBox, 0, 221, Short.MAX_VALUE).addComponent(txtExtension, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(btnCancel).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnOk))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPickAFile).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(txtExtension, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblExtension)).addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnOk).addComponent(btnCancel)).addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		setLocationRelativeTo(null);
	}

	public static String showDialog() {
		FrameFileType frame = new FrameFileType();
		frame.setVisible(true);

		if (frame.txtExtension.getText().length() > 0) {
			return frame.txtExtension.getText();
		} else {
			return null;
		}
	}
}
