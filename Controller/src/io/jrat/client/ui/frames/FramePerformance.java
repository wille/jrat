package io.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redpois0n.graphs.taskmgr.TaskmgrColors;
import com.redpois0n.graphs.taskmgr.TaskmgrGraph;

@SuppressWarnings("serial")
public class FramePerformance extends BaseFrame {

	private TaskmgrGraph graph;

	public FramePerformance() {
		super();
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				graph.dispose();
			}

		});

		setTitle("Performance");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/meter.png")));
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(100, 100, 600, 426);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final JLabel lblMaximum = new JLabel("Maximum: ");
		lblMaximum.setBounds(150, 270, 100, 10);
		getContentPane().add(lblMaximum);

		JButton button = new JButton("Garbage Collect");
		button.setBounds(10, 260, 130, 30);
		button.setIcon(new ImageIcon(Frame.class.getResource("/icons/garbage.png")));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.gc();
			}
		});

		getContentPane().add(button);

		graph = new TaskmgrGraph(new TaskmgrColors());
		graph.setBounds(0, 0, 593, 250);
		getContentPane().add(graph);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Runtime info"));
		panel.setBounds(10, 301, 574, 85);
		getContentPane().add(panel);
		
		JLabel lblWorkingDirectory = new JLabel("Working Directory: " + System.getProperty("user.dir"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWorkingDirectory)
					.addContainerGap(264, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWorkingDirectory)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		new Thread("Memory thread") {
			@Override
			public void run() {
				while (graph.isRunning()) {
					Runtime rt = Runtime.getRuntime();

					long current = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
					long max = rt.totalMemory() / 1024L / 1024L;

					graph.setMaximum((int) max);
					lblMaximum.setText("Maximum: " + max + " mb");

					graph.addValue((int) current);

					graph.setText(current + " mb");

					try {
						Thread.sleep(1000L);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
