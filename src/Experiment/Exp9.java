package Experiment;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class Exp9 extends JFrame{
	public Exp9() {
		JButton bt1 = new JButton("生成二维码");
		JButton bt2 = new JButton("解析二维码");
		class ML extends MouseAdapter{
			public void mouseClicked(MouseEvent e) {
				class Frame2 extends JFrame{
					public Frame2() throws Exception {
						JLabel img = new JLabel();
						JTextArea info = new JTextArea("");
						JButton bt3 = new JButton("请打开二维码");
						bt3.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseExited(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseEntered(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								String temp = JOptionPane.showInputDialog("请输入图片地址：");
								ImageIcon icon = new ImageIcon(temp);
								img.setIcon(icon);
								img.setSize(300, 300);
								try {
									info.setText(QrCode.readCode(new File(temp)));
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
							}
						});
						setLayout(null);
						bt3.setSize(100, 80);
						bt3.setLocation(100, 50);
						
						bt3.setSize(100, 40);
						img.setLocation(0, 100);
						info.setBounds(0, 450, 300, 100);
						
						add(bt3);
						add(img);
						add(info);
						setSize(300, 600);
						setVisible(true);
					}
				}
				try {
					new Frame2();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		bt2.addMouseListener(new ML());
		bt1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				class Frame1 extends JFrame{
					public Frame1() {
						setSize(300, 500);
						setLocation(500, 800);
						//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						JTextArea area = new JTextArea("请输入要生成二维码的内容:");
						JButton button = new JButton("--生成--");
						button.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseExited(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseEntered(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								// TODO Auto-generated method stub
								try {
									QrCode.paintCode(area.getText().toString());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try {
									new myFrame();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
						});
						setLayout(new GridLayout(2, 1));
						add(area);
						add(button);
						setVisible(true);
					}
				}
				Frame1 frame = new Frame1();

			}
		});
		setLayout(null);
		bt1.setSize(100, 40);
		bt1.setLocation(100, 100);
		bt2.setLocation(100, 300);
		bt2.setSize(100, 40);
		add(bt1);
		add(bt2);
//		add(pane, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		SwingConsole.run(new Exp9(), 300, 500);
	}
}

class QrCode {
	public static String readCode(File file) throws Exception {
		MultiFormatReader formatReader = new MultiFormatReader();
		BufferedImage image = ImageIO.read(file);// 读取文件，识别成一个图片
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		// 二维码的参数
		HashMap hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
		System.out.println("解析二维码的结果：" + result.toString());
		System.out.println("二维码的格式：" + result.getBarcodeFormat());
		System.out.println("二维码的内容：" + result.getText());
		return result.getText();
	}
	public static void paintCode(String contents) throws Exception {
		// 定义二维码的样式
		int width = 300;
		int height = 300;
		String format = "png";
		// 定义二维码的参数
		HashMap hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置二维码的容错等级
		hints.put(EncodeHintType.MARGIN, 2);// 边距
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		Path file = new File("img.png").toPath();// 保存的路径
		MatrixToImageWriter.writeToPath(bitMatrix, format, file);
	}
	public static void main(String[] args) throws Exception {
		paintCode("");
		readCode(new File("img.png"));
		myFrame it = new myFrame();
	}
}
class myFrame extends JFrame {
	private Image image = null;
	public myFrame() throws IOException {
		setSize(300, 300);
		setLocation(300, 300);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		File file = new File("img.png");
		image = ImageIO.read(file);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, 300, 300, this);
	}
}

