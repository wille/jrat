package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet39VisitManyURLs;
import jrat.controller.packets.outgoing.Packet44PlaySoundFromURL;
import jrat.controller.packets.outgoing.Packet46CrazyMouse;
import jrat.controller.packets.outgoing.Packet65Beep;
import jrat.controller.utils.NetUtils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelControlFunManager extends PanelControlParent {
	private JTextField txtURLwebsite;
	private JSpinner spinner;
	private JTextField txtURLwav;
	private JSpinner spinnerwav;

	public PanelControlFunManager(Slave slave) {
		super(slave);

		final Slave sl = slave;

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Misc"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Open website"));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory.createTitledBorder("Play sound from URL (.wav)"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE).addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(panel, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false).addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE).addContainerGap(67, Short.MAX_VALUE)));

		JLabel lblUrl_1 = new JLabel("URL:");

		txtURLwav = new JTextField();
		txtURLwav.setColumns(10);

		JLabel lblLoop = new JLabel("Loop:");

		spinnerwav = new JSpinner();
		spinnerwav.setModel(new SpinnerNumberModel(1, 1, 9999, 1));

		JLabel lblTimes = new JLabel("times");

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (txtURLwav.getText().trim().length() == 0) {
						throw new Exception();
					}
					Integer.parseInt(txtURLwav.getText().trim());
					if (!NetUtils.isURL(txtURLwav.getText().trim())) {
						throw new Exception();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Input valid values!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				sl.addToSendQueue(new Packet44PlaySoundFromURL(txtURLwav.getText().trim(), (Integer) spinnerwav.getValue()));
			}
		});
		btnPlay.setIcon(Resources.getIcon("start"));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup().addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING).addComponent(lblLoop).addComponent(lblUrl_1)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addComponent(txtURLwav, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE).addGroup(gl_panel_2.createSequentialGroup().addComponent(spinnerwav, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTimes)))).addComponent(btnPlay, Alignment.TRAILING)).addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(lblUrl_1).addComponent(txtURLwav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(lblLoop).addComponent(spinnerwav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTimes)).addGap(18).addComponent(btnPlay).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JLabel lblUrl = new JLabel("URL:");

		JLabel lblOpen = new JLabel("Open:");

		txtURLwebsite = new JTextField();
		txtURLwebsite.setColumns(10);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 1, 9999, 1));

		JLabel lblTimes_1 = new JLabel("times");

		JButton btnOpen = new JButton("Open");
		btnOpen.setIcon(Resources.getIcon("url"));
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (txtURLwebsite.getText().trim().length() == 0) {
						throw new Exception();
					}
					Integer.parseInt(spinner.getValue().toString());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Input valid values!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				sl.addToSendQueue(new Packet39VisitManyURLs(txtURLwebsite.getText().trim(), (Integer) spinner.getValue()));
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGap(13).addComponent(lblUrl).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(txtURLwebsite, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel_1.createSequentialGroup().addGap(6).addComponent(lblOpen).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTimes_1))).addContainerGap(19, Short.MAX_VALUE)).addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup().addContainerGap(179, Short.MAX_VALUE).addComponent(btnOpen).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblUrl)).addGroup(gl_panel_1.createSequentialGroup().addGap(6).addComponent(txtURLwebsite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addGap(9).addComponent(lblOpen)).addGroup(gl_panel_1.createSequentialGroup().addGap(8).addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblTimes_1)))).addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(btnOpen).addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		JButton btnCrazyMouse = new JButton("Crazy mouse");
		btnCrazyMouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog(null, "Select seconds to enable crazy mouse", "Crazy mouse", JOptionPane.QUESTION_MESSAGE);
				if (result == null) {
					return;
				}
				int seconds;
				try {
					seconds = Integer.parseInt(result);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Input valid values", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				sl.addToSendQueue(new Packet46CrazyMouse(seconds));
			}
		});
		btnCrazyMouse.setIcon(Resources.getIcon("mouse"));

		JButton btnBeep = new JButton("Beep");
		btnBeep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet65Beep());
			}
		});
		btnBeep.setIcon(Resources.getIcon("sound"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(btnBeep, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnCrazyMouse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(143, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(btnCrazyMouse).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnBeep).addContainerGap(46, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
}
