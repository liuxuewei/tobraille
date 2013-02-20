/**
 *@author liuxuewei
 *@project toBraille
 *@fileName ToBrailleThread.java
 */
package liuxuewei.file2braille;

import java.io.File;

import liuxuewei.demo.Demo;

/**
 * @author liuxuewei
 * @email mail to<br>
 *        <a href="mailto:liu.xuewei@hotmail.com">liu.xuewei@hotmail.com</a><br>
 *		  see also<关注我><br>
 *        <a href="http://www.oschina.net/liuxuewei119">开源中国@lxw出山小草</a>
 * @date 2012-9-21
 * @function 
 * @versions 1.0 
 */
public class TextToBrailleThread implements Runnable{
	private File file;
	private Demo demo;
	
	/**
	 * 
	 */
	public TextToBrailleThread(File file, Demo demo) {
		this.file=file;
		this.demo=demo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		demo.showBraille(TextToBraille.getBrailleFromText(file));
	}

}
