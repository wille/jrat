package se.jrat.controller.ip2c;

public class Country {
	private short m_2c;
	private int m_3c;
	private String m_name;

	public Country(short paramShort, int paramInt, String paramString) {
		this.m_2c = paramShort;
		this.m_3c = paramInt;
		this.m_name = paramString;
	}

	public Country(String paramString1, String paramString2, String paramString3) {
		if (paramString1.length() != 2)
			throw new IllegalArgumentException("Invalid id2c : '" + paramString1 + "'");
		if (paramString2.length() > 3)
			throw new IllegalArgumentException("Invalid id3c : '" + paramString2 + "'");
		this.m_2c = ((short) (paramString1.charAt(0) << '\b' | paramString1.charAt(1)));
		if (paramString2.length() == 0)
			paramString2 = "   ";
		if (paramString2.length() == 3)
			this.m_3c = (paramString2.charAt(0) << '\020' | paramString2.charAt(1) << '\b' | paramString2.charAt(2));
		else if (paramString2.length() == 2)
			this.m_3c = (paramString2.charAt(0) << '\b' | paramString2.charAt(1) << '\000');
		this.m_name = paramString3;
	}

	public short get2c() {
		return this.m_2c;
	}

	public int get3c() {
		return this.m_3c;
	}

	public String getName() {
		return this.m_name;
	}

	public String toString() {
		String str1 = get2cStr(this.m_2c);
		String str2 = get3cStr(this.m_3c);
		return "id2=" + str1 + ", id3c=" + str2 + ", name=" + this.m_name;
	}

	public String get3cStr() {
		return get3cStr(this.m_3c);
	}

	public String get2cStr() {
		return get2cStr(this.m_2c);
	}

	public static String get3cStr(int paramInt) {
		String str = new String(new byte[] { (byte) (paramInt >> 16), (byte) (paramInt >> 8), (byte) paramInt });
		if (str.equals("   "))
			return "";
		return str;
	}

	public static String get2cStr(short paramShort) {
		return new String(new byte[] { (byte) (paramShort >> 8), (byte) (paramShort & 0xFF) });
	}

	public boolean equals(Object paramObject) {
		if ((paramObject instanceof Country)) {
			Country localCountry = (Country) paramObject;
			return (localCountry.m_2c == this.m_2c) && (localCountry.m_3c == localCountry.m_3c) && (localCountry.m_name.equals(this.m_name));
		}
		return false;
	}
}
