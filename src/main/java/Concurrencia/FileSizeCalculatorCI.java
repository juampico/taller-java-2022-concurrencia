package Concurrencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class FileSizeCalculatorCI extends RecursiveTask<Long> implements IFileSizeCalculator {


    private String path;
    private long size;

    public FileSizeCalculatorCI(String path){
        this.path = path;
        this.size = 0;
    }

    @Override
    protected Long compute() {
        File f = new File(this.path);
        List<FileSizeCalculatorCI> list = new ArrayList<>();
        if (f.isDirectory()){
            this.size = 0;
            for (File f1: f.listFiles()){
                list.add(new FileSizeCalculatorCI(f1.getAbsolutePath()));
            }
            invokeAll(list);
            for (FileSizeCalculatorCI fsCi: list){
                try {
                    size += fsCi.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return size;
        }
        else{
            return this.getSize(f.getAbsolutePath());
        }
    }

    @Override
    public long getSize(String path) {
        File f = new File(path);
        return f.length();
    }
}
