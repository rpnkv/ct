package org.rpnkv.practive.iv.ct.exec;

@FunctionalInterface
public interface RemainingTasksResolver {

    boolean remainingTasksLeft(int processedCount);

}
