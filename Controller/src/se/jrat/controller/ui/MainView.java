package se.jrat.controller.ui;

import java.util.ArrayList;
import java.util.List;

import se.jrat.controller.ui.panels.PanelMainClients;
import se.jrat.controller.ui.panels.PanelMainClientsBoxes;
import se.jrat.controller.ui.panels.PanelMainClientsTable;

public class MainView {
	
	public static final List<PanelMainClients> VIEWS = new ArrayList<PanelMainClients>();
	
	static {
		VIEWS.add(new PanelMainClientsTable());
		VIEWS.add(new PanelMainClientsBoxes());
	}
	
	public static PanelMainClients get(String s) {
		for (PanelMainClients view : VIEWS) {
			if (s.equalsIgnoreCase(view.getViewName())) {
				return view;
			}
		}
		
		return null;
	}

}
