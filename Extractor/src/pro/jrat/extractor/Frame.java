package pro.jrat.extractor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;
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
	
	public static Image background;

	public static File path;
	
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblStatus;
	private JTextPane txtLog;
	private JButton btnAction;

	public Frame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/icon.png")));
		setTitle("jRAT Extractor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 575, 490);
		contentPane = new JPanel() {
			@Override
			public void paint(Graphics g) {
				if (getBg() != null) {
					g.drawImage(getBg(), 0, 0, null);
				}
				
				super.paint(g);	
			}
		};
		contentPane.setBackground(new Color(0, 0, 0, 0));
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
		scrollPane.setBounds(10, 115, 550, 124);
		contentPane.add(scrollPane);
		
		txtLog = new JTextPane();
		txtLog.setEditable(false);
		txtLog.setForeground(Color.BLACK);
		txtLog.setBackground(Color.WHITE);
		scrollPane.setViewportView(txtLog);
		
		btnAction = new JButton("Agree & Install");
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnAction.getText().equals("Agree & Install")) {
					txtLog.setText("");
					
					JFileChooser ch = new JFileChooser();
					ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					ch.showOpenDialog(null);
					
					path = ch.getSelectedFile();
					
					if (path != null) {
						btnAction.setEnabled(false);
						new Thread(new Runnable() {
							@Override
							public void run() {
								Extractor.extract(path, Frame.this);
							}
						}).start();
					}
				} else if (btnAction.getText().equals("Launch jRAT")) {
					try {
						if (System.getProperty("os.name").toLowerCase().contains("win")) {
							ProcessBuilder b = new ProcessBuilder(System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe", "-jar", "Client.jar");
							b.directory(path);
							b.start();
 						} else {
 							ProcessBuilder b = new ProcessBuilder("java", "-jar", "Client.jar");
							b.directory(path);
							b.start();
 						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnAction.setBounds(441, 429, 119, 23);
		contentPane.add(btnAction);
		
		JLabel lblJrat = new JLabel("jRAT Extractor");
		lblJrat.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblJrat.setBounds(44, 11, 203, 24);
		contentPane.add(lblJrat);
		
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
		return btnAction;
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
	
	public Image getBg() {
		if (background == null) {
			try {
				background = ImageIO.read(Main.class.getResource("/icons/background.png"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return background;
	}
}
