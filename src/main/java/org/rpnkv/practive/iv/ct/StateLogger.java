package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.fetch.DomainFetchTaskExecutionQueue;
import org.rpnkv.practive.iv.ct.persist.DomainInfoPersistQueue;
import org.rpnkv.practive.iv.ct.persist.PersistingQueueConsumer;
import org.rpnkv.practive.iv.ct.rise.TaskFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class StateLogger implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(StateLogger.class);

    @Value("${state.logger.delay}")
    private int delay;

    private final DomainFetchTaskExecutionQueue executionQueue;
    private final DomainInfoPersistQueue persistQueue;
    private final TaskFactory taskFactory;
    private final PersistingQueueConsumer persistingQueueConsumer;


    public StateLogger(DomainFetchTaskExecutionQueue executionQueue, DomainInfoPersistQueue persistQueue,
                       TaskFactory taskFactory, PersistingQueueConsumer persistingQueueConsumer) {
        this.executionQueue = executionQueue;
        this.persistQueue = persistQueue;
        this.taskFactory = taskFactory;
        this.persistingQueueConsumer = persistingQueueConsumer;
    }

    @PostConstruct
    public void init(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this, 2, delay, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        logger.info("Submitted {} tasks, execution queue {}/{}; persist queue {}/{}; persisted: {}",
                taskFactory.getTotalCount(), executionQueue.getCurrentActiveTasksCount(),
                executionQueue.getMaxActiveTasksCount(), persistQueue.getCurrentActiveTasksCount(),
                persistQueue.getMaxActiveTasksCount(), persistingQueueConsumer.getPersistedDomainsCount());
    }
}
