package org.rpnkv.practive.iv.ct.core;

import java.util.Objects;

public class Site {

    private final String url;
    private byte[] contents;

    public Site(String url) {
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
        Site site = (Site) o;
        return url.equals(site.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "Site{" +
                "url='" + url + '\'' +
                '}';
    }
}
