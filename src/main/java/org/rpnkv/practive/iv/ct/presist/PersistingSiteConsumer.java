package org.rpnkv.practive.iv.ct.presist;

import org.rpnkv.practive.iv.ct.core.Site;
import org.rpnkv.practive.iv.ct.exec.RemainingTasksResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistingSiteConsumer implements Runnable{

    private final PersistQueue persistQueue;
    private final RemainingTasksResolver remainingTasksResolver;
    private final SitePersistPerformer persistPerformer;
    private int persistedTasksCount;

    @Autowired
    public PersistingSiteConsumer(PersistQueue persistQueue, RemainingTasksResolver remainingTasksResolver,
                                  SitePersistPerformer persistPerformer) {
        this.persistQueue = persistQueue;
        this.remainingTasksResolver = remainingTasksResolver;
        this.persistPerformer = persistPerformer;
        persistedTasksCount = 0;
    }


    @Override
    public void run() {
        while (remainingTasksResolver.remainingTasksLeft(persistedTasksCount)){
            Site nextSite = persistQueue.next();
            persistPerformer.persist(nextSite);
        }

        persistPerformer.close();
    }
}
