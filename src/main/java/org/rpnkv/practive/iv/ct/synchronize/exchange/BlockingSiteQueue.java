package org.rpnkv.practive.iv.ct.synchronize.exchange;

import org.rpnkv.practive.iv.ct.get.Site;
import org.springframework.stereotype.Component;

@Component
class BlockingSiteQueue implements SiteQueue {
    @Override
    public void consumeSite(Site site) {

    }

    @Override
    public Site nextSite() {
        return null;
    }
}
