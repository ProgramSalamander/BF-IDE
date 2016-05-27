package ui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

	public MainFrame() {
		// 创建窗体
		frame = new JFrame("Lunar Eclipse");
		frame.setLayout(null);
		frame.setResizable(false);
		JLabel inputlabel = new JLabel("Input");
		JLabel outputlabel = new JLabel("output");
		resultlabel = new JLabel("result");
		inputlabel.setBounds(0, HEIGHT * 2 / 3 - 15, WIDTH / 2, 10);
		outputlabel.setBounds(WIDTH / 2, HEIGHT * 2 / 3 - 15, WIDTH / 2, 10);
		resultlabel.setBounds(WIDTH * 3 / 4 - 30, HEIGHT * 2 / 3 - 25, WIDTH / 2, HEIGHT / 3);
		resultlabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
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
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem runMenuItem = new JMenuItem("Run");
		JMenuItem undoMenuItem = new JMenuItem("Undo");
		JMenuItem redoMenuItem = new JMenuItem("Redo");
		codeMenu.add(runMenuItem);
		codeMenu.addSeparator();
		codeMenu.add(undoMenuItem);
		codeMenu.addSeparator();
		codeMenu.add(redoMenuItem);
		JMenuItem gitMenuItem = new JMenuItem();
		gitMenu.add(gitMenuItem);
		frame.setJMenuBar(menuBar);

		codeArea = new JTextArea();
		codeArea.setLineWrap(true);
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

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new RunActionListener());

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
			try {
				codeArea.setText(RemoteHelper.getInstance().getIOService().readFile("admin", "code"));
			} catch (RemoteException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}

	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
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
			try {
				resultlabel.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}

	}

}
