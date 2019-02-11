package org.rpnkv.practive.iv.ct.io;

import org.rpnkv.practive.iv.ct.get.Site;
import org.rpnkv.practive.iv.ct.synchronize.count.TaskCountChecker;
import org.rpnkv.practive.iv.ct.synchronize.exchange.SiteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class SitePrinter implements ApplicationListener<ContextStartedEvent> {

    private final TaskCountChecker taskCountChecker;

    private final SiteProducer siteProducer;
    private final FileWriter fileWriter;

    @Autowired
    public SitePrinter(TaskCountChecker taskCountChecker, SiteProducer siteProducer, FileWriter fileWriter) {
        this.taskCountChecker = taskCountChecker;
        this.siteProducer = siteProducer;
        this.fileWriter = fileWriter;
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        int count = 0;
        while (!taskCountChecker.incrementAndCheck()){
            Site site = siteProducer.nextSite();
            fileWriter.writeSite(site);
            count++;
        }

        System.out.println("consumed " + count + " sites");
    }
}
