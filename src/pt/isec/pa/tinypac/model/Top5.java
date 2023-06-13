package pt.isec.pa.tinypac.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Top5 {
    List<String> scores;
    public Top5() {
        this.scores = new ArrayList<>();
    }

    public  List<String> scores(){
        try {
            scores = Files.readAllLines(Path.of("files/scores.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scores.sort((o1, o2) -> Character.compare(o2.charAt(0), o1.charAt(0)));
        return scores;
    }
}
