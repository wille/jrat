package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Locale;
import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameControlPanel;
import su.jrat.client.ui.panels.PanelControlLocales;


public class Packet11InstalledLocales extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		int count = slave.readInt();

		Locale[] locales = new Locale[count];

		for (int i = 0; i < count; i++) {
			Locale locale = new Locale();

			locale.setCountry(slave.readLine());
			locale.setDisplaycountry(slave.readLine());
			locale.setLanguage(slave.readLine());
			locale.setDisplaylanguage(slave.readLine());

			locales[i] = locale;
		}

		slave.setLocales(locales);

		FrameControlPanel frame = FrameControlPanel.instances.get(slave);

		if (frame != null) {
			PanelControlLocales panel = (PanelControlLocales) frame.panels.get("locales");

			if (panel != null) {
				panel.load();
			}
		}
	}

}
