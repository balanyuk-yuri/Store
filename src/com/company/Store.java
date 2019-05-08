package com.company;

public class Store {
    private int productCount;

    Store(int productCount){
        this.productCount = productCount;
    }

    int getProductCount(){
        return productCount;
    }

    synchronized int realizeProduct(int count){
        if (count > productCount){
            int realized = productCount;
            productCount = 0;
            return realized;
        }

        productCount-=count;
        return count;
    }
}
