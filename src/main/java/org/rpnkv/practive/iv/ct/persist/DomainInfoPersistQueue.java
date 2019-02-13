package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.DomainInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements buffer for filled {@link DomainInfo} instances, waiting for persist.
 */
@Service
public class DomainInfoPersistQueue {

    private static final Logger logger = LoggerFactory.getLogger(DomainInfoPersistQueue.class);

    private final Queue<DomainInfo> persistQueue = new LinkedList<>();
    private final Object lock;

    @Value("${queue.persist.length}")
    private int queueLength;

    @Autowired
    public DomainInfoPersistQueue(Object lock) {
        this.lock = lock;
    }

    public void submit(DomainInfo domainInfo){
        synchronized (lock){
            while (persistQueue.size() == queueLength){
                try {
                    logger.error("Persist queue overflow!");
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting for queue release");
                    throw new RuntimeException(e);
                }
            }

            persistQueue.add(domainInfo);
            logger.debug("submitted domainInfo {}", domainInfo);
        }
    }

    DomainInfo next(){
        synchronized (lock){
            while (persistQueue.isEmpty()){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting for queue fill");
                }
            }

            return persistQueue.poll();
        }
    }

}
