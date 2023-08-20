package asteroids.ui;

import asteroids.Koko;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Avaruus {

    public ImageView taustakuva;
    public Pane alue;
    public Scene nakyma;
    public HashMap<KeyCode, Boolean> nappaimisto;

    public Avaruus() {
        setTaustakuva();
        setAsettelu();
        setSC();
    }

    private void setTaustakuva() {
        Image im = new Image("file:mausteet/nebula.jpg");
        PixelReader pr = im.getPixelReader();
        WritableImage wi = new WritableImage(Koko.X.I(), Koko.Y.I());
        PixelWriter pw = wi.getPixelWriter();
        for (int y = 0; y < Koko.Y.I(); y++) {
            for (int x = 0; x < Koko.X.I(); x++) {
                Color c = pr.getColor(x + 198, y + 225);
                pw.setColor(x, y, c);
            }
        }
        this.taustakuva = new ImageView(wi);
    }

    private void setAsettelu() {
        this.alue = new Pane();
        alue.getChildren().add(taustakuva);
        alue.setPrefSize(Koko.X.I(), Koko.Y.I());
    }

    private void setSC() {
        this.nakyma = new Scene(alue);
        this.nappaimisto = new HashMap<>();
        nakyma.setOnKeyPressed(e -> nappaimisto.put(e.getCode(), true));
        nakyma.setOnKeyReleased(e -> nappaimisto.put(e.getCode(), false));
    }

}
