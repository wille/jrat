package pro.jrat.api.events;

public class OnEnableEvent implements Event {

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
