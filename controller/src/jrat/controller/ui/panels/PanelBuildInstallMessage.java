package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Constants;
import jrat.controller.Help;
import jrat.controller.ui.components.JPlaceholderTextField;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelBuildInstallMessage extends JPanel {

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtTitle;
	private JCheckBox chckbxEnableInstallMessage;
	private JTextPane txtMsg;
	private JToggleButton btnError;
	private JToggleButton btnInfo;
	private JToggleButton btnNone;
	private JToggleButton btnWarning;
	private JToggleButton btnQuestion;
	private JButton button_1;

	public boolean shouldShowDialog() {
		return chckbxEnableInstallMessage.isSelected();
	}

	public String getTitle() {
		return txtTitle.getText().trim();
	}

	public String getMsg() {
		return txtMsg.getText().trim();
	}

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

	public PanelBuildInstallMessage() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Install message/Messagebox"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addContainerGap()));

		chckbxEnableInstallMessage = new JCheckBox("Enable install message (Installer)");
		chckbxEnableInstallMessage.setToolTipText("Message to popup when ran");

		btnInfo = new JToggleButton("");
		buttonGroup.add(btnInfo);
		btnInfo.setIcon(Resources.getIcon("big-info"));

		btnError = new JToggleButton("");
		buttonGroup.add(btnError);
		btnError.setSelected(true);
		btnError.setIcon(Resources.getIcon("big-error"));

		btnWarning = new JToggleButton("");
		buttonGroup.add(btnWarning);
		btnWarning.setIcon(Resources.getIcon("big-warning"));

		btnQuestion = new JToggleButton("");
		buttonGroup.add(btnQuestion);
		btnQuestion.setIcon(Resources.getIcon("big-question"));

		btnNone = new JToggleButton("None");
		buttonGroup.add(btnNone);

		JLabel lblTitle = new JLabel("Title:");

		txtTitle = new JPlaceholderTextField(Constants.NAME);
		txtTitle.setColumns(10);

		JLabel lblMsg = new JLabel("Msg:");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

		JButton button = new JButton("Test");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, getMsg(), getTitle(), getIcon());
			}
		});
		button.setIcon(Resources.getIcon("messagebox"));

		button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Messagebox to be ran on execution, only once using installer");
			}
		});
		button_1.setIcon(Resources.getIcon("help"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxEnableInstallMessage).addGroup(gl_panel.createSequentialGroup().addComponent(btnInfo).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnError, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnWarning, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnQuestion, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNone, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(lblTitle).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtTitle, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))).addGroup(gl_panel.createSequentialGroup().addGap(11).addComponent(lblMsg).addGap(5).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addComponent(button, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE).addGap(9)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)))).addContainerGap(84, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(chckbxEnableInstallMessage).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnNone, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addComponent(btnInfo).addComponent(btnError, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addComponent(btnWarning, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addComponent(btnQuestion, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblTitle).addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(4).addComponent(lblMsg)).addGroup(gl_panel.createSequentialGroup().addGap(6).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button_1)).addContainerGap(16, Short.MAX_VALUE)));

		txtMsg = new JTextPane();
		txtMsg.setText(Constants.NAME + " has been installed");
		scrollPane.setViewportView(txtMsg);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
