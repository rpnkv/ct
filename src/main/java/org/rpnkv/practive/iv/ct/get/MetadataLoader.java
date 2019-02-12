package org.rpnkv.practive.iv.ct.get;

import org.rpnkv.practive.iv.ct.core.Site;

@FunctionalInterface
public interface MetadataLoader {
    Site loadMetadata(Site site);
}
