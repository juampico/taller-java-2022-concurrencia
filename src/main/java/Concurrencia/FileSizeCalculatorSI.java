package Concurrencia;

import java.io.File;

public class FileSizeCalculatorSI implements IFileSizeCalculator {

    public FileSizeCalculatorSI(){

    }

    public long getSize(String path){
        File f = new File(path);
        if (f.isDirectory()){
            long size = 0;
            for (File f1: f.listFiles()){
                size += getSize(f1.getAbsolutePath());
            }
            return size;
        }
        else{
            return f.length();
        }
    }
}
