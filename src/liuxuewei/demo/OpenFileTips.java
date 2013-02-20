package liuxuewei.demo;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 屏幕右下角出现渐隐渐显的提示框 使用到了JDK1.7中新特性的透明窗体，所以必须要使用JDK1.7或以上版本的JDK 功能如下： 1.窗体出现时逐渐清晰
 * 2.停留一会儿时间之后会自动逐渐模糊直至消失 3.点击关闭按钮后逐渐模糊直至消失 4.提示内容支持html标签
 * 
 */
public class OpenFileTips implements Runnable {

	private JFrame showInfo;
	private JEditorPane detail;
	private int stayTime;// 休眠时间
	private String title, message;// 消息标题,内容
	private int style;// 窗体样式
	private JLabel Logo;

	/**
	 * 渐隐渐显的提示框
	 * 
	 * @param style
	 *            提示框的样式 以下为样式可选值： 0 NONE 无装饰（即去掉标题栏） 1 FRAME 普通窗口风格 2
	 *            PLAIN_DIALOG 简单对话框风格 3 INFORMATION_DIALOG 信息对话框风格 4
	 *            ERROR_DIALOG 错误对话框风格 5 COLOR_CHOOSER_DIALOG 拾色器对话框风格 6
	 *            FILE_CHOOSER_DIALOG 文件选择对话框风格 7 QUESTION_DIALOG 问题对话框风格 8
	 *            WARNING_DIALOG 警告对话框风格
	 * @param title
	 *            提示框标题
	 * @param message
	 *            提示框内容
	 * @param logo
	 *            显示logo
	 */
	public OpenFileTips(int style, String title, String message, JLabel Logo) {
		this.stayTime = 5;
		this.style = style;
		this.title = title;
		this.message = message;
		this.Logo = Logo;
	}

	public void print() {
		showInfo = new JFrame();
		showInfo.setLayout(new BorderLayout());
		detail = new JEditorPane();
		detail.setEditable(false);// 不可编辑
		detail.setContentType("text/html");// 将编辑框设置为支持html的编辑格式
		detail.setText(message);
		showInfo.add(Logo, BorderLayout.NORTH);
		showInfo.add(detail, BorderLayout.SOUTH);
		showInfo.setSize(200, 160);
		showInfo.setTitle(title);
		// 设置窗体的位置及大小
		int x = Toolkit.getDefaultToolkit().getScreenSize().width
				- Toolkit.getDefaultToolkit().getScreenInsets(
						showInfo.getGraphicsConfiguration()).right
				- showInfo.getWidth() - 5;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height
				- Toolkit.getDefaultToolkit().getScreenInsets(
						showInfo.getGraphicsConfiguration()).bottom
				- showInfo.getHeight() - 5;
		showInfo.setBounds(x, y, showInfo.getWidth(), showInfo.getHeight());
		showInfo.setUndecorated(true); // 去掉窗口的装饰
		showInfo.getRootPane().setWindowDecorationStyle(style); // 窗体样式
		showInfo.setOpacity(0.01f);// 初始化透明度
		showInfo.setVisible(true);
		showInfo.setAlwaysOnTop(true);// 窗体置顶
		// 添加关闭窗口的监听
		showInfo.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				hide();
			}
		});
	}

	/**
	 * 窗体逐渐变清晰
	 * 
	 */
	public void show() {
		for (int i = 0; i < 50; i++) {
			try {
				Thread.sleep(50);
			} catch (Exception sleepex) {
				sleepex.printStackTrace();
			}
			showInfo.setOpacity(i * 0.02f);
		}
	}

	/**
	 * 窗体逐渐变淡直至消失
	 * 
	 */
	public void hide() {
		float opacity = 100;
		while (true) {
			if (opacity < 2) {
				break;
			}
			opacity = opacity - 2;
			showInfo.setOpacity(opacity / 100);
			try {
				Thread.sleep(20);
			} catch (Exception sleepex) {
				sleepex.printStackTrace();
			}
		}
		showInfo.dispose();
	}

	public void run() {
		print();
		show();
		try {
			Thread.sleep(stayTime * 1000);
		} catch (Exception e) {
		}
		hide();

	}
}
