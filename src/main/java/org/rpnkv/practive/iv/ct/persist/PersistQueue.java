package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.core.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class PersistQueue {

    private static final Logger logger = LoggerFactory.getLogger(PersistQueue.class);

    private final Queue<Site> persistQueue = new LinkedList<>();
    private final Object lock;

    @Value("${queue.persist.length}")
    private int queueLength;

    @Autowired
    public PersistQueue(Object lock) {
        this.lock = lock;
    }

    public void submit(Site site){
        synchronized (lock){
            while (persistQueue.size() == queueLength){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    logger.error("Failed waiting for queue release");
                    throw new RuntimeException(e);
                }
            }

            persistQueue.add(site);
            logger.debug("submitted site {}", site);
        }
    }

    Site next(){
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
