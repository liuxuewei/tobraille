/**
 *@author liuxuewei
 *@project toBraille
 *@fileName LanguageFactory.java
 */
package liuxuewei.language;

/**
 * @author liuxuewei
 * @email mail to<br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *        see also<关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-9-7
 * @function
 * @versions 1.0
 */
public class LanguageFactory {
	public String getLanguage(int language) {
		switch (language) {
		case 1:
			return "ChineseToBraille";
		case 2:
			return "EnglishToBraille";
		default:
			return "ChineseToBraille";
		}
	}
}
