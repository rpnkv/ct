package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.rise.DomainFetchTaskAsync;
import org.rpnkv.practive.iv.ct.persist.DomainInfoPersistQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * After {@link DomainFetchTaskAsync} is executed, this class submits {@link org.rpnkv.practive.iv.ct.core.DomainInfo}
 * to {@link DomainInfoPersistQueue} and removes task from {@link DomainFetchTaskExecutionQueue}
 */
@Service
public class TaskExecutedCallbackImpl implements TaskExecutedCallback {

    private final DomainInfoPersistQueue domainInfoPersistQueue;
    private final DomainFetchTaskExecutionQueue executionQueue;

    @Autowired
    public TaskExecutedCallbackImpl(DomainInfoPersistQueue domainInfoPersistQueue, DomainFetchTaskExecutionQueue executionQueue) {
        this.domainInfoPersistQueue = domainInfoPersistQueue;
        this.executionQueue = executionQueue;
    }

    @Override
    public void accept(DomainFetchTaskAsync domainFetchTaskAsync) {
        domainInfoPersistQueue.submit(domainFetchTaskAsync.getDomainInfo());
        executionQueue.remove(domainFetchTaskAsync);
    }
}
