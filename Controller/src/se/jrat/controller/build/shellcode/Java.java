package se.jrat.controller.build.shellcode;

public class Java implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append("byte[] " + arrayName + " = new byte[] {" + Util.lineSeparator);

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (i == array.length - 1) {
				builder.append("(byte) " + Util.get0XByte(b));
			} else {
				builder.append("(byte) " + Util.get0XByte(b) + ", ");
				if (count >= 16) {
					count = 0;
					builder.append(Util.lineSeparator);
				}
			}
		}

		builder.append(Util.lineSeparator + "};");

		return builder.toString();
	}

}
