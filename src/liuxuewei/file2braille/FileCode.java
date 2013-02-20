package liuxuewei.file2braille;

import info.monitorenter.cpdetector.CharsetPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author root
 * 
 */
public class FileCode {
	// Input file name
	private String inputFileName;

	// The encoding when reading input file
	private String inputFileCode;

	// Output file name
	private String outputFileName;

	// The encoding when write to a file
	private String outputFileCode;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		// Convert file's encoding to 'utf8'.
//		write("c:/studio/test_file/test.txt", "utf8",
//				read("c:/studio/test_file/test.txt", 
//					guessEncoding("c:/studio/test_file/test.txt")));
//		
		// output new encoding
//		System.out.println(guessEncoding("我是中国人.txt"));
		
		// output file's content 
		System.out.println(read(new File("我是中国人.txt"),guessEncoding(new File("我是中国人.txt"))));
	}

	/**
	 * @describe Guess the encoding of the file specified by filename 
	 * @param filename
	 * @return the encoding of the file
	 */
	public static String guessEncoding(File file) {
		try {
			CharsetPrinter charsetPrinter = new CharsetPrinter();
			String encode = charsetPrinter.guessEncoding(file);
			if(!(encode.equals("GB2312")||encode.equals("UTF-8")))
				encode="Unicode";
			return encode;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * @describe Read file with specified encode
	 * @param filename
	 * @param encoding
	 * @return the content of the file in the form of string
	 */
	public static String read(File file, String encoding) {

		String string = "";
		BufferedReader in =null;
		try {
			in= new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encoding));
			String str = "";
			while ((str = in.readLine()) != null) {
				string += str + "\n";
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return string;
	}

	/**
	 * @describe write str to fileName with specified encode
	 * @param fileName
	 * @param encoding
	 * @param str
	 * @return null
	 */
	public static void write(String fileName, String encoding, String str) {
		Writer out=null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), encoding));
			out.write(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getInputFileCode() {
		return inputFileCode;
	}

	public void setInputFileCode(String inputFileCode) {
		this.inputFileCode = inputFileCode;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getOutputFileCode() {
		return outputFileCode;
	}

	public void setOutputFileCode(String outputFileCode) {
		this.outputFileCode = outputFileCode;
	}

}
