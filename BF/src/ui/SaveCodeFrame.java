package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class SaveCodeFrame extends JFrame {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 130;
	JLabel label;
	JTextField textField;
	JButton button;
	JButton button2;

	public String name;

	public SaveCodeFrame(String code, String currentUser) {
		setTitle("保存文件");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout(new FlowLayout());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		int x = (width - WIDTH) / 2;
		int y = (height - HEIGHT) / 2;
		setLocation(x, y);
		label = new JLabel("请输入保存的文件名");
		textField = new JTextField(25);
		button = new JButton("确认");
		button2 = new JButton("取消");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				name = textField.getText();
				try {
					RemoteHelper.getInstance().getIOService().writeFile(code, currentUser, name);
					JOptionPane.showMessageDialog(null, "保存成功！");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(label);
		add(textField);
		add(button);
		add(button2);
		setResizable(false);
		setVisible(true);

	}

}
