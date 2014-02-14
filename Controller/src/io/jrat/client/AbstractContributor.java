package io.jrat.client;

public abstract class AbstractContributor {

	private final String name;
	private final String country;
	private final String reason;

	public AbstractContributor(String name, String country, String reason) {
		this.name = name;
		this.country = country;
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

}
