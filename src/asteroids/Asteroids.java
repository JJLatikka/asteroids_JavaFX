package asteroids;

import asteroids.logic.TapahtumienHallinta;
import javafx.application.Application;
import javafx.stage.Stage;

public class Asteroids extends Application {

    private TapahtumienHallinta hallinta;

    @Override
    public void init() throws Exception {
        this.hallinta = new TapahtumienHallinta();
    }

    public static void main(String[] args) {

        launch(Asteroids.class);

    }

    @Override
    public void start(Stage st) throws Exception {
        st.setTitle("¤  H A R V E S T I N G  ¤  A S T E R O I D S  ¤");
        st.setScene(hallinta.avaruus.nakyma);
        st.show();
    }

    @Override
    public void stop() throws Exception {
        LukeminenJaTallennus.setHarvested(hallinta.harvested);
    }

}
