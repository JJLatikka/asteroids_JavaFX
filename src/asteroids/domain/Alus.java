package asteroids.domain;

import asteroids.Koko;
import asteroids.av.Grafiikka;
import javafx.scene.paint.Color;

public class Alus extends Objekti {

    public Alus(int x, int y) {
        super(Grafiikka.getRunko(), Grafiikka.getKuviotAlus(), true, 4, x, y);
        super.sirpaleidenVari = Color.YELLOW;
    }

    @Override
    public void kaanna(double s) {
        super.kaanna(s);
    }

    @Override
    public void liiku() {
        if (muoto.getTranslateX() > -20
                && muoto.getTranslateX() < Koko.X.I() - 20
                && muoto.getTranslateY() > -40
                && muoto.getTranslateY() < Koko.Y.I() - 140) {
            super.liiku();
        } else {
            if (muoto.getTranslateX() <= -20) {
                super.siirraSuunnassaX(-19.999);
            }
            if (muoto.getTranslateX() >= Koko.X.I() - 20) {
                super.siirraSuunnassaX(Koko.X.I() - 20.001);
            }
            if (muoto.getTranslateY() <= -40) {
                super.siirraSuunnassaY(-39.999);
            }
            if (muoto.getTranslateY() >= 628) {
                super.siirraSuunnassaY(Koko.Y.I() - 140.001);
            }
        }
    }

}
