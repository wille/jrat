package org.jrat.project.api.events;

public class OnEnableEvent {

	private String version;

	public OnEnableEvent(String v) {
		this.version = v;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
