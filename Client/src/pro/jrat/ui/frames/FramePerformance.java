package pro.jrat.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import pro.jrat.Constants;
import pro.jrat.common.Version;
import pro.jrat.threads.ThreadLocalRAM;
import pro.jrat.ui.panels.PanelImage;
import pro.jrat.ui.renderers.MemoryMeter;


@SuppressWarnings("serial")
public class FramePerformance extends BaseFrame {

	private JPanel contentPane;
	public PanelImage panel;
	public MemoryMeter ramMeter = new MemoryMeter(Color.blue);
	public static FramePerformance instance;
	public JProgressBar barRAM;
	public JLabel lblMaxRam;
	public JLabel lblUsedRam;

	public FramePerformance() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		instance = this;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePerformance.class.getResource("/icons/meter.png")));
		setTitle(Constants.NAME + " " + Version.getVersion() + " Performance");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new PanelImage();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Color c = JColorChooser.showDialog(null, "Select color", ramMeter.color);
				ramMeter.color = c;
				barRAM.setForeground(c);
			}
		});

		barRAM = new JProgressBar();
		barRAM.setForeground(ramMeter.color);
		barRAM.setStringPainted(true);

		lblMaxRam = new JLabel("Max ram:");

		lblUsedRam = new JLabel("Used ram:");

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
				exit();
			}
		});
		btnClose.setIcon(new ImageIcon(FramePerformance.class.getResource("/icons/delete.png")));

		JButton btnGarbageCollector = new JButton("Garbage Collector");
		btnGarbageCollector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.gc();
			}
		});
		btnGarbageCollector.setIcon(new ImageIcon(FramePerformance.class.getResource("/icons/garbage.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(barRAM, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(btnGarbageCollector)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClose)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMaxRam)
							.addGap(43)
							.addComponent(lblUsedRam)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(barRAM, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGarbageCollector)
						.addComponent(btnClose)
						.addComponent(lblMaxRam)
						.addComponent(lblUsedRam)))
		);
		contentPane.setLayout(gl_contentPane);
		new ThreadLocalRAM().start();
	}

	public void exit() {
		instance = null;
	}
}
