package se.jrat.controller.ui.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import se.jrat.controller.AbstractSlave;

@SuppressWarnings("serial")
public class PanelMainClientsBoxes extends PanelMainClients {
	
	private JDesktopPane pane;
	
	public PanelMainClientsBoxes() {
		pane = new JDesktopPane();
		
		setViewportView(pane);
	}
	
	@Override
	public String getViewName() {
		return "Boxes";
	}
	
	@Override
	public void addSlave(AbstractSlave slave) {
		pane.add(new SlaveBox(slave));
	}
	
	@Override
	public void removeSlave(AbstractSlave slave) {
		for (JInternalFrame c : pane.getAllFrames()) {
			assert c instanceof SlaveBox;
			
			SlaveBox box = (SlaveBox) c;
			
			if (box.getSlave().equals(slave)) {
				pane.remove(c);
			}
		}
	}

	@Override
	public AbstractSlave getSlave(int row) {
		return null;
	}
	
	@Override
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<AbstractSlave>();

		AbstractSlave selected = getSelectedSlave();
		
		if (selected != null) {
			list.add(selected);
		}
		
		return list;
	}
	
	@Override
	public AbstractSlave getSelectedSlave() {
		AbstractSlave selected = null;
		
		for (JInternalFrame c : pane.getAllFrames()) {
			assert c instanceof SlaveBox;
			
			SlaveBox box = (SlaveBox) c;
			
			if (box.isSelected()) {
				selected = box.getSlave();
				break; // only one frame can be active
			}
		}
		
		return selected;
	}
	
	@Override
	public void clear() {
		for (JInternalFrame c : pane.getAllFrames()) {
			pane.remove(c);
		}
	}

	@Override
	public JMenu getConfigMenu() {
		JMenu menu = new JMenu("Customize");
		
		return menu;
	}
	
	private class SlaveBox extends JInternalFrame {
		
		private AbstractSlave slave;
		
		public SlaveBox(AbstractSlave slave) {
			this.slave = slave;
		}
		
		public AbstractSlave getSlave() {
			return this.slave;
		}
	}

}
