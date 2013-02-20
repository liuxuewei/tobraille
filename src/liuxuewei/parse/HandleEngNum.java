package liuxuewei.parse;

public class HandleEngNum {
	// 0~9
	/**
	 * 处理英文数字
	 * 
	 * @param num
	 * @return
	 */
	public static String handleEngNum(String strTextPass) {
		strTextPass = strTextPass.replaceAll("0", "zero ");
		strTextPass = strTextPass.replaceAll("1", "one ");
		strTextPass = strTextPass.replaceAll("2", "two ");
		strTextPass = strTextPass.replaceAll("3", "three ");
		strTextPass = strTextPass.replaceAll("4", "four ");
		strTextPass = strTextPass.replaceAll("5", "five ");
		strTextPass = strTextPass.replaceAll("6", "six ");
		strTextPass = strTextPass.replaceAll("7", "seven ");
		strTextPass = strTextPass.replaceAll("8", "eight ");
		strTextPass = strTextPass.replaceAll("9", "nine ");
		return strTextPass;
	}
}
