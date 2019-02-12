package org.rpnkv.practive.iv.ct.exec;

import org.rpnkv.practive.iv.ct.core.Site;
import org.rpnkv.practive.iv.ct.exec.task.TaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasksProducer implements RemainingTasksResolver, Runnable{

    private final DomainReader domainReader;
    private final TaskExecutionQueue executionQueue;
    private final TaskFactory taskFactory;

    private volatile boolean isCountFinal = false;//TODO provide volatile explanation
    private int submittedTasksCount = 0;

    @Autowired
    public TasksProducer(DomainReader domainReader, TaskExecutionQueue executionQueue, TaskFactory taskFactory) {
        this.domainReader = domainReader;
        this.executionQueue = executionQueue;
        this.taskFactory = taskFactory;
    }

    @Override
    public boolean remainingTasksLeft(int processedCount) {
        if(!isCountFinal){
            return false;
        }else {
            return submittedTasksCount > processedCount;
        }
    }

    @Override
    public void run() {
        domainReader.getDomains()
                .map(Site::new)
                .map(taskFactory)
                .forEach(executionQueue);

        submittedTasksCount = taskFactory.getTotalCount();
        isCountFinal = true;
    }
}
