package com.redpois0n.build.shellcode;

public interface Shellcode {

	public abstract String generate(String arrayName, byte[] array) throws Exception;
}
