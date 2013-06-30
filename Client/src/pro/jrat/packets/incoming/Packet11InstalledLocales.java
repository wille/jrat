package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Locale;
import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlLocales;


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