package Concurrencia;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class WordsUsesCounterCI implements IWordUsesCounter {


    @Override
    public Map<String, Integer> getWordUses(String path){
        File f = new File(path);
        Map<String, Integer> result = new HashMap<>();
        Integer aux = null;
        HashMap<String, Integer> aux2 = new HashMap<>();
        if (f.isDirectory()) {
            ExecutorService executor = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors());
            List<File> txts = Arrays.stream(f.listFiles()).filter( file -> {
                return file.getAbsolutePath().endsWith(".txt");
            }).collect(Collectors.toList());

            @SuppressWarnings("unchecked")
            Future<Map<String, Integer>>[] futures = (Future<Map<String, Integer>>[]) new Future<?>[txts.size()];
            for (int i = 0; i < txts.size() ; i++) {
                futures[i] = executor.submit(new TextWordsCounter(txts.get(i).getAbsolutePath()));
            }

            //MERGE
            for (int i = 0; i < futures.length; i++){
                try {
                    for (Map.Entry<String, Integer> entry: futures[i].get().entrySet()){
                        if ( (aux = result.get(entry.getKey())) != null ){
                            result.replace(entry.getKey(), (aux + entry.getValue()));
                        }else{
                            result.put(entry.getKey(), entry.getValue());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();
        }
        return result;
    }
}
