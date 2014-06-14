package su.jrat.client.extensions;

import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;

import jrat.api.PluginClassLoader;
import jrat.api.RATControlMenuEntry;
import jrat.api.RATMenuItem;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisableEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnEnableEvent;
import jrat.api.events.OnPacketEvent;
import jrat.api.events.OnSendPacketEvent;
import jrat.api.utils.JarUtils;
import su.jrat.client.Main;
import su.jrat.common.Logger;
import su.jrat.common.Version;

@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class Plugin {

	private ClassLoader loader;
	private Class classToLoad;
	private Object instance;
	private ActionListener globalItemListener;

	private String name;
	private String version;
	private String author;
	private String description;

	private String jarname;

	private HashMap<String, Method> methods = new HashMap<String, Method>();
	private List<RATMenuItem> items = new ArrayList<RATMenuItem>();
	private List<RATControlMenuEntry> controlitems = new ArrayList<RATControlMenuEntry>();

	public Plugin(File jar) throws Exception {
		this(jar.getAbsolutePath());
	}

	public Plugin(String file) throws Exception {
		setJarName(file);
		setLoader(new PluginClassLoader(new URLClassLoader(new URL[] { new File(file).toURL() }, Main.class.getClassLoader())));

		String mainClass;

		try {
			mainClass = JarUtils.getMainClassFromInfo(new JarFile(file));
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.log("Failed loading main class from plugin.txt, trying meta-inf");
			mainClass = JarUtils.getMainClass(new JarFile(file));
		}

		setClassToLoad(Class.forName(mainClass, true, getLoader()));
		setInstance(getClassToLoad().newInstance());

		Method onLoad = getClassToLoad().getMethod("onEnable", new Class[] { OnEnableEvent.class });
		onLoad.invoke(getInstance(), new Object[] { new OnEnableEvent(Version.getVersion()) });
		getMethods().put("onenable", onLoad);

		setVersion(getClassToLoad().getMethod("getVersion", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setAuthor(getClassToLoad().getMethod("getAuthor", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setDescription(getClassToLoad().getMethod("getDescription", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setName(getClassToLoad().getMethod("getName", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		this.setGlobalItemListener((ActionListener) getClassToLoad().getMethod("getGlobalMenuItemListener", new Class[] {}).invoke(getInstance(), new Object[] {}));

		List<RATMenuItem> menuitems = (List<RATMenuItem>) getClassToLoad().getMethod("getMenuItems", new Class[] {}).invoke(getInstance(), new Object[] {});
		if (menuitems != null) {
			setItems(menuitems);
		}

		List<RATControlMenuEntry> citems = (List<RATControlMenuEntry>) getClassToLoad().getMethod("getControlTreeItems", new Class[] {}).invoke(getInstance(), new Object[] {});
		if (citems != null) {
			setControlitems(citems);
		}

		Method onPacket = getClassToLoad().getMethod("onPacket", new Class[] { OnPacketEvent.class });
		getMethods().put("onpacket", onPacket);

		Method onConnect = getClassToLoad().getMethod("onConnect", new Class[] { OnConnectEvent.class });
		getMethods().put("onconnect", onConnect);

		Method onDisconnect = getClassToLoad().getMethod("onDisconnect", new Class[] { OnDisconnectEvent.class });
		getMethods().put("ondisconnect", onDisconnect);

		Method onDisable = getClassToLoad().getMethod("onDisable", new Class[] { OnDisableEvent.class });
		getMethods().put("ondisable", onDisable);

		Method onSendPacket = getClassToLoad().getMethod("onSendPacket", new Class[] { OnSendPacketEvent.class });
		getMethods().put("onsendpacket", onSendPacket);

		Main.debug("Plugin " + getName() + " " + getVersion() + " enabled");

		PluginLoader.register(this);
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader classLoader) {
		this.loader = classLoader;
	}

	public Class getClassToLoad() {
		return classToLoad;
	}

	public void setClassToLoad(Class classToLoad) {
		this.classToLoad = classToLoad;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getJarname() {
		return jarname;
	}

	public void setJarName(String jarname) {
		this.jarname = jarname;
	}

	public HashMap<String, Method> getMethods() {
		return methods;
	}

	public void setMethods(HashMap<String, Method> methods) {
		this.methods = methods;
	}

	public List<RATMenuItem> getItems() {
		return items;
	}

	public void setItems(List<RATMenuItem> items) {
		this.items = items;
	}

	public List<RATControlMenuEntry> getControlItems() {
		return controlitems;
	}

	public void setControlitems(List<RATControlMenuEntry> controlitems) {
		this.controlitems = controlitems;
	}

	public ActionListener getGlobalItemListener() {
		return globalItemListener;
	}

	public void setGlobalItemListener(ActionListener globalItemListener) {
		this.globalItemListener = globalItemListener;
	}

	public boolean equals(Object o) {
		if (o instanceof Plugin) {
			Plugin plugin = (Plugin) o;

			return plugin.name.equals(this.name);
		}

		return false;
	}

}
