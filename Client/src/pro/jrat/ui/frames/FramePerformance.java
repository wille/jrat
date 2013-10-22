package pro.jrat.ui.frames;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

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

		setLayout(null);
		setResizable(false);
		setBounds(100, 100, 600, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		graph = new TaskmgrGraph(new TaskmgrColors());
		graph.setBounds(5, 5, this.getWidth() - 30, this.getHeight() - 80);
		add(graph);
		
		new Thread("Memory thread") {
			@Override
			public void run() {
				while (graph.isRunning()) {
					Runtime rt = Runtime.getRuntime();
					
					long current = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
					long max = rt.totalMemory() / 1024L / 1024L;
					int percent =  (int) (((float) current / (float) max) * 100);
					
					
					graph.setMaximum((int) max);
										
					graph.addValue((int) current);
					
					graph.setText(current + " mb");
					
					System.out.println("Current usage: " + current + ", Maximum usage: " + max + ", Percent: " + percent);
					
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
