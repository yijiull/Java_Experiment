package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class md5 {
	private static Scanner cin;
	static HashMap<String, String> mp = new HashMap<String, String>();
	static HashSet<String> st = new HashSet<String>();
	public static String getMD5(File file) throws NoSuchAlgorithmException, IOException {
		FileInputStream in = new FileInputStream(file);
		MessageDigest dig = MessageDigest.getInstance("MD5");
		byte[] temp = new byte[1024];
		int len; 
		while((len = in.read(temp)) != -1){
			dig.update(temp, 0, len);
		}
		in.close();
		BigInteger a = new BigInteger(1, dig.digest());
		return a.toString(16);
	}
	public static void getAllFile(File dir) throws NoSuchAlgorithmException, IOException {
		for(File file: dir.listFiles()) {
			if(file.isFile()) {
				String temp = getMD5(file);
				mp.put(file.getAbsolutePath(), temp);
				st.add(temp);
			}
			if(file.isDirectory()){
				getAllFile(file);
			}
		}
	}
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
		cin = new Scanner(System.in);
		String str;
		while(cin.hasNext()) {
			mp.clear();
			st.clear();
			str = cin.nextLine();
			File dir = new File(str);
			getAllFile(dir);
			for(String it : st) {
				HashSet<String> ans = new HashSet<String>();
				for(String key : mp.keySet()){
					String val = mp.get(key);
					if(val.equals(it)) {
						ans.add(key);
					}
				}
				if(ans.size() >= 2) {
					Iterator<String> c = ans.iterator();
					String content = getContent(c.next());
					System.out.println("以下文件内容相同：");
					for(String temp: ans) {
						String tr = getContent(temp);
//						System.out.println(tr);
						if(tr.equals(content)) {
							System.out.println(temp);
						}
						
					}
					System.out.println();
				}
			}
		}
		
	}
	private static String getContent(String string) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(string));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}
}
