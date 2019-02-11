package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.get.Site;

@FunctionalInterface
public interface SiteConsumer {

    void consumeSite(Site site);
}
