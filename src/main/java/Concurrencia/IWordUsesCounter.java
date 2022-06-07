package Concurrencia;

import java.util.Map;

public interface IWordUsesCounter {


    public Map<String, Integer> getWordUses(String path);
}
