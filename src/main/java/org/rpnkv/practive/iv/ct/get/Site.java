package org.rpnkv.practive.iv.ct.get;

public class Site {

    private final String url;
    private byte[] contents;

    public Site(String url) {
        this.url = url;
    }

    void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getContents() {
        return contents;
    }
}
