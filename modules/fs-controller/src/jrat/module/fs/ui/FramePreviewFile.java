package jrat.module.fs.ui;

import iconlib.IconUtils;
import jrat.controller.Slave;
import jrat.controller.ui.frames.BaseFrame;
import jrat.module.fs.packets.Packet60PreviewFile;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class FramePreviewFile extends BaseFrame {

	public static final Map<String, FramePreviewFile> INSTANCES = new HashMap<String, FramePreviewFile>();

	private JPanel contentPane;
	private String file;
	private int line = 0;
	private JTextPane textPane;
	private JButton btnClearreset;

	public JTextPane getPane() {
		return textPane;
	}

	public FramePreviewFile(Slave slave, String f) {
		super(slave);
		final Slave sl = slave;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePreviewFile.class.getResource("/file.png")));
		setTitle("Preview file - " + f);
		this.file = f;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		INSTANCES.put(file, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 543, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReadMore = new JButton("Read more");
		btnReadMore.setIcon(IconUtils.getIcon("transfer"));
		btnReadMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sl.addToSendQueue(new Packet60PreviewFile(file, line++));
			}
		});

		btnClearreset = new JButton("Clear/Reset");
		btnClearreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setText("");
				line = 0;
			}
		});
		btnClearreset.setIcon(IconUtils.getIcon("clear"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addComponent(btnReadMore).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClearreset))).addGap(1)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnReadMore).addComponent(btnClearreset)).addGap(4)));

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		contentPane.setLayout(gl_contentPane);
	}

	public void exit() {
		INSTANCES.remove(file);
	}

}
