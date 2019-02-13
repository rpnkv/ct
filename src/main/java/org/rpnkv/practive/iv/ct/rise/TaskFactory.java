package org.rpnkv.practive.iv.ct.rise;

import org.rpnkv.practive.iv.ct.DomainInfo;
import org.rpnkv.practive.iv.ct.fetch.TaskExecutedCallback;
import org.rpnkv.practive.iv.ct.fetch.DomainInfoFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Wraps {@link DomainInfo} with {@link DomainFetchTaskAsync}. Keeps count of total created tasks count.
 */
@Service
public class TaskFactory implements Function<DomainInfo, DomainFetchTaskAsync> {

    private int totalCount;
    private final TaskExecutedCallback taskExecutedCallback;
    private final DomainInfoFetcher domainInfoFetcher;

    @Autowired
    public TaskFactory(TaskExecutedCallback taskExecutedCallback, DomainInfoFetcher domainInfoFetcher) {
        this.taskExecutedCallback = taskExecutedCallback;
        this.domainInfoFetcher = domainInfoFetcher;
    }

    @Override
    public DomainFetchTaskAsync apply(DomainInfo domainInfo) {
        totalCount++;
        return new DomainFetchTaskAsync(taskExecutedCallback, domainInfoFetcher, domainInfo);
    }

    public int getTotalCount() {
        return totalCount;
    }

}
