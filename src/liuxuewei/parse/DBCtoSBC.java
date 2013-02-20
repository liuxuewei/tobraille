package liuxuewei.parse;

/**
 *@author liuxuewei
 *@project LearningJava
 *@fileName DBC2SBC.java
 */

/**
 * @author liuxuewei
 * @email mail to<br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *        see also<关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-9-8
 * @function
 * @versions 1.0
 */
public class DBCtoSBC {
	/**
	 * 把字符串中的全角字符串转换成半角字符串 如果仅有半角字符串，则不变
	 * 
	 * @param str
	 * @return
	 */
	public static String Q2BChange(String str) {
		String result = "";
		int code = 0;
		String inputStr = str.replaceAll("^[ |　]*", "")
				.replaceAll("[ |　]$", "").replaceAll("[ |　]", "");
		for (int i = 0; i < inputStr.length(); i++) {
			code = inputStr.codePointAt(i);
			System.out.println(code);
			if (code >= 65281 && code < 65373) {
				// “65248”是转换码距
				result += new String(new int[] { code - 65248 }, 0, 1);
			} else {
				result += inputStr.charAt(i);
			}
		}
		return result;
	}
	// public static void main(String[] args) {
	// System.out.println(DBCtoSBC.Q2BChange("ｗｏ我们wo我们"));
	// }
}
