package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.get.PullTask;
import org.rpnkv.practive.iv.ct.read.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Stream;

@ComponentScan
public class Main {

    @Autowired
    private FileReader fileReader;

    public static void main(String[] args) throws IOException, InterruptedException {

    }

    private void start(){
        fileReader.start();
    }
}
