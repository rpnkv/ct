package org.rpnkv.practive.iv.ct.get;

@FunctionalInterface
public interface MetadataLoader {
    Site loadMetadata(Site site);
}
