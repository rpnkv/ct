package org.rpnkv.practive.iv.ct.get;

import org.rpnkv.practive.iv.ct.synchronize.exchange.SiteConsumer;

public class PullTask implements Runnable {

    private final SiteConsumer siteConsumer;
    private final Site site;
    private final MetadataLoader metadataLoader;

    public PullTask(SiteConsumer siteConsumer, MetadataLoader metadataLoader, Site site) {
        this.metadataLoader = metadataLoader;
        this.siteConsumer = siteConsumer;
        this.site = site;
    }

    @Override
    public void run() {
        Site result = metadataLoader.loadMetadata(site);
        siteConsumer.consumeSite(result);
    }
}
