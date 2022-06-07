package Concurrencia;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordsUsesCounterSI implements IWordUsesCounter{

    public WordsUsesCounterSI(){

    }

    @Override
    public Map<String, Integer> getWordUses(String path) {
        File directory = new File(path);
        BufferedReader file = null;
        String line = null;
        HashMap<String, Integer> wc = new HashMap<>();
        Integer cant = null;
        for(File f: Arrays.stream(directory.listFiles()).filter(f -> {
            return f.getAbsolutePath().endsWith(".txt");
        }).collect(Collectors.toList())){
                try {
                    file = new BufferedReader(new FileReader(f.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                while (true){
                    try {
                        if (!((line = file.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
