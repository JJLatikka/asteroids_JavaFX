package asteroids.ui;

import asteroids.Koko;
import asteroids.LukeminenJaTallennus;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class Aloitusvalikko {

    private final double prantti;
    private final Color vari1;
    private final Color vari2;
    private final ToggleGroup valinta;
    private Map<Node, Node> pelaajatJaSaaliit;
    private final String johdanto;
    private List<Text> tekstielementit;
    public Button aloita;
    public Button jatka;
    public BorderPane valikko;
    public String pelaaja;

    public Aloitusvalikko(Map<String, Integer> harvested) {
        this.prantti = 18;
        this.vari1 = Color.YELLOW;
        this.vari2 = Color.BLACK;
        this.valinta = new ToggleGroup();
        setPJS(harvested);
        setValinta();
        this.johdanto = LukeminenJaTallennus.getJohdanto();
        setTE(harvested);
        this.aloita = setAloita();
        this.jatka = setJatka();
        setValikko();
        this.pelaaja = null;
    }

    public void setTE(Map<String, Integer> harvested) {
        String[] tekstit = {"\tJohdanto:", johdanto, "\tValitse, kuka pelaa!",
            "Pelaaja:", "Saalis tähän mennessä:", "\tParas kertasaalis: "
            + String.valueOf(harvested.get("Paras"))};
        this.tekstielementit = Arrays.asList(tekstit).stream().map(t -> {
            Text tx = new Text(t);
            tx.setFont(Font.font(prantti));
            tx.setFill(vari1);
            return tx;
        }).collect(Collectors.toList());
    }

    public void setPJS(Map<String, Integer> harvested) {
        this.pelaajatJaSaaliit = harvested.keySet().stream()
                .filter(k -> !k.equals("Paras"))
                .map(k -> {
                    RadioButton rb = new RadioButton(k);
                    rb.setToggleGroup(valinta);
                    rb.setUserData(k);
                    rb.setFont(Font.font(prantti));
                    rb.setTextFill(vari1);
                    Text t = new Text("\t" + String.valueOf(harvested.get(k)));
                    t.setFont(Font.font(prantti));
                    t.setFill(vari1);
                    Node[] pelaajaJaSaalis = {rb, t};
                    return pelaajaJaSaalis;
                }).collect(Collectors.toMap(pjs -> pjs[0], pjs -> pjs[1]));
    }

    private Button setAloita() {
        Button b = new Button("Aloita peli");
        b.setBackground(new Background(new BackgroundFill(vari1,
                CornerRadii.EMPTY, Insets.EMPTY)));
        b.setFont(Font.font(prantti));
        b.setTextFill(vari2);
        return b;
    }

    private Button setJatka() {
        Button b = new Button("Uusi yritys");
        b.setBackground(new Background(new BackgroundFill(vari2,
                CornerRadii.EMPTY, Insets.EMPTY)));
        b.setFont(Font.font(prantti));
        b.setTextFill(Color.BROWN);
        return b;
    }

    public void setValikko() {
        this.valikko = new BorderPane();
        valikko.setPadding(new Insets(25, 0, 0, 100));
        valikko.setPrefSize(Koko.X.I(), Koko.Y.I());
        valikko.setBackground(new Background(new BackgroundFill(vari2,
                CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane gp1 = new GridPane();
        gp1.setVgap(25);
        gp1.add(tekstielementit.get(0), 0, 0);
        gp1.add(tekstielementit.get(1), 0, 1);
        valikko.setTop(gp1);
        GridPane gp2 = new GridPane();
        gp2.setPadding(new Insets(25, 0, 0, 0));
        gp2.setHgap(50);
        gp2.setVgap(25);
        gp2.add(tekstielementit.get(2), 0, 0);
        gp2.add(tekstielementit.get(3), 0, 1);
        gp2.add(tekstielementit.get(4), 1, 1);
        gp2.add(tekstielementit.get(5), 0, pelaajatJaSaaliit.keySet().size() + 3);
        List<Node> l = pelaajatJaSaaliit.keySet().stream()
                .collect(Collectors.toList());
        for (int y = 0; y < l.size(); y++) {
            Node rb = l.get(y);
            gp2.add(rb, 0, y + 2);
            Node tx = pelaajatJaSaaliit.get(rb);
            gp2.add(tx, 1, y + 2);
        }
        gp2.add(aloita, 9, 4);
        valikko.setCenter(gp2);
    }

    private void setValinta() {
        valinta.selectedToggleProperty().addListener((muutos, vanha, uusi) -> {
            if (valinta.getSelectedToggle().isSelected()) {
                pelaaja = (String) valinta.getSelectedToggle().getUserData();
            }
        });
    }

}
