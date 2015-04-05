package se.jrat.controller.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.jrat.controller.utils.IconUtils;

import com.redpois0n.graphs.taskmgr.TaskmgrColors;
import com.redpois0n.graphs.taskmgr.TaskmgrGraph;

@SuppressWarnings("serial")
public class FramePerformance extends JFrame {

	private TaskmgrGraph graph;

	public FramePerformance() {
		super.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				graph.dispose();
			}

		});

		setTitle("Performance");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/icons/meter.png")));
		setResizable(false);
		setBounds(100, 100, 600, 426);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final JLabel lblMaximum = new JLabel("Maximum: ");

		JButton button = new JButton("Garbage Collect");
		button.setIcon(IconUtils.getIcon("garbage"));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.gc();
			}
		});

		graph = new TaskmgrGraph(new TaskmgrColors());
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Runtime info"));
		
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
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(graph, GroupLayout.PREFERRED_SIZE, 593, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblMaximum, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 574, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(graph, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblMaximum, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(groupLayout);

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
