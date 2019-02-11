package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.get.Site;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Component
class BlockingSiteQueue implements SiteQueue {

    private AtomicInteger consumedCount, requestedCount;
    private BlockingQueue<Site> queue = new ArrayBlockingQueue<>(20);

    public BlockingSiteQueue() {
        consumedCount = new AtomicInteger();
        requestedCount = new AtomicInteger();
    }

    @Override
    public void consumeSite(Site site) {
        consumedCount.incrementAndGet();
        //System.out.println("consumed " + consumedCount.incrementAndGet() + " sites");
    }

    @Override
    public Site nextSite() {
        System.out.println("requested " + requestedCount.incrementAndGet() + " sites");
        return null;
    }
}
