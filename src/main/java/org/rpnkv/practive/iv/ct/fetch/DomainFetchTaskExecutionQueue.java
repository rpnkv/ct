package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.rise.DomainFetchTaskAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

@Service
public class DomainFetchTaskExecutionQueue implements Consumer<DomainFetchTaskAsync> {

    private static final Logger logger = LoggerFactory.getLogger(DomainFetchTaskExecutionQueue.class);

    @Value("${queue.execution.length}")
    private int queueLength;

    private Semaphore semaphore;
    private final ExecutorService executorService;

    @Autowired
    public DomainFetchTaskExecutionQueue(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @PostConstruct
    public void init() {
        semaphore = new Semaphore(queueLength - 1);
    }

    @Override
    public void accept(DomainFetchTaskAsync domainFetchTaskAsync) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            logger.error("Failed waiting queue to release", e);
            throw new RuntimeException(e);//TODO handle and break the program
        }

        executorService.execute(domainFetchTaskAsync);
        logger.debug("submitted task {}", domainFetchTaskAsync);

    }

    public void remove(DomainFetchTaskAsync domainFetchTaskAsync) {
        semaphore.release();
        logger.debug("removed task {}", domainFetchTaskAsync);
    }

    public int getCurrentActiveTasksCount() {
        return queueLength - 1 - semaphore.availablePermits();
    }

    public int getMaxActiveTasksCount() {
        return queueLength;
    }
}
