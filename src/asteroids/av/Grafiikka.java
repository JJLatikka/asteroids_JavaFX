package asteroids.av;

import asteroids.Koko;
import asteroids.domain.Ammus;
import asteroids.domain.Asteroidi;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Grafiikka {

    private static int[] variJaIlmiintyminen(int k, Random rnd) {
        int vari = rnd.nextInt(5) + 1;
        int x = 0 + k / 2;
        int y = 0 + k / 2;
        switch (rnd.nextInt(4)) {
            case 0:
                y = rnd.nextInt(Koko.Y.I());
                break;
            case 1:
                x = Koko.X.I() - k / 2;
                y = rnd.nextInt(Koko.Y.I());
                break;
            case 2:
                x = rnd.nextInt(Koko.X.I());
                break;
            case 3:
                y = Koko.Y.I() - k / 2;
                x = rnd.nextInt(Koko.X.I());
                break;
        }
        int[] vxy = {vari, x, y};
        return vxy;
    }

    private static double[] suuntaVauhtiJaPyorimisnopeus(Random rnd) {
        double suunta = rnd.nextDouble() * 360;
        double gaussi = rnd.nextGaussian();
        double perusenergia = 0.5;
        if (gaussi < 0) {
            perusenergia *= -1;
        }
        double vauhti = (gaussi + perusenergia) * (0.5 + rnd.nextDouble());
        double pyorimisnopeus = rnd.nextGaussian();
        double[] svp = {suunta, vauhti, pyorimisnopeus};
        return svp;
    }

    public static Polygon getRunko() {
        Polygon r = new Polygon();
        r.getPoints().addAll(Piirtokoordinaatit.getRunko());
        return r;
    }

    public static Canvas getKuviotAlus() {
        Canvas k = new Canvas(50, 100);
        GraphicsContext gc = k.getGraphicsContext2D();
        gc.setStroke(Color.CORAL);
        gc.setLineWidth(2);
        for (double[] y : Piirtokoordinaatit.getKuviot1()) {
            gc.strokeLine(y[0], y[1], y[2], y[3]);
        }
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        for (double[] y : Piirtokoordinaatit.getKuviot2()) {
            gc.strokeLine(y[0], y[1], y[2], y[3]);
        }
        gc.setFill(Color.BLACK);
        gc.fillPolygon(new double[]{22.75, 27.25, 28.333, 21.667},
                new double[]{0, 0, 10, 10}, 4);
        gc.fillPolygon(new double[]{2.5, 10.125, 5, 0},
                new double[]{90, 90, 100, 100}, 4);
        gc.fillPolygon(new double[]{39.875, 47.5, 50, 45},
                new double[]{90, 90, 100, 100}, 4);
        gc.setFill(Color.YELLOW);
        gc.fillPolygon(new double[]{20, 30, 30, 20},
                new double[]{85, 85, 87.5, 87.5}, 4);
        return k;
    }

    public static Asteroidi getAsteroidi(Random rnd) {
        int k = (2 + rnd.nextInt(2)) * (3 + rnd.nextInt(3))
                * (4 + rnd.nextInt(4));
        int ru = k * (rnd.nextInt(51) + 25) / 10;
        Polygon muoto = new Polygon();
        muoto.getPoints().addAll(Piirtokoordinaatit.getViisikulmio(k, rnd));
        int[] vxy = variJaIlmiintyminen(k, rnd);
        double[] svp = suuntaVauhtiJaPyorimisnopeus(rnd);
        return new Asteroidi(muoto, vxy[0], vxy[1], vxy[2], svp[0], svp[1],
                svp[2], k, ru);
    }

    public static Ammus getAmmus(double x, double y, double r) {
        Polygon muoto = new Polygon(26.5, 43, 26.5, 52, 23.5, 52, 23.5, 43);
        muoto.setRotate(r);
        return new Ammus(muoto, (int) x, (int) y);
    }

    public static Canvas getSirpaleet(Paint p, List<Double> stardust) {
        Canvas cv = new Canvas(300, 300);
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(p);
        for (int i = 0; i < stardust.size() - 1; i++) {
            gc.fillOval(stardust.get(i), stardust.get(i + 1), 3, 3);
        }
        return cv;
    }

}
