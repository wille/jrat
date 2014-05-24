package io.jrat.stub.modules.startup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartupModules {
	
	private static final List<Class<? extends StartupModule>> MODULES = new ArrayList<Class<? extends StartupModule>>();
	
	static {
		MODULES.add(OSXToolBarStartupModule.class);
		MODULES.add(OperatingSystemCheckStartupModule.class);
	}
	
	public static void execute(Map<String, String> config) throws Exception {
		for (int i = 0; i < MODULES.size(); i++) {
			Class<? extends StartupModule> childClass = MODULES.get(i);
			Constructor<? extends StartupModule> c = childClass.getDeclaredConstructor(Map.class);
			c.setAccessible(true);
			
			StartupModule instance = c.newInstance(new Object[] { config });
			instance.run();
		}
	}

}
