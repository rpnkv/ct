package org.rpnkv.practive.iv.ct.get;

class RequestExecutorImpl {

    static Site execute(Site site) {
        site.setContents(new byte[]{1, 4, 8, 8});
        return site;
    }
}
