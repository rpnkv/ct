package org.rpnkv.practive.iv.ct.exec;

import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class TaskExecutionQueue implements Consumer<ExecutionTask> {

    @Override
    public void accept(ExecutionTask executionTask) {

    }
}
