package org.rpnkv.practive.iv.ct.exec.task;

import org.rpnkv.practive.iv.ct.core.ExecutedTasksConsumer;
import org.rpnkv.practive.iv.ct.core.Site;

public class ExecutionTask implements Runnable {

    private final ExecutedTasksConsumer executedTasksConsumer;
    private final Site site;

    public ExecutionTask(ExecutedTasksConsumer executedTasksConsumer, Site site) {
        this.executedTasksConsumer = executedTasksConsumer;
        this.site = site;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(228);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        site.setContents(new byte[]{1,4,8,8});
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
