package pro.jrat.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import pro.jrat.utils.NetUtils;




public class CountryMenuItemListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		NetUtils.openUrl("http://en.wikipedia.org/wiki/" + item.getText().replace("Country: ", ""));
	}

}
