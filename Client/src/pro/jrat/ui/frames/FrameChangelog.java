package pro.jrat.ui.frames;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import pro.jrat.Constants;
import pro.jrat.ErrorDialog;
import pro.jrat.common.Version;
import pro.jrat.net.WebRequest;
import pro.jrat.utils.NetworkUtils;



@SuppressWarnings("serial")
public class FrameChangelog extends BaseFrame {

	private JPanel contentPane;

	public FrameChangelog(String url, String version) {
		super();
		setAlwaysOnTop(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameChangelog.class.getResource("/icons/question.png")));
		setTitle("Whats new? " + Version.getVersion());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 527, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnDownloadLatestUpdate = new JButton("Download latest update");
		btnDownloadLatestUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI(Constants.DOWNLOAD_URL));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnDownloadLatestUpdate.setIcon(new ImageIcon(FrameChangelog.class.getResource("/icons/drive-download.png")));

		JLabel lblYourVersion = new JLabel("Your version: " + Version.getVersion());

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setIcon(new ImageIcon(FrameChangelog.class.getResource("/icons/delete.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnDownloadLatestUpdate)
					.addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
					.addComponent(lblYourVersion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClose))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDownloadLatestUpdate)
						.addComponent(btnClose)
						.addComponent(lblYourVersion))
					.addGap(30))
		);

		contentPane.setLayout(gl_contentPane);
		
		JEditorPane com;
		try {
			com = new JEditorPane(WebRequest.getUrl(url));
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
			return;
		}
		com.addHyperlinkListener(new HyperlinkListener() {
			 public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						NetworkUtils.openUrl(e.getURL().toURI().toString());
					} catch (Exception localException) {
						localException.printStackTrace();
					}		 
				}
			 }
		});
		com.setEditable(false);
		com.setContentType("text/html");
		
		scrollPane.setViewportView(com);


		/*try {
			com.getDocument().insertString(0, "<html>", null);
			BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(url)));

			String s = null;
			while ((s = reader.readLine()) != null) {
				com.getDocument().insertString(com.getDocument().getLength(), s + "\n", null);
			}
			
			reader.close();
			
			com.getDocument().insertString(com.getDocument().getLength(), "</html>", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}*/

		com.setSelectionStart(0);
		com.setSelectionEnd(0);

		setLocationRelativeTo(null);
	}
}
