package org.rpnkv.practive.iv.ct;

import org.rpnkv.practive.iv.ct.io.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ComponentScan
@Configuration
public class Main implements ApplicationListener<ContextStartedEvent> {

    @Bean
    ExecutorService executorService(){
        return Executors.newCachedThreadPool();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private FileReader fileReader;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        ctx.start();
    }

    private void start(){
        fileReader.start();
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        fileReader.start();
    }
}
