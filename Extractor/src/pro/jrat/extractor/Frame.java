package pro.jrat.extractor;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblStatus;
	private JTextPane txtLog;
	private JButton btnAgreeInstall;

	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
		setTitle("jRAT Extractor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 576, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 420, 570, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 46, 570, 2);
		contentPane.add(separator_1);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Frame.class.getResource("/icons/big_icon.png")));
		label.setBounds(10, 11, 24, 24);
		contentPane.add(label);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 59, 550, 29);
		contentPane.add(progressBar);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(20, 93, 540, 14);
		contentPane.add(lblStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 115, 550, 288);
		contentPane.add(scrollPane);
		
		txtLog = new JTextPane();
		scrollPane.setViewportView(txtLog);
		
		btnAgreeInstall = new JButton("Agree & Install");
		btnAgreeInstall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtLog.setText("");
				
				JFileChooser ch = new JFileChooser();
				ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				ch.showOpenDialog(null);
				
				final File path = ch.getSelectedFile();
				
				if (path != null) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Extractor.extract(path, Frame.this);
						}
					}).start();
				}
			}
		});
		btnAgreeInstall.setBounds(441, 429, 119, 23);
		contentPane.add(btnAgreeInstall);
		
		setLocationRelativeTo(null);
		
		loadEula();
	}

	private void loadEula() {
		try {
			System.out.println("Downloading EULA");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://jrat.pro/misc/eula.txt").openStream()));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				txtLog.getDocument().insertString(txtLog.getDocument().getLength(), line + "\n\r", null);
			}
			
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JLabel getStatusLabel() {
		return lblStatus;
	}

	public JTextPane getLog() {
		return txtLog;
	}

	public JButton getButton() {
		return btnAgreeInstall;
	}
	
	public void log(String str) {
		try {
			lblStatus.setText(str);
			txtLog.getDocument().insertString(txtLog.getDocument().getLength(), str + "\n\r", null);
			txtLog.setSelectionStart(txtLog.getDocument().getLength());
			txtLog.setSelectionEnd(txtLog.getDocument().getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
