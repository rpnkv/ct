package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.rise.DomainFetchTaskAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Service
public class DomainFetchTaskExecutionQueue implements Consumer<DomainFetchTaskAsync> {

    private static final Logger logger = LoggerFactory.getLogger(DomainFetchTaskExecutionQueue.class);

    @Value("${queue.execution.length}")
    private int queueLength;

    private int tasksCount = 0;
    private final Object lock;
    private final ExecutorService executorService;

    @Autowired
    public DomainFetchTaskExecutionQueue(Object lock, ExecutorService executorService) {
        this.lock = lock;
        this.executorService = executorService;
    }

    @Override
    public void accept(DomainFetchTaskAsync domainFetchTaskAsync) {
        synchronized (lock) {
            while (tasksCount == queueLength) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting queue to release", e);
                    throw new RuntimeException(e);//TODO handle and break the program
                }
            }

            executorService.execute(domainFetchTaskAsync);
            tasksCount++;
            logger.debug("submitted task {}", domainFetchTaskAsync);

            lock.notify();
        }
    }

    public void remove(DomainFetchTaskAsync domainFetchTaskAsync) {
        synchronized (lock) {
            tasksCount--;
            logger.debug("removed task {}", domainFetchTaskAsync);

            lock.notify();
        }
    }
}
