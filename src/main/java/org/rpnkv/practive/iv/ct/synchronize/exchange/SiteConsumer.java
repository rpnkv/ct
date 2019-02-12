package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.core.Site;

@FunctionalInterface
public interface SiteConsumer {

    void consumeSite(Site site);
}
