package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.exec.TasksProducer;
import org.rpnkv.practive.iv.ct.presist.PersistingSiteConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan
@Configuration
public class Main{

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

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
    }

}
