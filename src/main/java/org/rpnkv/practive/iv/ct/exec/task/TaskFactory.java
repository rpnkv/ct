package org.rpnkv.practive.iv.ct.exec.task;

import org.rpnkv.practive.iv.ct.core.ExecutedTasksConsumer;
import org.rpnkv.practive.iv.ct.core.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskFactory implements Function<Site, ExecutionTask> {

    private int totalCount;
    private final ExecutedTasksConsumer executedTasksConsumer;
    private final RequestExecutor requestExecutor;

    @Autowired
    public TaskFactory(ExecutedTasksConsumer executedTasksConsumer, RequestExecutor requestExecutor) {
        this.executedTasksConsumer = executedTasksConsumer;
        this.requestExecutor = requestExecutor;
    }

    @Override
    public ExecutionTask apply(Site site) {
        totalCount++;
        return new ExecutionTask(executedTasksConsumer, requestExecutor, site);
    }

    public int getTotalCount() {
        return totalCount;
    }
}
