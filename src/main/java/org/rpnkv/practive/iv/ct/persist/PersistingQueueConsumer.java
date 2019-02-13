package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.DomainInfo;
import org.rpnkv.practive.iv.ct.fetch.RemainingTasksResolver;
import org.slf4j.LoggerFactory;
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
    private int persistedDomainsCount;

    @Autowired
    public PersistingQueueConsumer(DomainInfoPersistQueue domainInfoPersistQueue, RemainingTasksResolver remainingTasksResolver,
                                   DomainInfoToFileWriter persistPerformer) {
        this.domainInfoPersistQueue = domainInfoPersistQueue;
        this.remainingTasksResolver = remainingTasksResolver;
        this.persistPerformer = persistPerformer;
        persistedDomainsCount = 0;
    }

    /**
     * Keeps asking {@link #domainInfoPersistQueue} for new {@link DomainInfo}s while {@link #remainingTasksResolver},
     * provided with current persisted tasks count returns TRUE.
     */
    @Override
    public void run() {
        while (remainingTasksResolver.remainingTasksLeft(persistedDomainsCount)){
            DomainInfo nextDomainInfo = domainInfoPersistQueue.next();
            persistPerformer.persist(nextDomainInfo);
            persistedDomainsCount++;
        }

        LoggerFactory.getLogger(PersistingQueueConsumer.class).info("Persisted {} sites", persistedDomainsCount);
        persistPerformer.close();
    }

    public int getPersistedDomainsCount() {
        return persistedDomainsCount;
    }
}
