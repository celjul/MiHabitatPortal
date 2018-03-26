package com.bstmexico.mihabitat.comunes.test.utils;

public class StringUtilsTest {

	public static String insertChars(String string, int chars) {
		StringBuilder builder = new StringBuilder(string);
		for (int i = string.length(); i <= chars; i++) {
			builder.append("x");
		}
		return builder.toString();
	}
}