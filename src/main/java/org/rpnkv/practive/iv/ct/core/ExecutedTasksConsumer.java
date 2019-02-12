package org.rpnkv.practive.iv.ct.core;

import org.rpnkv.practive.iv.ct.exec.task.ExecutionTask;

@FunctionalInterface
public interface ExecutedTasksConsumer {

    void acceptExecutedTask(ExecutionTask executionTask);

}
