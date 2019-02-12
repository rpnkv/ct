package org.rpnkv.practive.iv.ct.io;

import org.rpnkv.practive.iv.ct.get.PullTask;
import org.rpnkv.practive.iv.ct.get.PullTaskFactory;
import org.rpnkv.practive.iv.ct.get.Site;
import org.rpnkv.practive.iv.ct.synchronize.count.TaskCounterSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Component
@PropertySource("classpath:application.properties")
public class FileReader {

    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    @Value("${file.input}")
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
                    .map(Site::new)
                    .map(taskFactory::create)
                    .map(this::appendToExecutorService)
                    .count();

            taskCounterSetter.setTaskCount((int) tasksCount);
            logger.info("Submitted {} tasks", tasksCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PullTask appendToExecutorService(PullTask pullTask) {
        executorService.execute(pullTask);
        try {
            Thread.sleep(
                    0,500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pullTask;
    }

}
