package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.persist.PersistingQueueConsumer;
import org.rpnkv.practive.iv.ct.rise.DomainInfoFetchTaskProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main{

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeansConfiguration.class);
        ctx.start();

        Runnable persistingSiteConsumer = ctx.getBean(PersistingQueueConsumer.class),
                taskProducer = ctx.getBean(DomainInfoFetchTaskProducer.class);

        Thread persistingThread = new Thread(persistingSiteConsumer, "persisting"),
                taskProducingThread = new Thread(taskProducer, "task producing");

        persistingThread.start();
        taskProducingThread.start();

        persistingThread.join();
        taskProducingThread.join();

        ctx.close();

        logger.info("Job has been finished");
        System.exit(0);
    }

}
