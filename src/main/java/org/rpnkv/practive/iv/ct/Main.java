package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.exec.TasksProducer;
import org.rpnkv.practive.iv.ct.presist.PersistingSiteConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Main{

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ctx.start();

        Runnable persistingSiteConsumer = ctx.getBean(PersistingSiteConsumer.class),
                taskProducer = ctx.getBean(TasksProducer.class);

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
