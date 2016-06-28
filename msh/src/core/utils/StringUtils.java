package core.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public class StringUtils {

	public static String urlEncode(String value) {
		if (value == null) return null;
		try {
			return URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	public static String urlDecode(String value) {
		if (value == null) return null;
		try {
			return URLDecoder.decode(value, "utf-8");
		} catch (UnsupportedEncodingException ignored) {
			return value;
		}
	}

	/**
	 * 生成bit位的随机数
	 *
	 * @param bit 位数
	 * @return 随机数
	 */
	public static String random(int bit) {
		int min = (int) (Math.pow(10, bit - 1));
		int max = (int) (Math.pow(10, bit) - 1);
		return String.valueOf(random(min, max));
	}

	/**
	 * 生成bit位的随机数，加前缀
	 *
	 * @param bit    位数
	 * @param prefix 前缀
	 * @return 随机数
	 */
	public static String random(int bit, String prefix) {
		return prefix + random(bit);
	}

	/**
	 * 生成min到max之间的随机数
	 *
	 * @param min 最小值
	 * @param max 最大值
	 * @return 随机数
	 */
	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
	}

	public static String randomStr(int length) {
		Random random = new Random();
		String TEMPLATE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < length; i++) {
			sb.append(TEMPLATE.charAt(random.nextInt(TEMPLATE.length())));
		}
		return sb.toString();
	}

	public static String readStream(InputStream in) {
		if (in == null) return null;

		InputStreamReader is = null;
		StringBuilder content = new StringBuilder();
		String line;
		try {
			is = new InputStreamReader(in, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(is);
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
		} catch (IOException e) {
			try {
				if (is != null) is.close();
			} catch (IOException ignored) {
			}
		}
		return content.toString();
	}

	public static String format(String value, Object... args) {
		if (value == null) return "";
		return String.format(value, args);
	}

	public static String mapToString(Map map) {
		if (map == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder("{");
		for (Object key : map.keySet()) {
			Object value = map.get(key);
			sb.append(key).append("=");
			if (value instanceof Object[]) {
				Object[] objAry = (Object[]) value;
				sb.append(Arrays.toString(objAry));
			} else {
				sb.append(value);
			}
			sb.append(", ");
		}
		if (map.isEmpty()) {
			return sb.append("}").toString();
		} else {
			return sb.substring(0, sb.length() - 2) + "}";
		}
	}

	public static String convertToHtmlTag(String string, String split) {
		if (string == null) {
			return null;
		}
		if (split == null) {
			return string;
		}
		return convertToHtmlTag(string.split(split));
	}

	public static String convertToHtmlTag(String[] arrays) {
		if (arrays == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String value : arrays) {
			sb.append("<p>").append(value).append("</p>");
		}
		return sb.toString();
	}

	public static String securityTelephone(String telephone, int start, int length, char replace) {
		if (telephone == null || telephone.length() != 11) return "";
		int end = start + length;
		if (11 < end || end < start) return "";
		String securityTelephone = telephone.substring(0, start);
		for (int i = start; i < end; i++) {
			securityTelephone += replace;
		}
		securityTelephone += telephone.substring(end, 11);
		return securityTelephone;
	}

	public static String securityTelephone(String telephone) {
		return securityTelephone(telephone, 3, 6, '*');
	}

	public static boolean parseBoolean(String val) {
		return val != null && (val.equalsIgnoreCase("true") || val.equals("1"));
	}

	public static int parseInt(String val) {
		return ValidationUtils.isPositiveNumber(val) ? Integer.parseInt(val) : 0;
	}

	public static boolean intAnd(Integer _this, Integer value) {
		return !(_this == null || value == null) && (value & _this) == _this;
	}

	public static boolean inList(Object obj, List list) {
		return !(list == null || obj == null) && list.contains(obj);
	}

	public static JSONObject jsonObject(String jsonString) {
		try {
			return JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	public static boolean splitEquals(String input, String delimiters, String value) {
		if (input == null || delimiters == null || value == null) return false;
		String[] results = input.split(delimiters);
		for (String result : results) {
			if (value.equals(result)) {
				return true;
			}
		}
		return false;
	}

	public static Collection convertJSONArray(String array) {
		if (array == null) return Collections.emptyList();
		if (JSONUtils.mayBeJSON(array)) {
			return JSONArray.fromObject(array);
		}
		return Collections.emptyList();
	}

	public static String removeEmoji(String text) {
		return text;
		//return EmojiUtil.removeEmoji(text);
	}


	/**
	 * 传入播放数组 转换成[{"playList":"****"},{},{}..]形式的String 用于转换json数组
	 */
	public static String getPlayLists(String[] playList) {
		StringBuilder str = new StringBuilder();
		str.append("[");
		if (playList != null && playList.length > 0) {
			for (String aPlayList : playList) {
				if (ValidationUtils.isNotBlank(aPlayList)) {
					str.append("{\"playList\":\"").append(aPlayList).append("\"},");
				}
			}
			if (str.toString().endsWith(",")) {
				str.delete(str.length() - 1, str.length());
			}
		}
		str.append("]");
		return str.toString();
	}

	// 数字补零
	public static String fixNumberZero(int value, int len) {
		return fixNumberZero(String.valueOf(value), len);
	}

	public static String fixNumberZero(String value, int len) {
		int valueLen = value.length();
		String fix = "";
		if (len > valueLen) {
			for (int i = 0, s = len - valueLen; i < s; i++) {
				fix += '0';
			}
		}
		return fix + value;
	}

	/**
	 * 分隔字符串
	 *
	 * @param value 传入的字符串
	 * @param bit   隔多少位
	 */
	public static String splitByBit(String value, int bit) {
		if (value == null) return null;

		String result = "";
		while (value.length() > bit) {
			result += value.substring(0, bit) + " ";
			value = value.substring(bit);
		}
		result += value;
		return result;
	}

	/**
	 * 将集合按指定符号串联
	 */
	public static String join(Collection collection, String separator) {
		if (collection.isEmpty()) return "";
		if (separator == null) separator = "";
		String result = "";
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			result += next.toString();
			if (iterator.hasNext()) {
				result += separator;
			}
		}
		return result;
	}

	public static String join(Object[] array, String separator) {
		if (array == null) return "";
		int len = array.length;
		if (len == 0) return "";
		if (separator == null) separator = "";
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(separator);
		}
		return buf.append(array[len - 1]).toString();
	}

	public static String cutString(String val, int len) {
		if (ValidationUtils.isEmpty(val) || val.length() <= len) return val;
		return val.substring(0, len);
	}

	/**
	 * 是否是ean-13条形码
	 * ean-13条码算法:
	 * 前12位的奇数位的和odd
	 * 前12位的偶数位的和even
	 * 将奇数和跟偶数和的三倍相加
	 * 取结果的个位数，对十取余 （如果个位数是0，那么校验码不是10，而是0）
	 */
	public static boolean isEan13(String code) {
		if (code.length() != 13) {
			return false;
		}
		int odd = 0;
		int even = 0;
		for (int i = 0; i < 12; i += 2) {
			char c = code.charAt(i);
			int n = c - '0';
			odd += n;
		}
		for (int i = 1; i < 12; i += 2) {
			char c = code.charAt(i);
			int n = c - '0';
			even += n;
		}
		int cc = odd + even * 3;
		int check = cc % 10;
		if (check == code.charAt(12) - '0') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是json字符串
	 */
	public static boolean isJson(String value) {
		try {
			JSONObject.fromObject(value);
		} catch (JSONException e) {
			try {
				JSONArray.fromObject(value);
			} catch (JSONException e1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Javascript 编码（转义）
	 *
	 * @param input 字符串
	 */
	public static String escapeJavascript(String input) {
		return StringEscapeUtils.escapeJavaScript(input);
	}

	/**
	 * 替换${xxx}内容
	 *
	 * @param value  string
	 * @param params map
	 */
	public static String replacePlaceholder(String value, Map params) {
		try {
			return new PropertyPlaceholderHelper("${", "}").replacePlaceholders(value, new MapPlaceholderResolver(params));
		} catch (Exception e) {
			return value;
		}
	}

	private static class MapPlaceholderResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

		private Map params = new HashMap<>();

		public MapPlaceholderResolver(Map params) {
			this.params = params;
		}

		@Override
		public String resolvePlaceholder(String placeholderName) {
			Object value = params.get(placeholderName);
			return value == null ? null : value.toString();
		}
	}
}
