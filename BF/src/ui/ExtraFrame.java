package ui;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JScrollPane;

public class ExtraFrame extends JFrame {
	private static final int WIDTH =	150;
	private static final int HEIGHT = 600;
	private int index = 0;
	private int bfSize = 128;
	public void setBfSize(int bfSize) {
		this.bfSize = bfSize;
	}
	private JLabel[] label = new JLabel[bfSize];
	public ExtraFrame() {
		this.setTitle("BFÊý×é");
		Box vBox = Box.createVerticalBox();
		for(int i =0;i<bfSize;i++){
			label[i] = new JLabel("["+i+"]       [          "+index+"          ]");
			vBox.add(label[i]);
		}
		JScrollPane scrollPane = new JScrollPane(vBox);
		setContentPane(scrollPane);
		setSize(WIDTH, HEIGHT);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}
		});
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		ExtraFrame	extraFrame = new ExtraFrame();
	}
	public void changeColor(int i){
		label[i].setForeground(Color.RED);
	}
	public void changeLabel(int i,int index){
		label[i].setText("["+i+"]       [          "+index+"          ]");
	}

}
