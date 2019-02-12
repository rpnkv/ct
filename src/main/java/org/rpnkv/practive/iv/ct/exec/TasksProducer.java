package org.rpnkv.practive.iv.ct.exec;

import org.springframework.stereotype.Service;

@Service
public class TasksProducer implements RemainingTasksResolver{
    @Override
    public boolean remainingTasksLeft(int processedCount) {
        return false;
    }
}
