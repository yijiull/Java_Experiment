package Experiment;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;

public class Reptile {
    public static void main(String[] args) throws Exception {
        URL u = new URL("http://www.163.com");
        InputStreamReader isr = new InputStreamReader(u.openStream(), "gbk");  //lua ma
        BufferedReader br = new BufferedReader(isr);
        String s;
        PrintWriter pr = new PrintWriter("163.html", "gbk");  //lua ma
        while((s = br.readLine()) != null) {
        	pr.println(s);
        }
        br.close();
        pr.close();
        InetAddress address = InetAddress.getLocalHost(); 
        System.out.println("本机：\n" +  address.getHostName() + '\n' + address.getHostAddress());
        address = InetAddress.getByName("www.163.com");
        System.out.println("网站： \n" + address.getHostName() + '\n' + address.getHostAddress());
        
    }

}