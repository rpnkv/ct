package org.rpnkv.practive.iv.ct.rise;

import org.rpnkv.practive.iv.ct.core.DomainInfo;
import org.rpnkv.practive.iv.ct.fetch.RemainingTasksResolver;
import org.rpnkv.practive.iv.ct.fetch.DomainFetchTaskExecutionQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Running in separate thread, loads domain names, maps them to fetch tasks and submits to the queue.
 */
@Service
public class DomainInfoFetchTaskProducer implements RemainingTasksResolver, Runnable{

    /**
     * Provides domain names stream
     */
    private final DomainsFileReader domainsFileReader;

    @Qualifier("executionQueue")
    private final DomainFetchTaskExecutionQueue executionQueue;

    /**
     * Wraps {@link DomainInfo} with {@link DomainFetchTaskAsync}
     */
    private final TaskFactory taskFactory;

    private volatile boolean isCountFinal = false;
    private int submittedTasksCount = 0;

    @Autowired
    public DomainInfoFetchTaskProducer(DomainsFileReader domainsFileReader, DomainFetchTaskExecutionQueue executionQueue, TaskFactory taskFactory) {
        this.domainsFileReader = domainsFileReader;
        this.executionQueue = executionQueue;
        this.taskFactory = taskFactory;
    }

    @Override
    public boolean remainingTasksLeft(int alreadyProcessedCount) {
        if(!isCountFinal){//if we haven't processed all input lines yet - that we have tasks remaining by default
            return true;
        }else {
            return submittedTasksCount != alreadyProcessedCount;
        }
    }

    /**
     * With domain names provided, converts each string into {@link DomainInfo}, wraps by {@link DomainFetchTaskAsync}
     * and submits to {@link #executionQueue}.
     */
    @Override
    public void run() {
        domainsFileReader.getDomains()
                .map(DomainInfo::new)
                .map(taskFactory)
                .forEach(executionQueue);

        submittedTasksCount = taskFactory.getTotalCount();

        //volatile variable is set after regular in order to make changes to task count visible to the thread,
        //requesting remaining task count
        isCountFinal = true;
    }
}
