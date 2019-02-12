package org.rpnkv.practive.iv.ct.synchronize.count;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TaskCounterImpl implements TaskCountKeeper {

    private AtomicInteger totalTasks, processedTasks;

    public TaskCounterImpl() {
        totalTasks = new AtomicInteger(-1);
        processedTasks = new AtomicInteger(0);
    }


    @Override
    public boolean incrementAndCheck() {
        processedTasks.incrementAndGet();
        //TODO check if can produce deadlock
        if (totalTasks.get() == -1) {
            return false;
        } else {
            return totalTasks.get() == processedTasks.get();
        }
    }

    @Override
    public void setTaskCount(int count) {
        totalTasks.set(count);
    }
}
