package liuxuewei.braille;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import liuxuewei.parse.ChineseToLetter;
import liuxuewei.parse.FullCharConverter;
import liuxuewei.parse.HandleEngNum;

//实现汉字向拼音的转化

public class ChineseToBraille {
	/**
	 * get the Pin Yin of the words and get the combination of them.
	 * 
	 * @param strTextPass
	 * @return the combination of the spell
	 */
	/** 日志记录 */
	private final static Logger logger = Logger.getLogger("ChineseToBraille");

	static {
		// TODO change logger level to display or hide information
		// logger.setLevel(Level.INFO);
		logger.setLevel(Level.WARNING);
	}

	// 不允许创建实例
	private ChineseToBraille() {
	}

	/**
	 * @param strTextPass
	 * @param specialWord
	 * @return
	 */
	// TODO use this unused method somewhere
	@SuppressWarnings("unused")
	private String[] toSingleString(String strTextPass, String specialWord) {
		String[] strs = new String[3];
		if (strTextPass.startsWith(specialWord)) {
			strs = strTextPass.split(specialWord);
			strs[1] = strs[0];
			strs[0] = specialWord;
		} else if (strTextPass.endsWith(specialWord)) {
			strs = strTextPass.split(specialWord);
			strs[1] = specialWord;
		} else {
			String[] tempstrs = strTextPass.split(specialWord);
			strs[0] = tempstrs[0];
			strs[2] = tempstrs[1];
			strs[1] = specialWord;
		}

		return strs;
	}

	/**
	 * 替换掉英文前的空白,并给英文单词后面添加一个空白符
	 * 
	 * @param strTextPass
	 * @param clearChinese
	 * @return
	 */
	private static String replaceBlank(String strTextPass, boolean clearChinese) {
		String[] tempstrs = strTextPass.split(" ");
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < tempstrs.length; i++) {
			tempstrs[i] = tempstrs[i].trim();
			// 这里可以灵活一点
			tempstrs[i] = clearChinese(tempstrs[i]);
			char tempstart = tempstrs[i].charAt(0);
			if ((('a' <= tempstart && tempstart <= 'z') || ('A' <= tempstart && tempstart <= 'Z'))) {
				tempstrs[i] = tempstrs[i] + " ";
				logger.log(Level.INFO, "字母：" + tempstrs[i]);
			}
			// TODO 替换是否要clear
			if (clearChinese)
				tempstrs[i] = clearChinese(tempstrs[i]);
			result.append(tempstrs[i]);
		}
		return result.toString();
	}

	private static String clearChinese(String str) {
		char[] charstr = str.toCharArray();
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < charstr.length; i++) {
			result.append(charstr[i]);
			if (('a' <= charstr[i] && charstr[i] <= 'z')
					|| ('A' <= charstr[i] && charstr[i] <= 'Z')) {
				if (i + 1 < charstr.length
						&& !(('a' <= charstr[i + 1] && charstr[i + 1] <= 'z') || ('A' <= charstr[i + 1] && charstr[i + 1] <= 'Z')))
					result.append(" ");
			}
		}
		return result.toString();
	}

	/**
	 * 返回字符串盲文
	 * 
	 * @param strTextPass
	 * @param re
	 * @return
	 */
	protected static Vector<String> toBraille(String strTextPass,
			boolean clearChinese) throws NullPointerException {
		strTextPass = strTextPass.replaceAll("[\\s\\n]+", " ");// 替换连续空白符
		logger.log(Level.INFO, "处理连续空白符：" + strTextPass);
		strTextPass = strTextPass.trim();
		logger.log(Level.INFO, "处理首尾空白符：" + strTextPass);
		strTextPass = replaceBlank(strTextPass, clearChinese);// 替换掉中文后面、单词前面的空格
		logger.log(Level.INFO, "处理字母前、中文后空白符：" + strTextPass);
		// strTextPass=DBCtoSBC.Q2BChange(strTextPass);//全角字母转成半角字母
		strTextPass = FullCharConverter.full2HalfChange(strTextPass);// 全角字母转成半角字母
		logger.log(Level.INFO, "全角字母转成半角字母-->" + strTextPass);
		strTextPass = HandleEngNum.handleEngNum(strTextPass);// 处理阿拉伯数字
		logger.log(Level.INFO, "处理阿拉伯数字：" + strTextPass);
		Vector<String> ChineseWords = ChineseToLetter.getLetter(strTextPass);
		Vector<String> ChineseBrailles = new Vector<String>();
		Properties proper = new Properties();
		try {
			proper.load(ChineseToBraille.class.getClassLoader()
					.getResourceAsStream(
							"properties/CNBrailleValues.properties"));
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error: 没找到BrailleValues系统配置文件文件！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(Level.WARNING, "IO异常");
			e.printStackTrace();
		}
		String ChineseWord;
		String ChineseBraille;
		Matcher matcher;
		Pattern pattern = Pattern.compile("[a-z]+Chinese");
		int count = 0;// 记录输入字符串的指针
		for (int i = 0; i < ChineseWords.size(); i++) {
			matcher = pattern.matcher(ChineseWords.get(i));
			ChineseWord = ChineseWords.get(i).toUpperCase();// 多音字小写转换大写
			ChineseBraille = proper.getProperty(ChineseWord.replace("CHINESE",
					""));
			// TODO 处理输入的英文单词或不规则字母串 test: 哈 ha tt ben
			if (ChineseBraille == null) {
				// 加1则英文单词或不规则字母串要与下一个中文不空格
				count += ChineseWord.length() + 1;
			}
			try {
				if (!matcher.find()) {
					// 处理输入的字母，若是拼音则显示该拼音的盲文,同时处理标点符号（先处理）
					String matchword = ChineseBraille.replaceAll("\\W+", "");// 非字母
					if (!ChineseBraille.equals(ChineseWord)
							&& ((strTextPass.charAt(count)) == matchword
									.charAt(0))) {
						int length = matchword.length();
						ChineseBraille = ChineseBraille.replace(")", ") "
								+ strTextPass.substring(count, count + length));
						count += length + 1;
					}
					// else if(!ChineseBraille.equals(ChineseWord)&&
					// ((strTextPass.charAt(count))==ChineseBraille.replaceAll("\\W+",
					// "").charAt(0))){
					//
					// }
					else// 处理普通文字，显示该文字的盲文
					{
						ChineseBraille = ChineseBraille.replace(")", ") "
								+ strTextPass.charAt(count));
						count++;
					}
				}
				matcher.reset();
				if (matcher.find()) {
					// TODO findout why can't use replaceAll here?要重置
					ChineseBraille = ChineseBraille.replace("(", "多音字："
							+ strTextPass.charAt(count - 1) + "【");// 处理多音字显示
					ChineseBraille = ChineseBraille.replace(")", "】");
				}
			} catch (NullPointerException e) {
				// 罕见字会以小写或者null形式出现，可能会与入库多音字冲突
				logger.log(Level.WARNING, "请检查该罕见文字是否入库！");
				e.printStackTrace();
			}
			ChineseBrailles.add(ChineseBraille);
		}
		return ChineseBrailles;
	}
}
