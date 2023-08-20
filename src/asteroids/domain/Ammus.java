package asteroids.domain;

import javafx.scene.shape.Polygon;

public class Ammus extends Objekti {

    public boolean osunut;

    public Ammus(Polygon po, int x, int y) {
        super(po, false, 6, x, y);
        super.nopeudenMuutos(9);
        this.osunut = false;
    }

}
