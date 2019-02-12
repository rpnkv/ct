package org.rpnkv.practive.iv.ct.exec.task;

import org.apache.commons.lang3.NotImplementedException;
import org.rpnkv.practive.iv.ct.core.Site;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskFactory implements Function<Site, ExecutionTask> {

    private int totalCount;

    @Override
    public ExecutionTask apply(Site site) {
        totalCount++;
        return new ExecutionTask();
    }

    public int getTotalCount() {
        return totalCount;
    }
}
