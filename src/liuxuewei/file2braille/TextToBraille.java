/**
 *@author liuxuewei
 *@project toBraille
 *@fileName TextToBraille.java
 */
package liuxuewei.file2braille;

import java.io.File;
import java.util.Vector;

import liuxuewei.braille.ToBraille;
import liuxuewei.demo.Demo;

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
public class TextToBraille {
	/**
	 * @param String
	 *            fileName
	 * @return
	 * @return Vector<String> Brailles
	 */
	public static Vector<String> getBrailleFromText(File file) {
		
		// 方案一
		/*StringBuffer toBraille = new StringBuffer();
		byte[] b = new byte[128];
		int hasread;
		FileInputStream reader = null;
		try {
			reader = new FileInputStream(file);
			while ((hasread = reader.read(b)) != -1) {
				toBraille.append(new String(b, 0, hasread));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		System.out.println(toBraille.toString());*/
		
		// 方案二
	/*	FileReader fr = null;
		BufferedReader bfr=null;
		try {
			fr = new FileReader(file);
			bfr = new BufferedReader(fr);
			String str = new String();
			while ((str = bfr.readLine()) != null) {
				toBraille.append(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bfr!=null){
				try {
					bfr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						fr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println(toBraille.toString());*/

		// 方案三
		String textCode = FileCode.guessEncoding(file);
		String readText = FileCode.read(file, textCode);
		Vector<String> Brailles = ToBraille.ChineseToBraille(
				readText.toString(), false);
		return Brailles;
	}

	/**
	 * @param fileName
	 */
	public static void showBrailleFromText(File file, Demo demo) {
		demo.showBraille(TextToBraille.getBrailleFromText(file));
		// TextToBraille.getBrailleFromText(file);
	}
}
