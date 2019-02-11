package org.rpnkv.practive.iv.ct.read;

import org.apache.commons.lang3.NotImplementedException;
import org.rpnkv.practive.iv.ct.get.PullTask;
import org.rpnkv.practive.iv.ct.get.PullTaskFactory;
import org.rpnkv.practive.iv.ct.synchronize.count.TaskCounterSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Component
public class FileReader {

    @Value("file.inputPath")
    private String inputPath;

    private final TaskCounterSetter taskCounterSetter;

    private final ExecutorService executorService;

    private final PullTaskFactory taskFactory;

    @Autowired
    public FileReader(TaskCounterSetter taskCounterSetter, ExecutorService executorService, PullTaskFactory taskFactory) {
        this.taskCounterSetter = taskCounterSetter;
        this.executorService = executorService;
        this.taskFactory = taskFactory;
    }

    public void start() {
        Path path = Paths.get(inputPath);

        try {
            long tasksCount = Files.lines(path)
                    .map(taskFactory::create)
                    .map(this::appendToExecutorService)
                    .count();

            taskCounterSetter.setTaskCount((int) tasksCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PullTask appendToExecutorService(PullTask pullTask) {
        throw new NotImplementedException("");
    }

}
