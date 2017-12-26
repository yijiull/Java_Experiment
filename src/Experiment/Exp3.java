package Experiment;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;



class Point implements Comparable<Point> {
	double x = 0, y = 0;
	int id = 0;
	double dis = 0;
	public int compareTo(Point a) {
		return (dis < a.dis ? -1 : (dis == a.dis ? 0 : 1));
	}
}

class SineDraw1 extends JPanel{
	
	final static int MAX = 1010;
	private static Point[] p;
	private static int cnt = 0;
	public SineDraw1(){

	}
	public void init() throws FileNotFoundException {
		Scanner in = new Scanner(new File("KMeans_Set.txt"));
		p= new Point[80];
		cnt = 0;
//		while(in.hasNextLine()) System.out.println(in.nextLine());
		while (in.hasNextDouble()) {
			Point p1 = new Point();
			p1.x = in.nextDouble();
			p1.y = in.nextDouble();
			p[cnt] = p1;
			cnt++;
		}
		System.out.println(cnt);
		in.close();
		final int k = 4;
		Point center[] = new Point[k + 1];
		for (int i = 1; i <= k; i++) {
			Point p1 = new Point();
			p1.x = p[i].x;
			p1.y = p[i].y;
			p1.id = i;
			center[i] = p1;
		}
		center[2].x = p[4].x;
		center[2].y = p[4].y;
		center[3].x = p[8].x;
		center[3].y = p[8].y;
		
//		System.out.println(Arrays.toString(center));
		Point temp[] = new Point[k + 1];
		for(int i = 0; i < k + 1; i++) temp[i] = new Point();
		for (int t = 0; t < MAX; t++) {
			for (int i = 0; i < cnt; i++) {
				for (int j = 1; j <= k; j++) {
					temp[j].dis = distance(p[i], center[j]);
//					System.out.println(temp[j].dis);
					temp[j].id = center[j].id;
				}
				Arrays.sort(temp);
				int id = temp[1].id;
//				System.out.println("++++" + id);
				p[i].id = id;
			}
			double sumx[] = new double[k+1], sumy[] = new double[k+1];
			int count[] = new int[k+1];
			Arrays.fill(count, 0);
			Arrays.fill(sumx, 0);
			Arrays.fill(sumy, 0);
			for(int i = 0; i < cnt; i++) {
				sumx[p[i].id] += p[i].x;
				sumy[p[i].id] += p[i].y;
				count[p[i].id] += 1;
			}
			for(int i = 1; i <= k; i++) {
				center[i].x = sumx[i] / count[i];
				center[i].y = sumy[i] / count[i];
			}
			repaint();
		}
		for(int i = 0; i < cnt; i++) {
			p[i].dis = distance(p[i],center[p[i].id]);
		}
		PrintWriter out = new PrintWriter("out3_1.txt");
		for(int i = 0; i < cnt; i++) {
			out.println(String.format("x=%-10.4fy=%-10.4fdis=%-10.6fid=%-10d", p[i].x, p[i].y, p[i].dis, p[i].id));
		}
		out.close();
	}
	public void paintComponent(Graphics g) {
		for(int i = 0; i < 10000000; i++) 
		for(int j = 0; j < 10000000; j++)
		{
			int a = 0, b = 1;
			a = a + b;
		}
		super.paintComponent(g);
		g.setColor(Color.RED);
		for(int i = 0; i < cnt; i++) {
			int x1 = (int)((p[i].x + 7) * 50);
			int y1 = (int)((p[i].y + 7) * 50);
			int col = p[i].id;
			switch(col) {
			case 1: g.setColor(Color.BLUE);
			break;
			case 2: g.setColor(Color.RED);
			break;
			case 3: g.setColor(Color.BLACK);
			break;
			case 4: g.setColor(Color.GREEN);
			break;
			case 5: g.setColor(Color.ORANGE);
			break;
			}
			g.fillRect(x1, y1, 5, 5);
		}
	}


	private static double distance(Point a, Point b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		return Math.sqrt(Math.pow(dx, 2d) + Math.pow(dy, 2d));
	}
	
}



public class Exp3 extends JFrame{
	private SineDraw1 sines;
	
	public Exp3() throws FileNotFoundException {
		sines = new SineDraw1();
		sines.init();
		add(sines);
	}
	public static void main(String[] args) throws FileNotFoundException {
		SwingConsole.run(new Exp3(), 700, 400);
	}
	
	
}
