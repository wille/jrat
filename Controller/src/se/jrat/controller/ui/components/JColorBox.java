package se.jrat.controller.ui.components;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import se.jrat.controller.settings.SettingsColors;
import se.jrat.controller.ui.renderers.JColorBoxRenderer;


@SuppressWarnings("serial")
public class JColorBox extends JComboBox<String> {

	public static final String[] colors = new String[] { "default", "black", "blue", "cyan", "dark gray", "gray", "green", "dark green", "light gray", "magenta", "orange", "pink", "red", "white", "yellow" };

	public JColorBox(ActionListener listener) {
		super(colors);
		super.addActionListener(listener);
		super.setRenderer(new JColorBoxRenderer());
	}

	public JColorBox() {
		super(colors);
		super.setRenderer(new JColorBoxRenderer());
	}

	public JColorBox(SettingsColors.ColorProfile profile) {
		this();
		setProfile(profile);
	}

	public void setProfile(SettingsColors.ColorProfile profile) {
		super.setSelectedIndex(profile.getIndex());
	}

	public SettingsColors.ColorProfile getProfile() {
		SettingsColors.ColorProfile profile = SettingsColors.getGlobal().new ColorProfile();
		profile.setIndex(super.getSelectedIndex());
		return profile;
	}

	public Color getColor() {
		String text = super.getSelectedItem().toString();

		if (text.equals("default")) {
			return null;
		}

		Color color;
		if (text.equals("dark green")) {
			color = Color.green.darker();
		} else {
			try {
				color = (Color) Class.forName("java.awt.Color").getField(text).get(null);
			} catch (Exception e) {
				color = null;
			}
		}
		return color;
	}

}
