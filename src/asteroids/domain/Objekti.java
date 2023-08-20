package asteroids.domain;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public abstract class Objekti {

    public Polygon muoto;
    public Canvas kuviot;
    public final boolean kuviotOn;
    public int variasetus;
    private Point2D muutos;
    public Paint sirpaleidenVari;
    private double mx;
    private double my;

    public Objekti(Polygon m, Canvas k, boolean ko, int vari, int x, int y) {
        this.muoto = m;
        this.kuviot = k;
        this.kuviotOn = ko;
        this.muoto.setTranslateX(x);
        this.muoto.setTranslateY(y);
        this.kuviot.setTranslateX(x);
        this.kuviot.setTranslateY(y);
        this.variasetus = vari;
        this.muutos = new Point2D(0, 0);
        setVari(vari);
    }

    public Objekti(Polygon m, boolean ko, int vari, int x, int y) {
        this(m, new Canvas(), ko, vari, x, y);
    }

    public final void setVari(int i) {
        switch (i) {
            case 0:
                muoto.setFill(Color.RED);
                break;
            case 1:
                muoto.setFill(Color.DIMGREY);
                break;
            case 2:
                muoto.setFill(Color.BURLYWOOD);
                break;
            case 3:
                muoto.setFill(Color.LIGHTGRAY);
                break;
            case 4:
                muoto.setFill(Color.DARKGREY);
                break;
            case 5:
                muoto.setFill(Color.BLACK);
                break;
            case 6:
                muoto.setFill(Color.GREENYELLOW);
                break;
        }
    }

    public void kaanna(double s) {
        muoto.setRotate(muoto.getRotate() + s);
        if (kuviotOn) {
            kuviot.setRotate(kuviot.getRotate() + s);
        }
    }

    public void nopeudenMuutos(double s) {
        mx = s * 0.5 * Math.cos(Math.toRadians(muoto.getRotate() - 90));
        my = s * 0.5 * Math.sin(Math.toRadians(muoto.getRotate() - 90));
        muutos = muutos.add(mx, my);
    }

    public void liiku() {
        muoto.setTranslateX(muoto.getTranslateX() + mx);
        muoto.setTranslateY(muoto.getTranslateY() + my);
        if (kuviotOn) {
            kuviot.setTranslateX(kuviot.getTranslateX() + mx);
            kuviot.setTranslateY(kuviot.getTranslateY() + my);
        }
    }

    public void siirraSuunnassaX(double kohta) {
        muoto.setTranslateX(kohta);
        if (kuviotOn) {
            kuviot.setTranslateX(kohta);
        }
    }

    public void siirraSuunnassaY(double kohta) {
        muoto.setTranslateY(kohta);
        if (kuviotOn) {
            kuviot.setTranslateY(kohta);
        }
    }

}
