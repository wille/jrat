package se.jrat.controller.ui.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;

import se.jrat.controller.AbstractSlave;

@SuppressWarnings("serial")
public class PanelMainClientsBoxes extends PanelMainClients {
	
	public PanelMainClientsBoxes() {
	
		
		setViewportView(null);
	}
	
	@Override
	public String getViewName() {
		return "Boxes";
	}
	
	@Override
	public void addSlave(AbstractSlave slave) {

	}
	
	@Override
	public void removeSlave(AbstractSlave slave) {

	}

	@Override
	public AbstractSlave getSlave(int row) {
		return null;
	}
	
	@Override
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<AbstractSlave>();

		return list;
	}
	
	@Override
	public AbstractSlave getSelectedSlave() {
		return null;
	}
	
	@Override
	public void clear() {
		
	}

	@Override
	public JMenu getConfigMenu() {
		JMenu menu = new JMenu("Customize");
		
		return menu;
	}

}
