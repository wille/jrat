package io.jrat.controller.ui.dialogs;

import iconlib.IconUtils;
import io.jrat.controller.ui.panels.PanelImage;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class DialogErrorDialog extends JDialog {

	private JPanel contentPane;
	public Dimension originalSize;
	public Dimension extendedSize;
	private JTextPane txtReason;
	private int state = DialogErrorDialog.STATE_NORMAL;
	private JButton btnDetails;
	private JButton btnCopyToClipboard;
	public static final int STATE_NORMAL = 0;
	public static final int STATE_EXPANDED = 1;

	public static String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public DialogErrorDialog(Exception ex) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogErrorDialog.class.getResource("/icons/error.png")));
		setType(Type.POPUP);
		setTitle("Error occured");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 304);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 130, 414, 132);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		btnDetails = new JButton("Details >>");
		btnDetails.setIcon(IconUtils.getIcon("expand"));
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (state == DialogErrorDialog.STATE_NORMAL) {
					state = DialogErrorDialog.STATE_EXPANDED;
					setSize(extendedSize);
					btnDetails.setText("Details <<");
					btnDetails.setIcon(IconUtils.getIcon("collapse"));
					btnCopyToClipboard.setVisible(true);
				} else {
					state = DialogErrorDialog.STATE_NORMAL;
					setSize(originalSize);
					btnDetails.setText("Details >>");
					btnDetails.setIcon(IconUtils.getIcon("expand"));
					btnCopyToClipboard.setVisible(false);
				}
			}
		});
		btnDetails.setBounds(313, 94, 116, 25);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				setVisible(false);
			}
		});
		btnOk.setIcon(IconUtils.getIcon("enabled"));
		btnOk.setBounds(242, 94, 67, 25);

		PanelImage panel = new PanelImage();
		panel.setBounds(15, 16, 48, 47);
		panel.image = IconUtils.getIcon("xbig-error").getImage();

		JLabel lblAErrorOccured = new JLabel("A error occured!");
		lblAErrorOccured.setBounds(73, 16, 79, 14);

		JLabel lblErrorMessage = new JLabel("Error message:");
		lblErrorMessage.setBounds(73, 36, 73, 14);

		txtReason = new JTextPane();
		txtReason.setText(getStackTrace(ex));
		txtReason.setEditable(false);
		scrollPane.setViewportView(txtReason);
		contentPane.setLayout(null);
		contentPane.add(panel);
		contentPane.add(lblErrorMessage);
		contentPane.add(lblAErrorOccured);
		contentPane.add(scrollPane);
		contentPane.add(btnOk);
		contentPane.add(btnDetails);

		btnCopyToClipboard = new JButton("Copy to clipboard");
		btnCopyToClipboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection ss = new StringSelection(txtReason.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
				btnCopyToClipboard.setText("Copied!");
			}
		});
		btnCopyToClipboard.setVisible(false);
		btnCopyToClipboard.setIcon(IconUtils.getIcon("clipboard-go"));
		btnCopyToClipboard.setBounds(95, 94, 143, 24);
		contentPane.add(btnCopyToClipboard);

		String message = ex.getMessage();
		if (message == null || message.length() == 0) {
			message = "Unknown";
		}

		JLabel label = new JLabel(ex.getClass().getSimpleName() + ": " + message);
		label.setBounds(156, 36, 278, 14);
		contentPane.add(label);

		extendedSize = super.getSize();
		originalSize = new Dimension((int) extendedSize.getWidth(), (int) extendedSize.getHeight() - 150);

		setLocationRelativeTo(null);
	}
}