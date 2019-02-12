package org.rpnkv.practive.iv.ct.exec;

import org.rpnkv.practive.iv.ct.core.Site;
import org.rpnkv.practive.iv.ct.exec.task.TaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TasksProducer implements RemainingTasksResolver, Runnable{

    private final DomainsFileReader domainsFileReader;

    @Qualifier("executionQueue")
    private final TaskExecutionQueue executionQueue;
    private final TaskFactory taskFactory;

    private volatile boolean isCountFinal = false;//TODO provide volatile explanation
    private int submittedTasksCount = 0;

    @Autowired
    public TasksProducer(DomainsFileReader domainsFileReader, TaskExecutionQueue executionQueue, TaskFactory taskFactory) {
        this.domainsFileReader = domainsFileReader;
        this.executionQueue = executionQueue;
        this.taskFactory = taskFactory;
    }

    @Override
    public boolean remainingTasksLeft(int alreadyProcessedCount) {
        if(!isCountFinal){
            return true;
        }else {
            return submittedTasksCount != alreadyProcessedCount;
        }
    }

    @Override
    public void run() {
        domainsFileReader.getDomains()
                .map(this::applyProtocol)
                .map(Site::new)
                .map(taskFactory)
                .forEach(executionQueue);

        submittedTasksCount = taskFactory.getTotalCount();
        isCountFinal = true;
    }

    private String applyProtocol(String s) {
        return "http://".concat(s);
    }
}
