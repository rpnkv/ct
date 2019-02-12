package org.rpnkv.practive.iv.ct.io;

import org.rpnkv.practive.iv.ct.get.Site;
import org.rpnkv.practive.iv.ct.synchronize.count.TaskCountChecker;
import org.rpnkv.practive.iv.ct.synchronize.exchange.SiteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class SitePrinter implements ApplicationListener<ContextStartedEvent> {

    private final TaskCountChecker taskCountChecker;

    private final SiteProducer siteProducer;
    private final FileWriter fileWriter;
    private final ExecutorService executorService;

    @Autowired
    public SitePrinter(TaskCountChecker taskCountChecker, SiteProducer siteProducer, FileWriter fileWriter,
                       ExecutorService executorService) {
        this.taskCountChecker = taskCountChecker;
        this.siteProducer = siteProducer;
        this.fileWriter = fileWriter;
        this.executorService = executorService;
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        new Thread(this::runWriter, "Writer thread").start();
    }

    void runWriter(){
        while (!taskCountChecker.incrementAndCheck()){
            Site site = siteProducer.nextSite();
            fileWriter.writeSite(site);
        }
        executorService.shutdown();

        fileWriter.close();
    }
}
