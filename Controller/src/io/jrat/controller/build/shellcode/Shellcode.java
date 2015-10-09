package io.jrat.controller.build.shellcode;

public interface Shellcode {

	public abstract String generate(String arrayName, byte[] array) throws Exception;
}
