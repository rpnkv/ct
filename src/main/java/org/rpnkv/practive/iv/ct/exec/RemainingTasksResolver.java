package org.rpnkv.practive.iv.ct.exec;

/**
 * Resolves, if there are more task to process, comparing to already processed.
 */
@FunctionalInterface
public interface RemainingTasksResolver {

    boolean remainingTasksLeft(int alreadyProcessedCount);

}
