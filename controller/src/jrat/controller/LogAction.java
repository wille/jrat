package jrat.controller;

import jrat.api.Resources;

import javax.swing.*;
import java.awt.*;

public enum LogAction {
		
	CONNECT("Connect", Color.blue, Resources.getIcon("log-info")),
	DISCONNECT("Disconnect", Color.red, Resources.getIcon("log-error")),
	ERROR("Error", Color.red, Resources.getIcon("log-error")),
	WARNING("Warning", Resources.getIcon("log-warning"));
	
	private String text;
	private Color color;
	private Icon icon;
	
	LogAction(String text, Icon icon) {
		this(text, Color.black, icon);
	}
	
	LogAction(String text, Color color, Icon icon) {
		this.text = text;
		this.color = color;
		this.icon = icon;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Icon getIcon() {
		return this.icon;
	}

}
