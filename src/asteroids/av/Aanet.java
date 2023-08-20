package asteroids.av;

import javafx.scene.media.AudioClip;

public class Aanet {

    public final AudioClip musiikki;
    private final AudioClip tyontovoima;
    private final AudioClip laaseri;
    private final AudioClip rajahdys1;
    public final AudioClip rajahdys2;

    public Aanet() {
        this.musiikki = new AudioClip("file:mausteet/Battlestar.wav");
        this.tyontovoima = new AudioClip("file:mausteet/Thrusters.wav");
        this.laaseri = new AudioClip("file:mausteet/Laser.wav");
        this.rajahdys1 = new AudioClip("file:mausteet/Explosion.wav");
        this.rajahdys2 = new AudioClip("file:mausteet/Nuke.wav");
    }

    public void musiikki() {
        if (!musiikki.isPlaying()) {
            musiikki.play();
        }
    }

    public void tyontovoima(boolean paalle) {
        if (!tyontovoima.isPlaying() && paalle) {
            tyontovoima.play(0.5);
        }
        if (tyontovoima.isPlaying() && !paalle) {
            tyontovoima.stop();
        }
    }

    public void laaseri() {
        laaseri.setVolume(0.001);
        laaseri.play();
    }

    public void rajahdys1() {
        rajahdys1.play(0.25);
    }

    public void rajahdys2() {
        rajahdys1.play(0.75);
        rajahdys2.play(0.5);
    }

}
