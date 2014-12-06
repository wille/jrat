package se.jrat.client.ui.dialogs;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.jrat.client.ErrorDialog;
import se.jrat.client.threads.ThreadRecordButton;
import se.jrat.client.ui.frames.FrameRemoteScreen;


@SuppressWarnings("serial")
public class DialogRecordRemoteScreen extends BaseDialog {
	
	private boolean isRecording;
	private FrameRemoteScreen parent;
	private JTextField txtLocation;
	private JButton btnRecord;
	private ThreadRecordButton threadRecordButton;

	public DialogRecordRemoteScreen(FrameRemoteScreen parent) {
		setTitle("Record");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogRecordRemoteScreen.class.getResource("/icons/record.png")));
		setResizable(false);
		setBounds(100, 100, 452, 115);
		setModal(true);
		
		this.parent = parent;
		
		JLabel lblSaveTo = new JLabel("Save to:");
		lblSaveTo.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtLocation = new JTextField();
		txtLocation.setText("files/recordings");
		txtLocation.setEditable(false);
		txtLocation.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.showOpenDialog(null);
				
				File file = c.getSelectedFile();
				
				if (file != null) {
					txtLocation.setText(file.getAbsolutePath());
				}
			}
		});
		
		JButton btnHide = new JButton("Hide");
		
		JLabel lblFormat = new JLabel("Format:");
		lblFormat.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JComboBox<String> cbFormat = new JComboBox<String>();
		cbFormat.setModel(new DefaultComboBoxModel<String>(new String[] {"png"}));
		cbFormat.setEnabled(false);
		
		btnRecord = new JButton("Start Record");
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggle();
			}
		});
		btnRecord.setIcon(new ImageIcon(DialogRecordRemoteScreen.class.getResource("/icons/record_big.png")));
				
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblSaveTo)
						.addComponent(lblFormat))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(cbFormat, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnRecord)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnHide))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtLocation, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse)))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSaveTo)
						.addComponent(txtLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnHide)
						.addComponent(cbFormat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFormat)
						.addComponent(btnRecord))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		setLocationRelativeTo(null);
	}
	
	public void toggle() {
		if (!isRecording) {
			btnRecord.setText("Stop Record");
			
			threadRecordButton = new ThreadRecordButton(btnRecord);
			threadRecordButton.start();
			
			parent.setThreadRecordButton(new ThreadRecordButton(parent.getRecordButton()));
			parent.getThreadRecordButton().start();
			
			isRecording = true;
		} else {
			btnRecord.setText("Start Record");
			
			threadRecordButton.stopRunning();
			parent.getThreadRecordButton().stopRunning();
			parent.getRecordButton().setIcon(FrameRemoteScreen.DEFAULT_RECORD_ICON);
			
			isRecording = false;
		}
	}
	
	public boolean isRecording() {
		return isRecording;
	}

	@SuppressWarnings("deprecation")
	public void update(BufferedImage image) {
		Date date = new Date();
		
		String hours = (date.getHours() + "").length() == 1 ? "0" + date.getHours() : Integer.toString(date.getHours());
		String minutes = (date.getMinutes() + "").length() == 1 ? "0" + date.getMinutes() : Integer.toString(date.getMinutes());
		String seconds = (date.getSeconds() + "").length() == 1 ? "0" + date.getSeconds() : Integer.toString(date.getSeconds());
		
		String time = hours + "-" + minutes + "-" + seconds;
		
		int tryNo = 0;
		
		File location = new File(txtLocation.getText());
		File file;
		
		do {
			file = new File(location, tryNo++ + " " + parent.getSlave().getComputerName() + " " + time + ".png");
		} while (file.exists());
		
		if (!location.exists()) {
			location.mkdirs();
		}
		
		try {
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
			toggle();
			ErrorDialog.create(e);
		}
	}
}
