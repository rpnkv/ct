package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.rise.DomainFetchTaskAsync;
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
public class DomainFetchTaskExecutionQueue implements Consumer<DomainFetchTaskAsync> {

    private static final Logger logger = LoggerFactory.getLogger(DomainFetchTaskExecutionQueue.class);

    @Value("${queue.execution.length}")
    private int queueLength;

    private final Queue<DomainFetchTaskAsync> taskQueue = new LinkedList<>();
    private final Object lock;
    private final ExecutorService executorService;

    @Autowired
    public DomainFetchTaskExecutionQueue(Object lock, ExecutorService executorService) {
        this.lock = lock;
        this.executorService = executorService;
    }

    @Override
    public void accept(DomainFetchTaskAsync domainFetchTaskAsync) {
        synchronized (lock){
            while (taskQueue.size() == queueLength){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting queue to release",e);
                    throw new RuntimeException(e);//TODO handle and break the program
                }
            }

            taskQueue.add(domainFetchTaskAsync);
            executorService.execute(domainFetchTaskAsync);
            logger.debug("submitted task {}", domainFetchTaskAsync);

            if(taskQueue.size() == 1){//TODO check if size check is required
                lock.notify();
            }
        }
    }

    public void remove(DomainFetchTaskAsync domainFetchTaskAsync) {
        synchronized (lock){
            /*boolean remove = taskQueue.remove(domainFetchTaskAsync);
            if(!remove){
                logger.error("Attempting to remove task which isn't present inside the queue: {}", domainFetchTaskAsync);
            }*/

            taskQueue.poll();//it appears that doesn't actually matter, which task is removed //TODO create semaphore
            logger.debug("removed task {}", domainFetchTaskAsync);

            lock.notify();
        }
    }
}
