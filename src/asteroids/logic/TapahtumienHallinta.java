package asteroids.logic;

import asteroids.Koko;
import asteroids.LukeminenJaTallennus;
import asteroids.av.Aanet;
import asteroids.av.Grafiikka;
import asteroids.av.Piirtokoordinaatit;
import asteroids.domain.Alus;
import asteroids.domain.Ammus;
import asteroids.domain.Asteroidi;
import asteroids.domain.Objekti;
import asteroids.ui.Avaruus;
import asteroids.ui.Aloitusvalikko;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TapahtumienHallinta {

    public Map<String, Integer> harvested;
    public final Avaruus avaruus;
    private final Aloitusvalikko valikko;
    private String pelaaja;
    private final Aanet aanet;
    private final Random rnd;
    private Alus alus;
    private final AtomicInteger harvest;
    private final Text RUs;
    private final List<Asteroidi> asteroidit;
    private final List<Ammus> laukaukset;
    private final List<Canvas> sirpaleet;
    private final List<Double> stardust;
    private AnimationTimer at;

    public TapahtumienHallinta() {

        this.harvested = LukeminenJaTallennus.getHarvested();
        this.avaruus = new Avaruus();
        this.valikko = new Aloitusvalikko(harvested);
        this.aanet = new Aanet();
        this.rnd = new Random();
        this.alus = null;
        this.harvest = new AtomicInteger();
        this.RUs = new Text(20, Koko.Y.I() - 100, "Resource Units: " + harvest);
        this.asteroidit = new ArrayList<>();
        this.laukaukset = new ArrayList<>();
        this.sirpaleet = new ArrayList<>();
        this.stardust = Piirtokoordinaatit.getSirpaleet(rnd);
        this.at = setAT();
        additionalSettings();
        kaynnistys();
    }

    private void kaynnistys() {
        aanet.musiikki();
        avaruus.alue.getChildren().add(valikko.valikko);
    }

    private AnimationTimer setAT() {
        this.at = new AnimationTimer() {

            @Override
            public void handle(long now) {
                aanet.musiikki();
                lisaaAsteroideja();
                ohjaus();
                osumat();
                rajahdykset();
                imuri();
                liikkumitor();
            }

            private void lisaaAsteroideja() {
                if (asteroidit.size() < 11) {
                    Asteroidi a = Grafiikka.getAsteroidi(rnd);
                    asteroidit.add(a);
                    avaruus.alue.getChildren().add(a.muoto);
                }
            }

            private void ohjaus() {
                if (avaruus.nappaimisto.getOrDefault(KeyCode.LEFT, false)) {
                    alus.kaanna(-1.5);
                }
                if (avaruus.nappaimisto.getOrDefault(KeyCode.RIGHT, false)) {
                    alus.kaanna(1.5);
                }
                if (avaruus.nappaimisto.getOrDefault(KeyCode.UP, false)) {
                    aanet.tyontovoima(true);
                    alus.nopeudenMuutos(3);
                }
                if (avaruus.nappaimisto.getOrDefault(KeyCode.DOWN, false)) {
                    aanet.tyontovoima(true);
                    alus.nopeudenMuutos(-1);
                }
                if (avaruus.nappaimisto.getOrDefault(KeyCode.SHIFT, false)) {
                    aanet.tyontovoima(false);
                    alus.nopeudenMuutos(0);
                }
                if (avaruus.nappaimisto.getOrDefault(KeyCode.SPACE, false)
                        && laukaukset.size() < 2) {
                    laukaus();
                    avaruus.nappaimisto.put(KeyCode.SPACE, false);
                }
            }

            private void laukaus() {
                double x = alus.muoto.getTranslateX();
                double y = alus.muoto.getTranslateY();
                double r = alus.muoto.getRotate();
                Ammus a = Grafiikka.getAmmus(x, y, r);
                laukaukset.add(a);
                avaruus.alue.getChildren().add(a.muoto);
                aanet.laaseri();
            }

            private void osumat() {
                laukaukset.forEach(l -> {
                    asteroidit.forEach(a -> {
                        if (Shape.intersect(l.muoto, a.muoto)
                                .getBoundsInLocal().getWidth() != -1) {
                            l.osunut = true;
                            a.hp--;
                            if (a.hp == a.sulamispiste) {
                                a.setVari(0);
                            }
                        }
                    });
                });
            }

            private void rajahdykset() {
                asteroidit.stream().filter(a -> a.hp <= 0)
                        .collect(Collectors.toList()).forEach(r -> {
                    harvest.getAndAdd(r.resourceUnits);
                    RUs.setText("Resource Units: " + harvest);
                    asteroidit.remove(r);
                    stardust(r);
                });
                if (asteroidit.stream().filter(a
                        -> Shape.intersect(a.muoto, alus.muoto)
                                .getBoundsInLocal().getWidth() != -1)
                        .count() != 0) {
                    rip();
                }
            }

            private void stardust(Objekti r) {
                double x = r.muoto.getTranslateX();
                double y = r.muoto.getTranslateY();
                Canvas s = Grafiikka.getSirpaleet(r.sirpaleidenVari, stardust);
                s.setTranslateX(x - 150);
                s.setTranslateY(y - 125);
                avaruus.alue.getChildren().remove(r.muoto);
                if (r.kuviotOn) {
                    avaruus.alue.getChildren().remove(r.kuviot);
                }
                sirpaleet.add(s);
                avaruus.alue.getChildren().add(s);
                if (r.muoto == alus.muoto) {
                    aanet.rajahdys2();
                } else {
                    aanet.rajahdys1();
                }
            }

            private void imuri() {
                if (sirpaleet.size() > 5) {
                    avaruus.alue.getChildren().remove(sirpaleet.get(0));
                    sirpaleet.remove(0);
                }
                laukaukset.stream().filter(a -> a.muoto.getTranslateX() < -25
                        || a.muoto.getTranslateX() > Koko.X.I()
                        || a.muoto.getTranslateY() < -50
                        || a.muoto.getTranslateY() > Koko.Y.I() || a.osunut
                ).collect(Collectors.toList()).forEach(a -> {
                    laukaukset.remove(a);
                    avaruus.alue.getChildren().remove(a.muoto);
                });
            }

            private void liikkumitor() {
                asteroidit.forEach(a -> {
                    a.liiku();
                    a.pyori();
                });
                alus.liiku();
                laukaukset.forEach(a -> a.liiku());
            }

            private void rip() {
                stardust(alus);
                at.stop();
                avaruus.alue.getChildren().add(valikko.jatka);
                valikko.jatka.setTranslateX(Koko.X.I() - 125);
                valikko.jatka.setTranslateY(Koko.Y.I() - 125);
            }

        };
        return at;
    }

    private void additionalSettings() {
        RUs.setFont(Font.font(15));
        RUs.setFill(Color.BROWN);
        avaruus.alue.getChildren().add(RUs);
        valikko.aloita.setOnAction(e -> {
            if (valikko.pelaaja != null) {
                aloitus();
            }
        });
        valikko.jatka.setOnAction(e -> {
            poistous();
            jatkous();
        });
    }

    private void aloitus() {
        pelaaja = valikko.pelaaja;
        alus = new Alus(Koko.X.I() / 2 - 25, Koko.Y.I() / 2 - 100);
        avaruus.alue.getChildren().remove(valikko.valikko);
        avaruus.alue.getChildren().addAll(alus.muoto, alus.kuviot);
        at.start();
    }

    private void poistous() {
        avaruus.alue.getChildren().remove(valikko.jatka);
        asteroidit.forEach(a -> {
            avaruus.alue.getChildren().remove(a.muoto);
        });
        if (!sirpaleet.isEmpty()) {
            sirpaleet.forEach(s -> {
                avaruus.alue.getChildren().remove(s);
            });
        }
        if (!laukaukset.isEmpty()) {
            laukaukset.forEach(l -> {
                avaruus.alue.getChildren().remove(l.muoto);
            });
        }
        asteroidit.clear();
        sirpaleet.clear();
        laukaukset.clear();
    }

    private void jatkous() {
        Integer tulos = harvest.intValue();
        harvested.put(pelaaja, harvested.get(pelaaja) + tulos);
        if (tulos > harvested.get("Paras")) {
            harvested.put("Paras", tulos);
            valikko.setTE(harvested);
        }
        valikko.setPJS(harvested);
        valikko.pelaaja = null;
        harvest.set(0);
        valikko.setValikko();
        kaynnistys();
    }

}
