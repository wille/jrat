package se.jrat.client.ui.dialogs;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.jrat.client.listeners.ToolsDownloadListener;
import se.jrat.common.utils.DataUnits;


@SuppressWarnings("serial")
public class DialogDownload extends BaseDialog {

	private JLabel lblKb;
	private JProgressBar progressBar;

	public JProgressBar getBar() {
		return progressBar;
	}

	public DialogDownload(ToolsDownloadListener listener) {
		super();
		setTitle("Downloading...");
		setResizable(false);
		setBounds(100, 100, 398, 138);

		progressBar = new JProgressBar();

		JButton btnHide = new JButton("Hide");

		JLabel lblDownloading = new JLabel("Downloading... " + listener.getUrl());

		lblKb = new JLabel("0/0 B");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE).addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addComponent(lblKb).addPreferredGap(ComponentPlacement.RELATED, 273, Short.MAX_VALUE).addComponent(btnHide)).addComponent(lblDownloading)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(16).addComponent(lblDownloading).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnHide).addComponent(lblKb)).addContainerGap(12, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}

	public void setKb(int done, int all) {
		String d = DataUnits.getAsString((long) done);
		String a = DataUnits.getAsString((long) all);
		lblKb.setText(d + "/" + a);
	}
}
