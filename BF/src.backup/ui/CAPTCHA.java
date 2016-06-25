package ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class CAPTCHA extends JComponent {
	private String s;
	public CAPTCHA() {
		super();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				repaint();
			}
		});
	}

	public String getCaptcha(){
		return s;
	}
	public void paintComponent(Graphics g) {
		Random ra = new Random();
		char st[] = "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();

		g.setColor(new Color(ra.nextInt(256), ra.nextInt(256), ra.nextInt(256)));
		g.fillRect(0, 0, getWidth(), getHeight());

		Font f = new Font("Fixedsys", Font.PLAIN, 20);
		g.setColor(new Color(ra.nextInt(100), ra.nextInt(100), ra.nextInt(100)));
		g.setFont(f);
		String ss = new String(st[ra.nextInt(36)] + "" + st[ra.nextInt(36)] + st[ra.nextInt(36)] + st[ra.nextInt(36)]);
		g.drawString(ss, 15, 17);
		s = ss;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CAPTCHA captcha = new CAPTCHA();
		frame.add(captcha,BorderLayout.CENTER);
		frame.setSize(100,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
