package org.rpnkv.practive.iv.ct.core;

import java.util.Objects;

/**
 *
 */
public class DomainInfo {

    private final String url;
    private byte[] contents;

    public DomainInfo(String url) {
        this.url = url;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainInfo domainInfo = (DomainInfo) o;
        return url.equals(domainInfo.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "DomainInfo{" +
                "url='" + url + '\'' +
                '}';
    }
}
