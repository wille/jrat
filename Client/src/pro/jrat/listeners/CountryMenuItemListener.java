package pro.jrat.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import pro.jrat.utils.NetworkUtils;




public class CountryMenuItemListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		NetworkUtils.openUrl("http://en.wikipedia.org/wiki/" + item.getText().replace("Country: ", ""));
	}

}
