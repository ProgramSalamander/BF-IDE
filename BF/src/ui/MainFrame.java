package ui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import org.omg.CORBA.INTERNAL;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600;
	private JTextArea codeArea;
	private JTextArea inputArea;
	private JFrame frame;
	private JLabel resultlabel;
	private String currentUser = null;

	public MainFrame() {
		// 创建窗体
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (ClassNotFoundException e2) {
		// // TODO 自动生成的 catch 块
		// e2.printStackTrace();
		// } catch (InstantiationException e2) {
		// // TODO 自动生成的 catch 块
		// e2.printStackTrace();
		// } catch (IllegalAccessException e2) {
		// // TODO 自动生成的 catch 块
		// e2.printStackTrace();
		// } catch (UnsupportedLookAndFeelException e2) {
		// // TODO 自动生成的 catch 块
		// e2.printStackTrace();
		// }
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
		JMenu gitMenu = new JMenu("Git");
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setSelected(false);
		menuBar.add(fileMenu);
		menuBar.add(codeMenu);
		menuBar.add(gitMenu);
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
		JMenuItem undoMenuItem = new JMenuItem("Undo", 'Z');
		JMenuItem redoMenuItem = new JMenuItem("Redo", 'Y');
		codeMenu.add(runMenuItem);
		codeMenu.addSeparator();
		codeMenu.add(undoMenuItem);
		codeMenu.addSeparator();
		codeMenu.add(redoMenuItem);
		JMenuItem gitMenuItem = new JMenuItem();
		gitMenu.add(gitMenuItem);
		JMenu userMenu = new JMenu();
		userMenu.setBounds(WIDTH - 100, 10, 20, 20);
		ImageIcon userIcon = new ImageIcon("user.png");
		Image temp = userIcon.getImage().getScaledInstance(userMenu.getWidth(), userMenu.getHeight(),
				userIcon.getImage().SCALE_DEFAULT);
		userIcon = new ImageIcon(temp);
		userMenu.setIcon(userIcon);
		menuBar.add(userMenu);

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new RunActionListener());
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
				if (currentUser==null) {
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
					JLabel userID = new JLabel("用户名");
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
					constraints.gridwidth = 1;
					loginFrame.add(nameField, constraints);
					constraints.gridx = 0;
					constraints.gridy = 2;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
					loginFrame.add(password, constraints);
					constraints.gridx = 1;
					constraints.gridy = 2;
					constraints.gridheight = 1;
					constraints.gridwidth = 1;
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
									JOptionPane.showMessageDialog(null, "登录成功！");
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
							constraints.fill = GridBagConstraints.NORTH;
							constraints.anchor = GridBagConstraints.CENTER;
							constraints.weightx = 3;
							constraints.weighty = 5;
							registFrame.setLayout(gridBagLayout);
							JButton ok = new JButton("确认");
							JButton cancel = new JButton("取消");
							JLabel title = new JLabel("注册Lunar Eclipse");
							JLabel userID = new JLabel("用户名");
							JLabel password = new JLabel("请输入密码");
							JLabel assurepassword = new JLabel("再次输入密码");
							JTextField nameField = new JTextField(15);
							JPasswordField passwordField = new JPasswordField(15);
							JPasswordField assurepasswordField = new JPasswordField(15);
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
							constraints.gridy = 4;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(ok, constraints);
							constraints.gridx = 2;
							constraints.gridy = 4;
							constraints.gridheight = 1;
							constraints.gridwidth = 1;
							registFrame.add(cancel, constraints);

							ok.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									String username = nameField.getText();
									String password = new String(passwordField.getPassword());
									String assurepassword = new String(assurepasswordField.getPassword());
									try {
										if (RemoteHelper.getInstance().getUserService().regist(username, password,
												assurepassword)) {
											registFrame.dispose();
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
					JOptionPane.showConfirmDialog(null, "是否要登出当前账号", "登出窗口", JOptionPane.YES_NO_OPTION);
					int option = 0;
					if (option == JOptionPane.YES_OPTION) {
						frame.setTitle("Lunar Eclipse  (Not Logined)");
						RemoteHelper.getInstance().setUser(null);
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
				int option = JOptionPane.showConfirmDialog(frame, "确定退出？(请确认所有代码已保存。)", "提示",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION)
					if (windowEvent.getWindow() == frame) {
						System.exit(0);
					} else {
						return;
					}
			}
		});
		// SwingUtilities.updateComponentTreeUI(frame);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(x, y);
		frame.setVisible(true);
	}

	class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			codeArea.setText("");
			inputArea.setText("");
			resultlabel.setText("result");
		}
	}

	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame openFrame = new JFrame("打开");
			openFrame.setResizable(false);
			openFrame.setSize(300,300);
			Box vBox = Box.createVerticalBox();
			openFrame.setLocation(frame.getLocationOnScreen().x, frame.getLocationOnScreen().y+50);
			openFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					openFrame.dispose();
				}
			});
			try {
				String fileList = RemoteHelper.getInstance().getIOService().readFileList(currentUser);
				String[] list = fileList.split("//");
				for(int i = 0 ; i<list.length;i++){
					String[] name = list[i].split("_");
					JButton fileButton = new JButton(name[1]);
					fileButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								String code= RemoteHelper.getInstance().getIOService().readFile(currentUser,name[1]);
								codeArea.setText(code);
								openFrame.dispose();
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					});
					vBox.add(fileButton);
					JScrollPane scrollPane = new JScrollPane(vBox,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					scrollPane.setAutoscrolls(true);
					openFrame.setContentPane(scrollPane);
					
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
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-H-m-s");
				String string = simpleDateFormat.format(Calendar.getInstance().getTime());
				RemoteHelper.getInstance().getIOService().writeFile(code, currentUser, string);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
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
			} catch (Exception e1) {
				e1.printStackTrace();
				resultlabel.setText(e1.getMessage());
			}
		}

	}

}
