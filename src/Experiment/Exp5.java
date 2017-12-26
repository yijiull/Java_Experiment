package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Exp5 {
	public static String read(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}
	public static void main(String[] args) throws IOException {
		double score[] = new double[1010];
		int n = 0;
		FileInputStream in = new FileInputStream(new File("score.csv"));
		Scanner s = new Scanner(in);
		while(s.hasNextLine()) {
			String a = s.nextLine();
			String[] temp = a.split(";");
			score[n++] = Double.parseDouble(temp[1]);
		}
		s.close();
		double hight = score[0], low = score[0];
		double sum = 0;
		int cnt[] = new int[4];
		Arrays.fill(cnt, 0);
		for(int i = 1; i < n; i++) {
			hight = max(hight, score[i]);
			low = min(low, score[i]);
			sum += score[i];
			if(score[i] >= 90) cnt[0]++;
			else if(score[i] >= 80) cnt[1]++;
			else if(score[i] >= 70) cnt[2]++;
			else if(score[i] >= 60) cnt[3]++;
		}
		PrintWriter out = new PrintWriter("out.txt");
		out.println("最高分： " + hight + "\n最低分： " + low + "\n平均分： " + sum / n);
		for(int i = 0; i < 4; i++) {
			out.println(100 - (i + 1) * 10 + "--" + (100 - i * 10 - 1 + (i == 0 ? 1 : 0)) + ": " + cnt[i]);
		}
		out.close();
	}
	private static double min(double low, double d) {
		if(low < d) return low;
		else return d;
	}
	private static double max(double hight, double d) {
		if(hight > d) return hight;
		else return d;
	}
}
