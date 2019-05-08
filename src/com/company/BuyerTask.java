package com.company;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class BuyerTask implements Callable<Void> {
    Buyer buyer;
    Store store;
    CyclicBarrier barrier;
    Random gen = new Random();

    BuyerTask(Buyer buyer, Store store, CyclicBarrier barrier){
        this.buyer = buyer;
        this.store = store;
        this.barrier = barrier;
    }

    @Override
    public Void call() throws Exception {
        while (store.getProductCount() > 0){
            barrier.await(5, TimeUnit.SECONDS);
            int neededCount = 1 + gen.nextInt(10);
            int realized = buyer.buy(store, neededCount);
            if (realized < neededCount){
                barrier.reset();
            }
        }
        return null;
    }
}
