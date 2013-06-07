package com.redpois0n.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.redpois0n.utils.NetworkUtils;



public class CountryMenuItemListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		NetworkUtils.openUrl("http://en.wikipedia.org/wiki/" + item.getText().replace("Country: ", ""));
	}

}
