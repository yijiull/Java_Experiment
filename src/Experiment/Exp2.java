package Experiment;

public class Exp2 {
	 public static void main(String args[]){
	        final int maxn = 10000001;
	        int prime[] = new int[maxn];
	        int cnt = 0;
	        for(int i = 0; i < maxn; i++) prime[i] = 0;
	        for(int i = 2; i < maxn; i++){
	            if(prime[i] == 0){
	                prime[cnt++] = i;
	            }
	            for(int j = 0; j < cnt; j++){
	                if(prime[j] * i >= maxn) break;
	                int t = prime[j] * i;
	                prime[t] = 1;
	                if(i % prime[j] == 0) break;
	            }
	        }
	        System.out.println("一共 " + cnt + "个素数");
	        System.out.println("最大素数为 ： " + prime[cnt-1]);
	        //for(int i = 0; i < cnt; i++) System.out.println(prime[i]);
	    }
}
