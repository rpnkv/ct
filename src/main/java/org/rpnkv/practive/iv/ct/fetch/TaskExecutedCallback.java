package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.rise.DomainFetchTaskAsync;

@FunctionalInterface
public interface TaskExecutedCallback {

    void accept(DomainFetchTaskAsync domainFetchTaskAsync);

}
