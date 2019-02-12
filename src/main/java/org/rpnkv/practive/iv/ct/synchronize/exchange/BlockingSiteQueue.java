package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.get.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
class BlockingSiteQueue implements SiteQueue {

    private static final Logger logger = LoggerFactory.getLogger(BlockingSiteQueue.class);
    private BlockingQueue<Site> queue = new ArrayBlockingQueue<>(20);

    public BlockingSiteQueue() {
    }

    @Override
    public void consumeSite(Site site) {
        try {
            queue.put(site);
        } catch (InterruptedException e) {
            logger.error("Failed adding site " + site.getUrl() + " to queue", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Site nextSite() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            logger.error("Failed pulling site from queue", e);
            throw new RuntimeException(e);
        }
    }
}
