package liuxuewei.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewRareWord_1 {
	private FileWriter writer;
	private String[] addstr = new String[2];
	private static Logger logger = Logger.getLogger("NewRareWord");
	private String date = DateInfo.getDate();
	public NewRareWord_1(String add) {
		logger.setLevel(Level.WARNING);
		addstr = add.split("=");
		addstr[0] = CodeChange.stringToUnicode(addstr[0]) + "=";
		String[] mutils = addstr[1].split(" ");
		addstr[1] = mutils[0];
		logger.log(Level.INFO, addstr[1]);
		for (int i = mutils.length; i > 1; i--) {
			mutils[i - 1] = " " + mutils[i - 1] + "Chinese";
			logger.log(Level.INFO, mutils[i - 1]);
			addstr[1] += mutils[i - 1];
		}
		// 方案1:读取旧newspecialwords.properties文件的内容，拼接新内容(不推荐使用方案)
		// 方案2:不读取旧文件使用RandomAccessFile浮标到文件末尾(推荐使用方案)
		try {
			String OldInfo = readOldInfo();
			writer = new FileWriter("newspecialwords" + date
					+ ".properties");
			// 坑人的windows系统啊，换行符怎么是\r\n?而不是\n\r
			writer.append(OldInfo + addstr[0] + addstr[1] + "\r\n");
			// writer.append(addstr[0]+addstr[1]);
			writer.flush();
		} catch (MalformedURLException e) {
			logger.log(Level.WARNING,"非法的URL");
			e.printStackTrace();
		} 
		catch(FileNotFoundException e){
			logger.log(Level.WARNING, "Error: 找不到newspecialwords" + date
					+ "系统配置文件,或其他程序正在占用该文件而导致无法访问！");
			e.printStackTrace();
		}catch (IOException e) {
			logger.log(Level.WARNING, "IO异常");
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, "关闭流IO异常");
				e.printStackTrace();
			}
		}

	}

	

	private String readOldInfo() {
		StringBuffer OldInfo = new StringBuffer();
		char[] in = new char[32];
		Reader input = null;
		int hasread = 0;

		try {
			input = new FileReader("newspecialwords" + date
					+ ".properties");
			while ((hasread = input.read(in)) > 0) {
				OldInfo.append(new String(in, 0, hasread));
			}
		} catch (FileNotFoundException e) {
			// 注意这里没有找到文件的话会自动创建一个指定的文件，详情请看FileReader源代码
			logger.log(Level.WARNING, "Error: 找不到newspecialwords" + date
					+ "系统配置文件,或其他程序正在占用该文件而导致无法访问！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return OldInfo.toString();
	}
}
