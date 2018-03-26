package com.bstmexico.mihabitat.condominios.test.utils;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2014
 */
public class StringUtilsTest {
	public static String insertChars(String string, int chars) {
		StringBuilder builder = new StringBuilder(string);
		for (int i = string.length(); i <= chars; i++) {
			builder.append("x");
		}
		return builder.toString();
	}
}
