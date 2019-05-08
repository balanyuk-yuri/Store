package com.company;

public class Buyer {
    private int curProductCount = 0;
    private int curPurchaseCount = 0;

    int buy(Store store, int count){
        int realized = store.realizeProduct(count);
        if (realized > 0){
            ++curPurchaseCount;
            curProductCount += realized;
        }

        return realized;
    }

    public int getCurProductCount() {
        return curProductCount;
    }

    public int getCurPurchaseCount() {
        return curPurchaseCount;
    }
}
