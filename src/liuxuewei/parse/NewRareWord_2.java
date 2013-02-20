package liuxuewei.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewRareWord_2 {
	// 使用RandomAccessFile实现文件的追加，其中：fileName表示文件名；content表示要追加的内容
	private static String date = DateInfo.getDate();
	private static String fileName = "newspecialwords" + date + ".properties";
	private String[] addstr = new String[2];
	private static Logger logger = Logger.getLogger("NewRareWord");
	public NewRareWord_2(String add) {
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
		appendAddInfo(addstr[0] + addstr[1] + "\r\n");
	}
	private static void appendAddInfo_1(String fileName, String content) {
		RandomAccessFile raf = null;
		try {
			// 按读写方式创建一个随机访问文件流
			raf = new RandomAccessFile(fileName, "rw");
			long fileLength = raf.length();// 获取文件的长度即字节数
			// 将写文件指针移到文件尾。
			raf.seek(fileLength);
			// 按字节的形式将内容写到随机访问文件流中
			raf.writeBytes(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				// 关闭流
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 使用FileWriter实现文件的追加，其中：fileName表示文件名；content表示要追加的内容
	/*
	 * private static void appendAddInfo_2(String fileName, String content) {
	 * try { // 创建一个FileWriter对象，其中boolean型参数则表示是否以追加形式写文件 FileWriter fw = new
	 * FileWriter(fileName, true); // 追加内容 fw.write(content); // 关闭文件输出流
	 * fw.close(); } catch (IOException e) { e.printStackTrace(); } }
	 */

	public static void showFileContent(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println(line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void appendAddInfo(String content) {
		appendAddInfo_1(fileName, content);
	}

	/*
	 * public static void main(String[] args) { String fileName =
	 * "C:/temp/append.txt"; String content = "Successful operation!";
	 * System.out.println(fileName + "文件的内容如下：");
	 * RAFReadOldInfo.showFileContent(fileName); // 显示文件内容 //
	 * 按RandomAccessFile的形式追加文件
	 * System.out.println("\n按RandomAccessFile的形式追加文件后的内容如下：");
	 * RAFReadOldInfo.appendAddInfo_1(fileName, content);
	 * RAFReadOldInfo.appendAddInfo_1(fileName, "\n Game is Over！ \n");
	 * RAFReadOldInfo.showFileContent(fileName); // 显示文件内容 // 按FileWriter的形式追加文件
	 * System.out.println("\n按FileWriter的形式追加文件后的内容如下：");
	 * RAFReadOldInfo.appendAddInfo_2(fileName, content);
	 * RAFReadOldInfo.appendAddInfo_2(fileName, "\n Game is Over！ \n");
	 * RAFReadOldInfo.showFileContent(fileName); // 显示文件内容 }
	 */
}