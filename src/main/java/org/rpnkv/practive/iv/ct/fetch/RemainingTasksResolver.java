package org.rpnkv.practive.iv.ct.fetch;

/**
 * Resolves, if there are more task to process, comparing to already processed.
 */
@FunctionalInterface
public interface RemainingTasksResolver {

    boolean remainingTasksLeft(int alreadyProcessedCount);

}
