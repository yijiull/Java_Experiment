package Experiment;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class test {

	static class button extends JButton {
		static int cnt = 0;

		button() {
			// ImageIcon ic = new
			// ImageIcon("/home/yijiull/eclipse-workspace/homework/Linus.png");
			// this.setIcon(ic);
			this.setText("cnt == " + cnt++);
		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(3, 4));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(300, 400);

		ImageIcon img = new ImageIcon("/home/yijiull/eclipse-workspace/homework/Linus.png");
		// 要设置的背景图片
		JLabel imgLabel = new JLabel(img);
		// 将背景图放在标签里。
		frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		// 将背景标签添加到jfram的LayeredPane面板里。
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		// 设置背景标签的位置
		Container contain = frame.getContentPane();
		((JComponent) contain).setOpaque(false);
		// 将内容面板设为透明。将LayeredPane面板中的背景显示出来。

		//单行文本框
		frame.add(new JTextField("Java!", 20));
		//文本输入区
		JTextArea area = new JTextArea(30, 30);
		JScrollPane pane = new JScrollPane(area);
		frame.add(pane);
		frame.setVisible(true);
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < 4; j++) {
				button bt = new button();
				bt.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						button bt = (button) e.getSource();
						bt.setText("I have been pressed");
					}
				});
				bt.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseMoved(MouseEvent arg0) {
						bt.setText("pos" + arg0.getX() + "  " + arg0.getY());
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseDragged(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				bt.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						bt.setText("out!!!!");
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						bt.setText("in!!");
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						JFrame dg = new JFrame();
						dg.setSize(50, 50);
						dg.setLocation(100, 100);

						dg.setVisible(true);
						bt.setText("pos" + arg0.getX() + "  " + arg0.getY());
						ImageIcon ic = new ImageIcon("/home/yijiull/eclipse-workspace/homework/Linus.png");
						bt.setIcon(ic);
						System.out.println(arg0.getClickCount()); // 判断双击
						// TODO Auto-generated method stub

					}
				});
				bt.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent e) {
						bt.setText("");
						// TODO Auto-generated method stub

					}

					@Override
					public void keyPressed(KeyEvent e) {
						switch (e.getKeyCode()) {
						case KeyEvent.VK_UP:
							bt.setText("UP!!");
							break;
						case KeyEvent.VK_DOWN:
							bt.setText("DOWN!");
							break;
						case KeyEvent.VK_LEFT:
							bt.setText("LEFT!!");
							break;
						case KeyEvent.VK_RIGHT:
							bt.setText("RiGHT");
							break;
						default:
							bt.setText("unknow!!" + e.getKeyChar());

						}
						// TODO Auto-generated method stub

					}
				});
				frame.add(bt);
			}
		// SwingConsole.run(frame, 300, 400);
	}
}
