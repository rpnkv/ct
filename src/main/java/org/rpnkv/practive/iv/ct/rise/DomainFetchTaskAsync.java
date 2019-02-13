package org.rpnkv.practive.iv.ct.rise;

import org.rpnkv.practive.iv.ct.DomainInfo;
import org.rpnkv.practive.iv.ct.fetch.TaskExecutedCallback;

import java.util.function.Consumer;

/**
 * Represents async task fetching specific domain metadata.
 * After metadata is fetched - callback is invoked.
 */
public class DomainFetchTaskAsync implements Runnable {


    private final TaskExecutedCallback taskExecutedCallback;

    /**
     * Fills {@link #domainInfo} with metadata.
     */
    private final Consumer<DomainInfo> requestExecutor;

    /**
     * Domain being processed
     */
    private final DomainInfo domainInfo;

    public DomainFetchTaskAsync(TaskExecutedCallback taskExecutedCallback, Consumer<DomainInfo> requestExecutor, DomainInfo domainInfo) {
        this.taskExecutedCallback = taskExecutedCallback;
        this.requestExecutor = requestExecutor;
        this.domainInfo = domainInfo;
    }

    @Override
    public void run() {
        requestExecutor.accept(domainInfo);
        taskExecutedCallback.accept(this);
    }

    public DomainInfo getDomainInfo() {
        return domainInfo;
    }

    @Override
    public String toString() {
        return "DomainFetchTaskAsync{" +
                "domainInfo=" + domainInfo +
                '}';
    }
}
