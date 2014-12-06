package se.jrat.common;

public enum Flood {

	NONE(-1),
	UDP(0),
	ARME(1),
	DRAIN(2),
	SLOWLORIS(3);

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
