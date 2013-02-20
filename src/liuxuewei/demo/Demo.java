package liuxuewei.demo;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import liuxuewei.braille.ToBraille;
import liuxuewei.file2braille.ImageToBrailleThread;
import liuxuewei.file2braille.TextToBrailleThread;
import liuxuewei.parse.ChineseToLetter;
import liuxuewei.parse.NewRareWord_2;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

public class Demo {
	// 主界面
	private JFrame frame = new JFrame("盲文翻译演示系统");
	private JLabel info = new JLabel("界面显示有限,请输入<0~8>个文字：");
	private JTextField totrans = new JTextField(20);
	private JPanel inputpanel = new JPanel();
	private JButton okbtn = new JButton("确定");
	private JButton clearbtn = new JButton("清空");
	private JButton addRWordbtn = new JButton("添加");
	private JButton openFilebtn = new JButton("打开");
	private JPanel btnpanel = new JPanel();
	private JPanel controlPanel = new JPanel(new BorderLayout());
	private JPanel viewPanel = new JPanel(new BorderLayout());
	private static JTextArea display = new JTextArea();
	private JScrollPane show = new JScrollPane(display,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);// 不能static，否则将失去设置的UI
	// 文件选择器、Tips弹出窗
	private JFileChooser chooser = new JFileChooser();
	private File showFile;
	private static URL url;
	private static JLabel Logo = new JLabel(
			"<html>感谢您使用<br><font size=5>toBraille</font><html>");
	// 系统托盘
	private TrayIcon trayIcon = null;
	// 右键策略选择
	private JPopupMenu poMenu = new JPopupMenu();
	private static boolean strategy = false;
	private ButtonGroup stragroup = new ButtonGroup();
	private JRadioButtonMenuItem[] items = new JRadioButtonMenuItem[2];
	static Logger logger = Logger.getLogger("MainFrame");
	/**
	 * 约定的输入格式
	 */
	private String OrderInput = "<html>英文字符串与中文字符串混合使用时请用空格隔开。<br>【即输入英文时，要符合英文书写格式】</html>";
	static {
		// TODO change level to show or hide information
//		logger.setLevel(Level.INFO);
		 logger.setLevel(Level.WARNING);
		// for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		// logger.log(Level.INFO, info.getClassName());
		// }
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// System.setProperty("sun.awt.noerasebackground", "true");
		try {
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
			// UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public Demo() {
		GUIInit();// 初始化界面
		SystemTrayInit();// 启动系统托盘
	}

	private void GUIInit() {
		url = this.getClass().getClassLoader()
				.getResource("liuxuewei/image/toBraille.gif");
		Logo.setIcon(new ImageIcon(url));
		Logo.setHorizontalTextPosition(SwingConstants.LEFT);
		// 设置窗口属性
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setIconImage(new ImageIcon(url).getImage());
		frame.setLayout(new BorderLayout());
		// 根据屏幕分辨率设置窗口大小
		if (screen.width <= 800 || screen.height <= 600)
			frame.setSize(300, 500);
		else
			frame.setSize(400, 600);
		frame.setResizable(false);
		frame.setLocation((screen.width - frame.getWidth()) / 2,
				(screen.height - frame.getHeight()) / 2);
		// 输入区
		inputpanel.setLayout(new BorderLayout());
		inputpanel.add(info, BorderLayout.NORTH);
		inputpanel.add(totrans, BorderLayout.CENTER);
		totrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBraille(false);
			}
		});
		controlPanel.add(inputpanel, BorderLayout.NORTH);
		// 按钮区
		ButtonListener btnlistener = new ButtonListener();
		btnpanel.setLayout(new FlowLayout());
		btnpanel.setBackground(new Color(200, 100, 50));
		okbtn.setActionCommand("OK");
		okbtn.setToolTipText("Ok");
		okbtn.addActionListener(btnlistener);
		btnpanel.add(okbtn);
		clearbtn.setActionCommand("CLEAR");
		clearbtn.setToolTipText("Clear");
		clearbtn.addActionListener(btnlistener);
		btnpanel.add(clearbtn);
		addRWordbtn.setToolTipText("Add new rare Chinese Word.");
		addRWordbtn.setActionCommand("ADD");
		addRWordbtn.addActionListener(btnlistener);
		btnpanel.add(addRWordbtn);
		// 设置文件选择器和Tips弹出窗
		chooser.setMultiSelectionEnabled(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"(*.txt *.jpg)", "txt", "jpg");
		chooser.setFileFilter(filter);
		chooser.setToolTipText("You can only choose a file end with jpg or txt!");
		openFilebtn.setToolTipText("Open a file end with txt or jpg.");
		openFilebtn.setActionCommand("OPEN");
		openFilebtn.addActionListener(btnlistener);
		btnpanel.add(openFilebtn);
		controlPanel.add(btnpanel, BorderLayout.SOUTH);
		frame.add(controlPanel, BorderLayout.NORTH);
		display.setEditable(false);
		display.setSize(50, 30);
		display.setBackground(new Color(20, 140, 50));
		display.setToolTipText("The area to show brailles.");
		show.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(new Color(50, 200, 50), new Color(20, 80,
						50)), "盲文显示区"));
		viewPanel.add(show, BorderLayout.CENTER);
		frame.add(viewPanel, BorderLayout.CENTER);
		// 在内容显示区添加策略选择器
		items[0] = new JRadioButtonMenuItem("策略A");
		items[0].setActionCommand("FALSE");
		items[1] = new JRadioButtonMenuItem("策略B");
		items[1].setActionCommand("TRUE");
		for (int i = 0; i < 2; i++) {
			stragroup.add(items[i]);
			poMenu.add(items[i]);
			items[i].addActionListener(btnlistener);
		}
		display.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				checkForTriggerEvent(event);
			}

			public void mouseReleased(MouseEvent event) {
				checkForTriggerEvent(event);
			}

			private void checkForTriggerEvent(MouseEvent event) {
				if (event.isPopupTrigger()) {
					poMenu.show(event.getComponent(), event.getX(),
							event.getY());
				}
			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.validate();
		frame.setVisible(true);
		JOptionPane.showMessageDialog(frame, "约定输入的字符串格式：\n" + OrderInput);
	}

	private void SystemTrayInit() {
		if (SystemTray.isSupported()) { // 检查当前系统是否支持系统托盘
			SystemTray tray = SystemTray.getSystemTray();// 获取表示桌面托盘区的
															// SystemTray 实例。
			// Image image = Toolkit.getDefaultToolkit().getImage(url);//方法1
			Image image = Toolkit.getDefaultToolkit().createImage(url);// 方法2
			PopupMenu popupMenu = new PopupMenu();
			MenuItem exitItem = new MenuItem("exit");
			// System.out.println(exitItem.getFont());
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						System.exit(0);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			popupMenu.add(exitItem);
			trayIcon = new TrayIcon(image, "toBraille", popupMenu);
			trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 1) {
						// 注意下面的API调用，这个可以给用户提示信息
						trayIcon.displayMessage("message",
								"Thanks for useing toBraille.\n感谢您使用toBraille",
								TrayIcon.MessageType.INFO);
						showIT(true);
					}
				}
			});
			// 注意下面这个API调用，能够保证使用的图标被缩放到合适的比例
			trayIcon.setImageAutoSize(true);
			try {
				tray.add(trayIcon); // 将 TrayIcon 添加到 SystemTray。
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(frame, "您的系统不支持系统托盘");
		}
	}

	private class ButtonListener implements ActionListener {
		private boolean isFirstOpen = true;
		int returnVal;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("OK")) {
				getBraille(strategy);
			}
			if (e.getActionCommand().equals("CLEAR")) {
				totrans.setText("");
				display.setText("");
			}
			if (e.getActionCommand().equals("FALSE")) {
				strategy = false;
			}
			if (e.getActionCommand().equals("TRUE")) {
				strategy = true;
			}
			if (e.getActionCommand().equals("ADD")) {
				String add = JOptionPane
						.showInputDialog("遇到罕见字？请添加入库(eg: 我=WO，多音字eg：还=HAI huan...)");
				logger.log(Level.INFO, add);
				if (add != null && !((add.trim()).equals("")))// 不能先判断(add.trim()).equals("")，因为add可能为空
				{
//					new NewRareWord_1(add);
					new NewRareWord_2(add);
					JOptionPane
							.showMessageDialog(
									frame,
									"添加后请复制程序安装根目录下newspecialwords文件的内容，"
											+ "\n按照specialwords文件内提示的格式粘贴到specialwords文件中。"
											+ "\n进行测试，测试通过后您可以删除newspecialwords文件！");
				}
			}
			if (e.getActionCommand().equals("OPEN")) {
				if (isFirstOpen) {
					String title = "友情提示";
					String message = "<strong>文件的格式:</strong><br>"
							+ "您只能打开jpg图片或txt文本格式的文件<br>"
							+ "<strong>联系作者:</strong>liu.xuewei@hotmail.com<br>";
					Runnable translucent = new OpenFileTips(2, title, message,
							Logo);
					Thread thread = new Thread(translucent);
					thread.start();
					isFirstOpen = false;
				}
				returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					showFile = chooser.getSelectedFile();
					String path = showFile.getPath();
					System.out.println("You chose to open this file: " + path);
//					if (path.endsWith("jpg")) {
//						 ImageToBraille.showBrailleFromImg(showFile,
//						 Demo.this);
//					}
					if (path.endsWith("jpg")) {
						Thread imageThread = new Thread(
								new ImageToBrailleThread(showFile, Demo.this));
						ThreadProgressBar.show(frame, imageThread,
								"系统正在处理,请稍等...");
					}
					if (path.endsWith("txt")) {
						Thread textThread = new Thread(new TextToBrailleThread(
								showFile, Demo.this));
						// textThread.start();
						ThreadProgressBar.show(frame, textThread,
								"系统正在处理,请稍等...");
						// TextToBraille.showBrailleFromText(showFile,
						// Demo.this);
					}
				}
			}
			System.gc();
		}
	}

	private void getBraille(boolean clear) {
		String text = totrans.getText();
		if (!text.trim().equals("")) {
			Vector<String> Brailles = ChineseToLetter.getLetter(text);
			System.out.println("Letters<--->" + Brailles);
			Brailles = ToBraille.ChineseToBraille(text, clear);
			showBraille(Brailles);
		}
	}

	public void showBraille(Vector<String> Brailles) {
		StringBuffer displaystr = new StringBuffer();
		int count = 0;
		for (String Braille : Brailles) {
			if (Braille == null) {
				Braille = "我是罕见字或者英文单词，记得入库哦！\n";
				count++;
			}
			displaystr.append(Braille + "\n");
			logger.log(Level.INFO, "\n" + Braille);
		}
		if (count > 0) {
			JOptionPane.showMessageDialog(frame,
					"系统检测到您输入了罕见字或英文单词哦。\n若为罕见字，请记得入库。");
		}
		display.setText(displaystr.toString());
	}

	public void showIT(boolean visable) {
		if (frame.isVisible() != visable)
			frame.setVisible(visable);
	}

	public static void main(String[] args) {
		LaunchFlash Irreg = new LaunchFlash();
		Irreg.start();
	}
	/*
	 * public static void main(String[] args) { // TODO test 好人一生##平安 new
	 * Demo(); // System.out.println(ToBraille.ChineseToBraille("好好学习天天向上",
	 * true)); }
	 */
}
