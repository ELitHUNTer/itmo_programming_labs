package lab1;

import main.Solution;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.*;

public class Lab1Main implements Solution {

    private int[] e = new int[(21 - 3) / 2 + 1];
    private float[] x = new float[14];

    @Override
    public void solve() {
        for (int i = 0; i < x.length; i++) x[i] = getRandom();
        for (int i = 0, a = 3; i < e.length; i++, a += 2) e[i] = a;
        //for (int i = 0; i < x.length; i++) System.out.print(x[i] + " "); System.out.println();
        double[][] n = new double[10][14];
        printMatrix(n);
    }

    private float getRandom() {
        return new Random().nextFloat(-9.0f, 14.0f);
    }

    private double getElement(int i, int j) {
        double x = this.x[j];
        return switch (e[i]) {
            case 19 -> cbrt(pow( (0.75 * ( pow(2*x, 2) + 0.75 ) ) , 2));
            case 5, 11, 13, 17, 21 -> tan(atan( (x+2.5) / 23 ))
                    /
                    (PI + pow(2 * x, tan(x)));
            default -> tan(asin(sin(cos(cos(x)))));
        };
    }

    private void printMatrix(double[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = getElement(i, j);
                System.out.printf("%-10.5f ", m[i][j]);
            }
            System.out.println();
        }
    }
}
