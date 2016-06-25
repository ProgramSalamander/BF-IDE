package ui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.EmeraldDuskSkin;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;
import org.jvnet.substance.watermark.SubstanceCrosshatchWatermark;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600;
	private JTextArea codeArea;
	private JTextArea inputArea;
	private JFrame frame;
	private JLabel resultlabel;
	private String currentUser = "default";
	private ExtraFrame  extraFrame;
	private RedoUndoStack redoUndoStack = new RedoUndoStack();
	public MainFrame() {
		// 创建窗体
		frame = new JFrame("Lunar Eclipse  (Not Logined)");
		frame.setLayout(null);
		frame.setResizable(false);
		JLabel inputlabel = new JLabel("Input:");
		JLabel outputlabel = new JLabel("Output:");
		JLabel codelabel = new JLabel("Code:");
		resultlabel = new JLabel("result");
		inputlabel.setBounds(0, HEIGHT * 2 / 3 - 15, WIDTH / 2, 10);
		outputlabel.setBounds(WIDTH / 2, HEIGHT * 2 / 3 - 15, WIDTH / 2, 10);
		codelabel.setBounds(10, 10, WIDTH, 25);
		resultlabel.setBounds(WIDTH * 3 / 4 - 30, HEIGHT * 2 / 3 - 25, WIDTH / 2, HEIGHT / 3);
		resultlabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		frame.add(codelabel);
		frame.add(inputlabel);
		frame.add(outputlabel);
		frame.add(resultlabel);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu codeMenu = new JMenu("Code");
		JMenu helpMenu = new JMenu("Help");
		JMenu arrayMenu = new JMenu("Array");
		menuBar.add(fileMenu);
		menuBar.add(codeMenu);
		menuBar.add(arrayMenu);
		menuBar.add(helpMenu);

		JMenuItem newMenuItem = new JMenuItem("New", 'N');
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		JMenuItem openMenuItem = new JMenuItem("Open", 'O');
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		JMenuItem saveMenuItem = new JMenuItem("Save", 'S');
		fileMenu.add(saveMenuItem);
		JMenuItem runMenuItem = new JMenuItem("Run", 'R');
		JMenu undoMenu = new JMenu();
		JMenu redoMenu = new JMenu();
		codeMenu.add(runMenuItem);

		JMenu userMenu = new JMenu();
		userMenu.setBounds(WIDTH - 100, 10, 20, 20);
		ImageIcon userIcon = new ImageIcon("user.png");
		Image temp = userIcon.getImage().getScaledInstance(userMenu.getWidth(), userMenu.getHeight(),
				userIcon.getImage().SCALE_DEFAULT);
		userIcon = new ImageIcon(temp);
		userMenu.setIcon(userIcon);
		menuBar.add(userMenu);
		undoMenu.setBounds(WIDTH - 100, 10, 20, 20);
		userIcon = new ImageIcon("undo.png");
		temp = userIcon.getImage().getScaledInstance(userMenu.getWidth(), userMenu.getHeight(),
				userIcon.getImage().SCALE_DEFAULT);
		userIcon = new ImageIcon(temp);
		undoMenu.setIcon(userIcon);
		menuBar.add(undoMenu);
		redoMenu.setBounds(WIDTH - 100, 10, 20, 20);
		userIcon = new ImageIcon("redo.png");
		temp = userIcon.getImage().getScaledInstance(userMenu.getWidth(), userMenu.getHeight(),
				userIcon.getImage().SCALE_DEFAULT);
		userIcon = new ImageIcon(temp);
		redoMenu.setIcon(userIcon);
		menuBar.add(redoMenu);

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new RunActionListener());
		undoMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				codeArea.setText(redoUndoStack.undoPush());
			}
		});
		redoMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				codeArea.setText(redoUndoStack.redoPush());
			}
		});
		arrayMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				extraFrame.setVisible(true);
			}
		});
		helpMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null,
						"欢迎使用此BF的IDE！\n在这里你可以将你的BF代码输入至主界面Code区域\n同时将你的参数输入左下角的Input区域，便可在右下角的Output区域中看到代码执行的结果");
			}
		});
		userMenu.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentUser.equals("default")) {
					JFrame loginFrame = new JFrame("登录");
					GridBagLayout gridBagLayout = new GridBagLayout();
					GridBagConstraints constraints = new GridBagConstraints();
					constraints.fill = GridBagConstraints.NORTH;
					constraints.anchor = GridBagConstraints.CENTER;
					constraints.weightx = 3;
					constraints.weighty = 4;
					loginFrame.setLayout(gridBagLayout);
					JButton ok = new JButton("确认");
					JButton cancel = new JButton("取消");
					JButton regist = new JButton("注册");
					JLabel title = new JLabel("登录Lunar Eclipse");
					JLabel userID = new JLabel("用户�?");
					JLabel password = new JLabel("密码");
					final JTextField nameField = new JTextField(15);
					final JPasswordField passwordField = new JPasswordField(15);
					passwordField.setLocation(100, 20);
					constraints.gridx = 1;
					constraints.gridy = 0;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(title, constraints);
					constraints.gridx = 0;
					constraints.gridy = 1;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(userID, constraints);
					constraints.gridx = 1;
					constraints.gridy = 1;
					constraints.gridheight = 1;
					constraints.gridwidth = 2;
					loginFrame.add(nameField, constraints);
					constraints.gridx = 0;
					constraints.gridy = 2;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(password, constraints);
					constraints.gridx = 1;
					constraints.gridy = 2;
					constraints.gridheight = 1;
					constraints.gridwidth = 2;
					loginFrame.add(passwordField, constraints);
					constraints.gridx = 0;
					constraints.gridy = 3;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(ok, constraints);
					constraints.gridx = 1;
					constraints.gridy = 3;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(cancel, constraints);
					constraints.gridx = 2;
					constraints.gridy = 3;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(regist, constraints);

					ok.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							String username = nameField.getText();
							String password = new String(passwordField.getPassword());
							try {
								if (RemoteHelper.getInstance().getUserService().login(username, password)) {
									JOptionPane.showMessageDialog(null, "登录成功�?");
									currentUser = username;
									RemoteHelper.getInstance().setUser(username);
									frame.setTitle("Lunar Eclipse  (" + currentUser + " logined)");
									loginFrame.dispose();
								} else {
									JOptionPane.showMessageDialog(null, "登录失败...");
								}
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}

						}
					});
					cancel.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							loginFrame.dispose();
						}
					});
					regist.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							JFrame registFrame = new JFrame("注册窗口");
							GridBagLayout gridBagLayout = new GridBagLayout();
							GridBagConstraints constraints = new GridBagConstraints();
							constraints.fill = GridBagConstraints.BOTH;
							constraints.anchor = GridBagConstraints.CENTER;
							constraints.weightx = 4;
							constraints.weighty = 6;
							registFrame.setLayout(gridBagLayout);
							JButton ok = new JButton("确认");
							JButton cancel = new JButton("取消");
							JLabel title = new JLabel("注册Lunar Eclipse");
							JLabel userID = new JLabel("用户�?");
							JLabel password = new JLabel("请输入密�?");
							JLabel assurepassword = new JLabel("确认密码");
							JTextField nameField = new JTextField(10);
							JPasswordField passwordField = new JPasswordField(10);
							JPasswordField assurepasswordField = new JPasswordField(10);
							JTextField captchaField = new JTextField(10);
							CAPTCHA captcha = new CAPTCHA();
							JLabel captchaLabel = new JLabel("验证�?");
							constraints.gridx = 1;
							constraints.gridy = 0;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(title, constraints);
							constraints.gridx = 0;
							constraints.gridy = 1;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(userID, constraints);
							constraints.gridx = 1;
							constraints.gridy = 1;
							constraints.gridheight = 1;
							constraints.gridwidth = 2;
							registFrame.add(nameField, constraints);
							constraints.gridx = 0;
							constraints.gridy = 2;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(password, constraints);
							constraints.gridx = 1;
							constraints.gridy = 2;
							constraints.gridheight = 1;
							constraints.gridwidth = 2;
							registFrame.add(passwordField, constraints);
							constraints.gridx = 0;
							constraints.gridy = 3;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(assurepassword, constraints);
							constraints.gridx = 1;
							constraints.gridy = 3;
							constraints.gridheight = 1;
							constraints.gridwidth = 2;
							registFrame.add(assurepasswordField, constraints);
							constraints.gridx = 0;
							constraints.gridy = 5;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(ok, constraints);
							constraints.gridx = 2;
							constraints.gridy = 5;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(cancel, constraints);
							constraints.gridx = 0;
							constraints.gridy = 4;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(captchaLabel, constraints);
							constraints.gridx = 1;
							constraints.gridy = 4;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(captchaField, constraints);
							constraints.gridx = 2;
							constraints.gridy = 4;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(captcha, constraints);

							ok.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									String username = nameField.getText();
									String password = new String(passwordField.getPassword());
									String assurepassword = new String(assurepasswordField.getPassword());
									String captch = captchaField.getText();
									String cap = captcha.getCaptcha();
									try {
										if(username.length()==0 ||password.length()==0||assurepassword.length()==0){
											JOptionPane.showMessageDialog(null, "输入信息不完整！");
										}
										else if ((captch.length()==0)||(!cap.equals(captch))) {
											JOptionPane.showMessageDialog(null, "验证码不�?致！");
										}
										else {
											if(RemoteHelper.getInstance().getUserService().regist(username, password,assurepassword)){
												registFrame.dispose();
											}
										}
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								}
							});
							cancel.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									registFrame.dispose();
								}
							});
							registFrame.setSize(300, 150);
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							Dimension screenSize = toolkit.getScreenSize();
							int width = screenSize.width;
							int height = screenSize.height;
							int x = (width - 300) / 2;
							int y = (height - 150) / 2;
							registFrame.setResizable(false);
							registFrame.setLocation(x, y);
							registFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							registFrame.setVisible(true);
						}
					});

					loginFrame.setSize(300, 150);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Dimension screenSize = toolkit.getScreenSize();
					int width = screenSize.width;
					int height = screenSize.height;
					int x = (width - 300) / 2;
					int y = (height - 150) / 2;
					loginFrame.setResizable(false);
					loginFrame.setLocation(x, y);
					loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					loginFrame.setVisible(true);
				} else {
					JOptionPane.showConfirmDialog(null, "是否要登出当前账�?", "登出窗口", JOptionPane.YES_NO_OPTION);
					int option = 0;
					if (option == JOptionPane.YES_OPTION) {
						frame.setTitle("Lunar Eclipse  (Not Logined)");
						RemoteHelper.getInstance().setUser("default");
						currentUser="default";
					}
				}
			}

		});

		frame.setJMenuBar(menuBar);

		codeArea = new JTextArea();
		codeArea.setLineWrap(true);
		codeArea.setEditable(true);
		codeArea.setFont(new Font("TimesRoman", Font.BOLD, 20));
		codeArea.setMargin(new Insets(30, 30, 30, 30));
		codeArea.setBackground(Color.LIGHT_GRAY);
		codeArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				redoUndoStack.pop(codeArea.getText());
			}
			
		});

		JScrollPane scrollPane = new JScrollPane(codeArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 0, WIDTH, HEIGHT * 2 / 3 - 20);
		frame.add(scrollPane);
		inputArea = new JTextArea();
		inputArea.setBounds(0, HEIGHT * 2 / 3, WIDTH / 2, HEIGHT / 3);
		inputArea.setMargin(new Insets(10, 10, 10, 10));
		inputArea.setFont(new Font("Arial", Font.ITALIC, 20));
		frame.add(inputArea);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int option = JOptionPane.showConfirmDialog(frame, "确定�?出？(请确认所有代码已保存�?)", "提示",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION)
					if (windowEvent.getWindow() == frame) {
						System.exit(0);
					} else {
						return;
					}
			}
		});
		extraFrame = new ExtraFrame();
		extraFrame.setLocation(x+WIDTH-5, y);
		int bfSize;
		try {
			bfSize = RemoteHelper.getInstance().getExecuteService().getBFArraySize();
			extraFrame.setBfSize(bfSize);
		} catch (RemoteException e1) {
			// TODO 自动生成�? catch �?
			e1.printStackTrace();
		}
		//外观设计
		
			try {
				UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO 自动生成�? catch �?
				e1.printStackTrace();
			} 
            SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());  
          SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());    
		
        
			
		
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(x, y);
		Image icon = toolkit.getImage("C:\\Users\\Ferriswheel\\Pictures\\图标\\1.jpg");
		frame.setIconImage(icon);
		frame.setVisible(true);
	}

	class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			codeArea.setText("");
			inputArea.setText("");
			resultlabel.setText("result");
			extraFrame.repaint();
		}
	}

	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame openFrame = new JFrame("打开");
			openFrame.setResizable(false);
			openFrame.setSize(300, 300);
			Box vBox = Box.createVerticalBox();
			openFrame.setLocation(frame.getLocationOnScreen().x, frame.getLocationOnScreen().y + 50);
			openFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					openFrame.dispose();
				}
			});
			try {
				String fileList = RemoteHelper.getInstance().getIOService().readFileList(currentUser);
				if (!fileList.equals("")) {
					String[] list = fileList.split("//");
					for (int i = 0; i < list.length; i++) {
						String[] name = list[i].split("_");
						JButton fileButton = new JButton(name[1]);
						fileButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									String code = RemoteHelper.getInstance().getIOService().readFile(currentUser,
											name[1]);
									codeArea.setText(code);
									openFrame.dispose();
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
							}
						});
						vBox.add(fileButton);
						JScrollPane scrollPane = new JScrollPane(vBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						scrollPane.setAutoscrolls(true);
						openFrame.setContentPane(scrollPane);
					}
				} else {
					JLabel label = new JLabel("您还未保存过文件�?");
					openFrame.add(label, BorderLayout.CENTER);
				}

			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			openFrame.setVisible(true);
			openFrame.pack();
		}

	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			SaveCodeFrame saveCodeFrame = new SaveCodeFrame(code,currentUser);
		}
	}

	class RunActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			String param = inputArea.getText();
			String result = "";
			try {
				if ((result = RemoteHelper.getInstance().getExecuteService().execute(code, param)) != "") {
					resultlabel.setText(result);
				} else {
					resultlabel.setText("解释失败...");
				}
				char[] bfArray = RemoteHelper.getInstance().getExecuteService().getBFArray();

				for(int i =0;i<bfArray.length;i++){
					if(bfArray[i]!=0){
						extraFrame.changeLabel(i, bfArray[i]);
						extraFrame.changeColor(i);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				resultlabel.setText(e1.getMessage());
			}
		}

	}

}
