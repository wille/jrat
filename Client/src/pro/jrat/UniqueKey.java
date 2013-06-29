package pro.jrat;

import pro.jrat.common.codec.Hex;
import pro.jrat.common.hash.Sha256;

public final class UniqueKey {

	public static final byte[] getBinary() throws Exception {
		String name = System.getProperty("user.name");
		String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
		String country = System.getProperty("user.country") + " " + System.getProperty("user.country.format") + " " + System.getProperty("user.language");

		String data = "&name=" + name + "&os=" + os + "&geo=" + country;
		
		return Sha256.sha256(data.getBytes("UTF-8"));	
	}

	public static final String getTextual() throws Exception {
		return Hex.encode(getBinary());
	}

}
