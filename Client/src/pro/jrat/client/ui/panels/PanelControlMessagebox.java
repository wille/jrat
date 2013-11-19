package pro.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet10Messagebox;

@SuppressWarnings("serial")
public class PanelControlMessagebox extends PanelControlParent {

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtTitle;
	private JTextPane txtMsg;
	private JToggleButton btnError;
	private JToggleButton btnInfo;
	private JToggleButton btnNone;
	private JToggleButton btnWarning;
	private JToggleButton btnQuestion;
	private JCheckBox chckbxSetSystemFeel;

	public int getIcon() {
		int mode = 0;
		if (btnNone.isSelected()) {
			mode = JOptionPane.PLAIN_MESSAGE;
		} else if (btnQuestion.isSelected()) {
			mode = JOptionPane.QUESTION_MESSAGE;
		} else if (btnError.isSelected()) {
			mode = JOptionPane.ERROR_MESSAGE;
		} else if (btnWarning.isSelected()) {
			mode = JOptionPane.WARNING_MESSAGE;
		} else if (btnInfo.isSelected()) {
			mode = JOptionPane.INFORMATION_MESSAGE;
		}
		return mode;
	}

	public PanelControlMessagebox(Slave slave) {
		super(slave);

		final Slave sl = slave;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Icon"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Other"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)).addContainerGap(253, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE).addContainerGap(50, Short.MAX_VALUE)));

		JLabel lblTitle = new JLabel("Title:");

		txtTitle = new JTextField();
		txtTitle.setText("Hey.");
		txtTitle.setColumns(10);

		JLabel lblMsg = new JLabel("Msg:");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sl.addToSendQueue(new Packet10Messagebox(chckbxSetSystemFeel.isSelected(), getIcon(), txtTitle.getText(), txtMsg.getText()));
			}
		});
		btnSend.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/right.png")));

		JButton btnTest = new JButton("Test");
		btnTest.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/messagebox.png")));
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, txtMsg.getText(), txtTitle.getText(), getIcon());
			}
		});

		chckbxSetSystemFeel = new JCheckBox("Set system feel");
		chckbxSetSystemFeel.setToolTipText("Sets the default system look and feel and not the default java");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblTitle).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtTitle, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)).addGroup(gl_panel_1.createSequentialGroup().addGap(11).addComponent(lblMsg).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(chckbxSetSystemFeel).addGap(18).addComponent(btnTest).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend))).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblTitle).addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGap(8).addComponent(lblMsg)).addGroup(gl_panel_1.createSequentialGroup().addGap(11).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE).addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(btnSend).addComponent(btnTest).addComponent(chckbxSetSystemFeel)).addContainerGap()));

		txtMsg = new JTextPane();
		txtMsg.setText("Message.");
		scrollPane.setViewportView(txtMsg);
		panel_1.setLayout(gl_panel_1);

		btnInfo = new JToggleButton("");
		buttonGroup.add(btnInfo);
		btnInfo.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/box_info.png")));

		btnQuestion = new JToggleButton("");
		buttonGroup.add(btnQuestion);
		btnQuestion.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/box_question.png")));

		btnWarning = new JToggleButton("");
		buttonGroup.add(btnWarning);
		btnWarning.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/box_warning.png")));

		btnError = new JToggleButton("");
		buttonGroup.add(btnError);
		btnError.setIcon(new ImageIcon(PanelControlMessagebox.class.getResource("/icons/box_error.png")));

		btnNone = new JToggleButton("None");
		buttonGroup.add(btnNone);
		btnNone.setSelected(true);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(6).addComponent(btnQuestion, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(6).addComponent(btnWarning, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnError, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNone, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addContainerGap(56, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap(19, Short.MAX_VALUE).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(9).addComponent(btnInfo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addGap(9).addComponent(btnQuestion, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addGap(9).addComponent(btnWarning, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addGap(9).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnError, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addComponent(btnNone, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))).addContainerGap()));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
}
