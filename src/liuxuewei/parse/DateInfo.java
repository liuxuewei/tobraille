/**
 *@author liuxuewei
 *@project toBraille
 *@fileName DateInfo.java
 */
package liuxuewei.parse;

import java.util.Calendar;

/**
 * @author liuxuewei
 * @email <strong>mail to</strong><br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *		  <strong>see also</strong><关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-11-3
 * @function 
 * @versions 1.0 
 */
public class DateInfo {
	@SuppressWarnings("deprecation")
	public static String getDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
	}
}
