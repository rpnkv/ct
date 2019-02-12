package org.rpnkv.practive.iv.ct.exec;

import org.apache.commons.lang3.NotImplementedException;
import org.rpnkv.practive.iv.ct.core.Site;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskFactory implements Function<Site, ExecutionTask> {
    @Override
    public ExecutionTask apply(Site site) {
        throw new NotImplementedException("");
    }

    public int getTotalCount() {
        throw new NotImplementedException("");
    }
}
