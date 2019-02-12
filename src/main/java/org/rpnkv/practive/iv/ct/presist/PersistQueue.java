package org.rpnkv.practive.iv.ct.presist;

import org.rpnkv.practive.iv.ct.core.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistQueue {

    private final Object lock;

    @Autowired
    public PersistQueue(Object lock) {
        this.lock = lock;
    }

    public void submit(Site site){

    }

    Site next(){
        return null;
    }

}
