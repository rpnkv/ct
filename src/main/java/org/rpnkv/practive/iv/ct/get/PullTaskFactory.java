package org.rpnkv.practive.iv.ct.get;

import org.rpnkv.practive.iv.ct.synchronize.exchange.SiteConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PullTaskFactory {

    private final SiteConsumer consumer;

    @Autowired
    public PullTaskFactory(SiteConsumer consumer) {
        this.consumer = consumer;
    }

    public PullTask create(Site site) {
        return new PullTask(consumer, RequestExecutorImpl::execute, site);
    }
}
