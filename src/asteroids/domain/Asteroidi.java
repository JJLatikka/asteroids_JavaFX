package asteroids.domain;

import asteroids.Koko;
import javafx.scene.shape.Polygon;

public class Asteroidi extends Objekti {

    private final double pyorimisnopeus;
    private final int koko;
    public int resourceUnits;
    private final int hpMax;
    public int hp;
    public int sulamispiste;

    public Asteroidi(Polygon po, int vari, int x, int y, double s, double v,
            double p, int k, int ru) {
        super(po, false, vari, x, y);
        super.kaanna(s);
        super.nopeudenMuutos(v);
        this.pyorimisnopeus = p;
        super.sirpaleidenVari = super.muoto.getFill();
        this.koko = k;
        this.resourceUnits = ru;
        this.hpMax = k / 20;
        this.hp = k / 20;
        this.sulamispiste = hpMax / 2;
    }

    @Override
    public void liiku() {
        super.liiku();
        if (super.muoto.getTranslateX() > Koko.X.I() + koko) {
            super.siirraSuunnassaX(0 - koko);
            jaahdyta();
        }
        if (super.muoto.getTranslateX() < 0 - koko) {
            super.siirraSuunnassaX(Koko.X.I() + koko);
            jaahdyta();
        }
        if (super.muoto.getTranslateY() > Koko.Y.I() + koko) {
            super.siirraSuunnassaY(0 - koko);
            jaahdyta();
        }
        if (super.muoto.getTranslateY() < 0 - koko) {
            super.siirraSuunnassaY(Koko.Y.I() + koko);
            jaahdyta();
        }
    }

    public void pyori() {
        super.kaanna(pyorimisnopeus);
    }

    private void jaahdyta() {
        super.setVari(super.variasetus);
        hp = hpMax;
    }

}
