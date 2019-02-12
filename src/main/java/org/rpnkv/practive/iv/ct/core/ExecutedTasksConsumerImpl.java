package org.rpnkv.practive.iv.ct.core;

import org.rpnkv.practive.iv.ct.exec.TaskExecutionQueue;
import org.rpnkv.practive.iv.ct.exec.task.ExecutionTask;
import org.rpnkv.practive.iv.ct.presist.PersistQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutedTasksConsumerImpl implements ExecutedTasksConsumer {

    private final PersistQueue persistQueue;
    private final TaskExecutionQueue executionQueue;

    @Autowired
    public ExecutedTasksConsumerImpl(PersistQueue persistQueue, TaskExecutionQueue executionQueue) {
        this.persistQueue = persistQueue;
        this.executionQueue = executionQueue;
    }

    @Override
    public void acceptExecutedTask(ExecutionTask executionTask) {
        persistQueue.submit(executionTask.getSite());
        executionQueue.remove(executionTask);
    }
}
