package Experiment;

public class Exp1 {
	public static void main(String args[]){
        double L = -10, R = 5;
        double eps = 0.0001;
        while(true) {
            double m = (L + R) / 2;
            if(ck(m) == 0) {
                System.out.println(m);
                break;
            }else {
                if(ck(R) * ck(m) < 0) L = m;
                if(ck(L) * ck(m) < 0) R = m;
            }
            if(R - L < eps) {
                System.out.println(m);
                break;
            }
        }
//        for(int i = -10; i < 5; i++){
//            System.out.println(ck(i));
//        }
    }
    public static double ck(double m){
        return m * m * m - 10 * m + 23;
    }
}
