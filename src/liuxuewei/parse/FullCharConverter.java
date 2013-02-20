package liuxuewei.parse;

import java.io.UnsupportedEncodingException;

/**
 * 特殊字符串转换
 * 
 * @Class Name FullCharConverter
 * @Author xuewei
 * @Create In 2012-8-24
 */
public class FullCharConverter {

	/**
	 * 全角转半角的 转换函数
	 * 
	 * @Methods Name full2HalfChange
	 * @Create In 2012-8-24 By xuewei
	 * @param QJstr
	 * @return String
	 */
	public static final String full2HalfChange(String QJstr) {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}
			try {
				b = Tstr.getBytes("unicode");
				// 得到 unicode 字节数据
				if (b[2] == -1) {
					// 表示全角
					b[3] = (byte) (b[3] + 32);
					b[2] = 0;
					outStrBuf.append(new String(b, "unicode"));
				} else {
					outStrBuf.append(Tstr);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} // end for.
		return outStrBuf.toString();
	}

	/**
	 * 半角转全角
	 * 
	 * @Methods Name half2Fullchange
	 * @Create In 2012-8-24 By xuewei
	 * @param QJstr
	 * @return String
	 */
	public static final String half2Fullchange(String QJstr) {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			Tstr = QJstr.substring(i, i + 1);
			if (Tstr.equals(" ")) {
				// 半角空格
				outStrBuf.append(Tstr);
				continue;
			}
			try {
				b = Tstr.getBytes("unicode");
				if (b[2] == 0) {
					// 半角
					b[3] = (byte) (b[3] - 32);
					b[2] = -1;
					outStrBuf.append(new String(b, "unicode"));
				} else {
					outStrBuf.append(Tstr);
				}
				return outStrBuf.toString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return outStrBuf.toString();
	}

	// /**
	// * @Methods Name main
	// * @Create In 2012-8-24 By xuewei
	// * @param args
	// * void
	// */
	// public static void main(String[] args) {
	// //全角转半角
	// String QJstr = "８１４乡道";
	// System.out.println("全角："+QJstr);
	// System.out.println("转换成半角："+FullCharConverter.full2HalfChange(QJstr));
	// System.out.println("------------------------------------");
	// // 半角转全角
	// String BJstr = "G45大广高速";
	// System.out.println("半角："+BJstr);
	// System.out.println(FullCharConverter.half2Fullchange(BJstr));
	// }
}
