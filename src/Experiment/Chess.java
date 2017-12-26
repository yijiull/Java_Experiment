package Experiment;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Chess extends JFrame {
	private JTextField rows = new JTextField(), cols = new JTextField();
	private enum State  {Blank, OO, XX};
	static class Game extends JDialog{
		private State turn = State.XX;
		private static int CNT = 0;
		int n, m;
		int[][] p;
		Game(int cellwide, int cellhigh) {
			// TODO Auto-generated constructor stub
			setTitle("The Game");
			n = cellwide;
			m = cellhigh;
			p = new int[n][m];
			CNT = 0;
			setLayout(new GridLayout(cellwide, cellhigh));
			for(int i = 0; i < cellwide; i++) {
				for(int j = 0; j < cellhigh; j++) {
					Unit temp = new Unit();
					temp.x = i;
					temp.y = j;
					p[i][j] = -1;
					add(temp);
				}
			}
			setSize(cellwide*48, cellhigh*48);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		class Unit extends JPanel{
			private State cur = State.Blank;
			int x, y;
			public Unit() {
				addMouseListener(new ML());
			}
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int x1 = 0, y1 = 0,
					x2 = getSize().width,
					y2 = getSize().height;
				g.drawRect(x1, y1, x2, y2);
				System.out.println(x1 + "  " + y1 +"  "+ x2  + "  " + y2);
				x1 = x2 / 4;
				y1 = y2 / 4;
				int wide = x2 / 2;
				int high = y2 / 2;
				if(cur == State.XX) {
					g.drawLine(x1, y1, x1 + wide, y1 + high);
					g.drawLine(x1, y1 + high, x1 + wide, y1);
				}
				if(cur == State.OO){
					g.drawOval(x1, y1, wide, high);
				}
			}
			private int check() {
				int res = -1;
				for(int i = 0; i < n; i++) {
					for(int j = 0; j < m; j++) {
						if(p[i][j] != -1) {
							int temp = p[i][j];
							int ok = dfs(i, j, temp);
							if(ok == 1) {
								return temp;
							}
						}
					}
				}
				return res;
			}
			int dir[][] = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
			
			private int dfs(int x, int y, int temp) {
				for(int i = 0; i < 4; i++) {
					int dx = dir[i][0];
					int dy = dir[i][1];
					int cnt = 1;
					int nx = x; 
					int ny = y;
					for(int j = 0; j < 4; j++) {
						nx = nx + dx;
						ny = ny + dy;
						if(nx >= 0 && nx < n && ny >= 0 && ny < m && p[nx][ny] == temp) {
							cnt++;
						}else {
							break;
						}
						if(cnt == 5) return 1;
					}
				}
				return 0;
			}
			class ML extends MouseAdapter{
				public void mousePressed(MouseEvent e) {
					if(cur == State.Blank) {
						if(CNT % 2 == 0) {
							cur = State.XX;
							p[x][y] = 0;
							CNT++;
						}else {
							cur = State.OO;
							p[x][y] = 1;
							CNT++;
						}
//						System.out.println("x: " + x + "  y: " + y + "  id: " + id);
					}else {
//						System.out.println("WAWA!!");
						JOptionPane.showMessageDialog(null, "请选择其它格子！！", "Hey!", JOptionPane.ERROR_MESSAGE);
					}
					repaint();
					int res = check();
					if(res != -1) {
						String s;
						if(res == 0) {
							s = "XX ";
						}else {
							s = "OO ";
						}
						JOptionPane.showMessageDialog(null, s + "Win!!!\nCongratulations!!", "Hey!", JOptionPane.ERROR_MESSAGE);
					}
					if(CNT == n * m) {
						JOptionPane.showMessageDialog(null, "平局！！", "Hey!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	class BL implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JDialog d = new Game(
					new Integer(rows.getText()),
					new Integer(cols.getText()));
			d.setVisible(true);
		}
	}
	public Chess() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Rows", JLabel.CENTER));
		p.add(rows);
		p.add(new JLabel("Columns", JLabel.CENTER));
		p.add(cols);
		add(p, BorderLayout.NORTH);
		JButton b = new JButton("go");
		b.addActionListener(new BL());
		add(b, BorderLayout.SOUTH);
	}
	public static void main(String[] args) {
		SwingConsole.run(new Chess(), 200, 200);
	}
}
