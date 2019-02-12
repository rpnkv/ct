package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.core.DomainInfo;
import org.rpnkv.practive.iv.ct.fetch.RemainingTasksResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Fetches filled {@link DomainInfo} from {@link DomainInfoPersistQueue} and invokes persist task.
 */
@Service
public class PersistingQueueConsumer implements Runnable{

    private final DomainInfoPersistQueue domainInfoPersistQueue;
    private final RemainingTasksResolver remainingTasksResolver;
    private final DomainInfoToFileWriter persistPerformer;
    private int persistedTasksCount;

    @Autowired
    public PersistingQueueConsumer(DomainInfoPersistQueue domainInfoPersistQueue, RemainingTasksResolver remainingTasksResolver,
                                   DomainInfoToFileWriter persistPerformer) {
        this.domainInfoPersistQueue = domainInfoPersistQueue;
        this.remainingTasksResolver = remainingTasksResolver;
        this.persistPerformer = persistPerformer;
        persistedTasksCount = 0;
    }

    /**
     * Keeps asking {@link #domainInfoPersistQueue} for new {@link DomainInfo}s while {@link #remainingTasksResolver},
     * provided with current persisted tasks count returns TRUE.
     */
    @Override
    public void run() {
        while (remainingTasksResolver.remainingTasksLeft(persistedTasksCount)){
            DomainInfo nextDomainInfo = domainInfoPersistQueue.next();
            persistPerformer.persist(nextDomainInfo);
            persistedTasksCount++;
        }

        persistPerformer.close();
    }
}
