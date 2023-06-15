package pt.isec.pa.tinypac.top5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(scores,new StringComparator());
        return scores;
    }

    static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int number1 = Integer.parseInt(s1.split(",")[0]);
            int number2 = Integer.parseInt(s2.split(",")[0]);
            return Integer.compare(number2, number1);
        }
    }
}
