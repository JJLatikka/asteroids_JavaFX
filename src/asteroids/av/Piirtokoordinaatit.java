package asteroids.av;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Piirtokoordinaatit {

    public static List<Double> getRunko() {
        List<Double> p1 = new ArrayList<>();
        double[][] t = {
            {27.5, 0}, {35, 52.5}, {40, 60}, {50, 100}, {45, 100}, {35, 85}, {30, 85}, {30, 87.5},
            {20, 87.5}, {20, 85}, {15, 85}, {5, 100}, {0, 100}, {10, 60}, {15, 52.5}, {22.5, 0}
        };
        for (double[] y : t) {
            for (double x : y) {
                p1.add(x);
            }
        }
        return p1;
    }

    public static double[][] getKuviot1() {
        double[][] p2 = {
            {25, 0, 17.5, 52.5}, {17.5, 52.5, 12.5, 60}, {12.5, 60, 2.5, 100},
            {25, 0, 32.5, 52.5}, {32.5, 52.5, 37.5, 60}, {37.5, 60, 47.5, 100},
            {2.5, 100, 12.5, 82.5}, {12.5, 82.5, 37.5, 82.5}, {37.5, 82.5, 47.5, 100}
        };
        return p2;
    }

    public static double[][] getKuviot2() {
        double[][] p3 = {
            {22.5, 57.75, 19.5, 60.25}, {22.375, 65.25, 19.375, 67.75}, {22.25, 72.75, 19.25, 75.25},
            {27.5, 57.75, 30.5, 60.25}, {27.625, 65.25, 30.625, 67.75}, {27.75, 72.75, 30.75, 75.25}
        };
        return p3;
    }

    public static List<Double> getSirpaleet(Random rnd) {
        List<Double> p4 = new ArrayList<>();
        double xa = 100;
        double ya = 50;
        double r = 1;
        int n = 3;
        for (int i1 = 0; i1 < 4; i1++) {
            for (int i2 = 0; i2 < n; i2++) {
                double x = xa + r + rnd.nextInt(n * 2);
                if (rnd.nextBoolean()) {
                    x *= -1;
                }
                p4.add(x);
                double y = ya + r + rnd.nextInt(n * 2);
                if (rnd.nextBoolean()) {
                    y *= -1;
                }
                p4.add(y);
            }
            r *= 2;
            n *= 3;
        }
        return p4;
    }

    public static List<Double> getViisikulmio(double k, Random rnd) {
        List<Double> p5 = new ArrayList<>();
        double x1 = Math.cos(Math.PI * 2 / 5);
        double x2 = Math.cos(Math.PI / 5);
        double y1 = Math.sin(Math.PI * 2 / 5);
        double y2 = Math.sin(Math.PI * 4 / 5);
        double[][] t = {
            {k, 0}, {k * x1, -1 * k * y1}, {-1 * k * x2, -1 * k * y2},
            {-1 * k * x2, k * y2}, {k * x1, k * y1}
        };
        for (double[] y : t) {
            for (double x : y) {
                int m = rnd.nextInt(17) - 8;
                p5.add(x + m);
            }
        }
        return p5;
    }

}
