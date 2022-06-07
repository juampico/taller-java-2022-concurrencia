package Concurrencia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextWordsCounter implements Callable<Map<String, Integer>> {

    public String path;

    public TextWordsCounter(String path){
        this.path = path;
    }


    @Override
    public Map<String, Integer> call() throws IOException {
        BufferedReader file = null;
        String line;
        Integer cant = null;
        Map<String, Integer> wc = new HashMap<>();
        if (path.endsWith(".txt")) {
            try {
                file = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while ((line = file.readLine()) != null){
                for (String str: line.split(" ")){
                    if ((cant = wc.get(str)) != null)
                        wc.replace(str, (cant+1));
                    else
                        wc.put(str, 1);
                }
            }
        }
        return wc;
    }
}
