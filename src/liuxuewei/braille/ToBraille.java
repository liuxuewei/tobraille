/**
 *@author liuxuewei
 *@project toBraille
 *@fileName toBraille.java
 */
package liuxuewei.braille;

import java.util.Vector;

/**
 * @author liuxuewei
 * @email mail to<br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *        see also<关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-9-11
 * @function
 * @versions 1.0
 */
public class ToBraille {
	public static Vector<String> ChineseToBraille(String src, boolean strategy) {
		if (!src.trim().equals("")) {
			Vector<String> res = ChineseToBraille.toBraille(src, strategy);
			return res;
		} else
			return null;

	}
}
