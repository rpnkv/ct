package org.rpnkv.practive.iv.ct.get;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PullTask implements Runnable {

    private final Supplier<byte[]> requestExecutor;
    private final Consumer<byte[]> resultConsumer;


    PullTask(Supplier<byte[]> requestExecutor, Consumer<byte[]> resultConsumer) {
        this.requestExecutor = requestExecutor;
        this.resultConsumer = resultConsumer;
    }

    @Override
    public void run() {
        byte[] result = requestExecutor.get();
        resultConsumer.accept(result);
    }
}
