package org.rpnkv.practive.iv.ct.exec;

import org.rpnkv.practive.iv.ct.exec.task.ExecutionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Service
public class TaskExecutionQueue implements Consumer<ExecutionTask> {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutionQueue.class);

    @Value("${queue.execution.length}")
    private int queueLength;

    private final Queue<ExecutionTask> taskQueue = new LinkedList<>();
    private final Object lock;
    private final ExecutorService executorService;

    @Autowired
    public TaskExecutionQueue(Object lock, ExecutorService executorService) {
        this.lock = lock;
        this.executorService = executorService;
    }

    @Override
    public void accept(ExecutionTask executionTask) {
        synchronized (lock){
            while (taskQueue.size() == queueLength){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting queue to release",e);
                    throw new RuntimeException(e);//TODO handle and break the program
                }
            }

            taskQueue.add(executionTask);
            executorService.execute(executionTask);
            logger.debug("submitted task {}", executionTask);

            if(taskQueue.size() == 1){//TODO check if size check is required
                lock.notify();
            }
        }
    }

    public void remove(ExecutionTask executionTask) {
        synchronized (lock){
            boolean remove = taskQueue.remove(executionTask);
            if(!remove){
                logger.error("Attempting to remove task which isn't present inside the queue: {}", executionTask);
            }

            logger.debug("removed task {}",executionTask);

            lock.notify();
        }
    }
}
