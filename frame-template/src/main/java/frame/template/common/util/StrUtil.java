package frame.template.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StrUtil {
	public static final String SPACE = " ";
	public static final String EMPTY = "";
	public static final String SPLITER = "###";
	public static final String COMMA = ",";
	public static final String DOT = ".";

	public static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";

	private static final String YES = "1";
	private static final String NO = "0";

	public static boolean equals(String a, String b) {
		if (a == null || b == null) {
			return false;
		} else {
			return a.equals(b);
		}
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if (a == null || b == null) {
			return false;
		} else {
			return a.equalsIgnoreCase(b);
		}
	}

	public static String join(String... str) {
		return joinWith(SPLITER, str);
	}

	public static String joinWith(String spliter, String... str) {
		StringBuilder sb = new StringBuilder();
		for (String s : str) {
			sb.append(spliter).append(s);
		}
		return sb.toString().replaceFirst(spliter, "");
	}

	public static String joinWith(String spliter, List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(spliter).append(s);
		}
		return sb.toString().replaceFirst(spliter, "");
	}

	public static List<String> split(String str, String spliter) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String[] strArr = str.split(spliter);
		if (strArr.length == 0) {
			return null;
		}
		List<String> list = new ArrayList<>();
		for (String s : strArr) {
			if (StringUtils.isNotBlank(s)) {
				list.add(s);
			}
		}
		return list;
	}

	public static String convertSwitch(boolean bool) {
		return bool ? YES : NO;
	}

	public static boolean convertSwitch(String str) {
		return YES.equals(str);
	}

	public static String cleanScriptFormat(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		Pattern pattern = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		return matcher.replaceAll("");
	}
}
