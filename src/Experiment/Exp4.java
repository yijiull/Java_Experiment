package Experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Exp4 {
	public static void main(String[] args) throws IOException{
        Scanner s = null;
        try{
            s = new Scanner(new File("LR_ex1.txt"));
            double x[] = new double[10010];
            double y[] = new double[10010];
            int cnt = 0;
            while(s.hasNextDouble()){
                s.nextDouble();
                x[cnt] = s.nextDouble();
                y[cnt] = s.nextDouble();
                cnt++;
            }
            //for(int i = 0; i < 10; i++) System.out.println("x " + x[i] + "y " + y[i]);
            double X2 = 0, X = 0, XY = 0, Y = 0;
            for(int i = 0; i < cnt; i++){
                X2 = X2 + x[i] * x[i];
                X = X + x[i];
                XY = XY + x[i] * y[i];
                Y = Y + y[i];
            }
            double temp = X / X2;
            double B = (Y - temp * XY) / (cnt - temp * X);
            temp = cnt / X;
            double A = (Y - temp * XY) / (X - temp * X2);
            System.out.println("f(x) = " + A + "x + " + B);
//            for(int i = 0; i < cnt; i++){
//                double t = A * x[i] + B;
//                if(t - y[i] > 0.1 || y[i] - t > 0.1) System.out.println(y[i] - t);
//            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally{
            if(s != null){
                s.close();
            }
        }
    }
}
