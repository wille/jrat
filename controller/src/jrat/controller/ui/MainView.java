package jrat.controller.ui;

import jrat.controller.ui.panels.PanelMainClients;
import jrat.controller.ui.panels.PanelMainClientsBoxes;
import jrat.controller.ui.panels.PanelMainClientsTable;

import java.util.ArrayList;
import java.util.List;

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
