package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.get.Site;

public interface SiteConsumer {

    void consumeSite(Site site);

    Site nextSite();
}
