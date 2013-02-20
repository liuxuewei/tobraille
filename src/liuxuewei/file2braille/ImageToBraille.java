package liuxuewei.file2braille;

import java.io.File;
import java.util.Vector;
import liuxuewei.braille.ToBraille;
import liuxuewei.demo.Demo;
import liuxuewei.ocr.OCR;

/**
 * @author liuxuewei
 * @email mail to<br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *        see also<关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-9-20
 * @function
 * @versions 1.0
 */
public class ImageToBraille {
	/**
	 * @param String
	 *            fileName
	 * @return Vector<String> Brailles
	 */
	public static Vector<String> getBrailleFromImg(File file) {
		OCR ocr = new OCR();
		String toBraille = "";
		try {
			toBraille = ocr.recognizeText(file, "jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vector<String> Brailles = ToBraille.ChineseToBraille(toBraille, false);
		return Brailles;
	}

	/**
	 * @param fileName
	 */
	public static void showBrailleFromImg(File file, Demo demo) {
		demo.showBraille(ImageToBraille.getBrailleFromImg(file));
	}
}
