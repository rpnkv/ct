package org.rpnkv.practive.iv.ct.exec.task;

import org.rpnkv.practive.iv.ct.core.ExecutedTasksConsumer;
import org.rpnkv.practive.iv.ct.core.Site;

import java.util.function.Consumer;

public class ExecutionTask implements Runnable {

    private final ExecutedTasksConsumer executedTasksConsumer;
    private final Consumer<Site> requestExecutor;
    private final Site site;

    public ExecutionTask(ExecutedTasksConsumer executedTasksConsumer, Consumer<Site> requestExecutor, Site site) {
        this.executedTasksConsumer = executedTasksConsumer;
        this.requestExecutor = requestExecutor;
        this.site = site;
    }

    @Override
    public void run() {
        requestExecutor.accept(site);
        executedTasksConsumer.acceptExecutedTask(this);
    }

    public Site getSite() {
        return site;
    }

    @Override
    public String toString() {
        return "ExecutionTask{" +
                "site=" + site +
                '}';
    }
}
