package asteroids;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LukeminenJaTallennus {

    public static Map<String, Integer> getHarvested() {
        Map<String, Integer> harvested = new HashMap<>();
        try {
            harvested = Files.lines(Paths.get("mausteet/harvested.txt"))
                    .map(l -> l.split(",")).collect(Collectors.toMap(p -> p[0],
                    p -> Integer.parseInt(p[1])));
        } catch (IOException e) {
            System.out.println(e);
        }
        return harvested;
    }

    public static String getJohdanto() {
        StringBuilder sb = new StringBuilder();
        try {
            Files.lines(Paths.get("mausteet/johdanto.txt")).forEach(r
                    -> sb.append(r).append("\n"));
        } catch (IOException e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    public static void setHarvested(Map<String, Integer> harvested) {
        List<String> saaliit = harvested.keySet().stream().map(k -> k + ","
                + harvested.get(k)).collect(Collectors.toList());
        try {
            Files.write(Paths.get("mausteet/harvested.txt"), saaliit);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
