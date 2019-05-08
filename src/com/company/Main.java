package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	if (args.length != 1){
	    System.out.println("В аргументах укажите количество покупателей");
	    return;
    }

	int buyerCount = 0;
	try {
	    buyerCount = Integer.parseInt(args[0]);
	    if (buyerCount < 1){
	        throw new Exception();
        }
    }
	catch (Exception e){
        System.out.println("Количество покупателей должно быть целым положительным числом");
        return;
    }

	int productCount = 1000;
	Store store = new Store(productCount);
	List<Buyer> buyers = new ArrayList<>();
	List<Callable<Void>> buyerTasks = new ArrayList<>();

	ExecutorService pool = Executors.newFixedThreadPool(buyerCount);
	CyclicBarrier barrier = new CyclicBarrier(buyerCount);
	for (int i = 0; i < buyerCount; ++i){
		Buyer buyer = new Buyer();
		buyers.add(buyer);
		buyerTasks.add(new BuyerTask(buyer, store, barrier));
	}

	pool.invokeAll(buyerTasks);
	pool.shutdown();

	int realized = 0;
	for (int i = 0; i < buyerCount; ++i){
		System.out.printf("Покупатель #%d Количество покупок: %d Количество товара: %d%n",
				i + 1, buyers.get(i).getCurPurchaseCount(), buyers.get(i).getCurProductCount());
		realized+= buyers.get(i).getCurProductCount();
	}

	if (realized != productCount){
		System.out.printf("Количество проданного товара %d не равно исходному количеству товара %d%n",
				realized, productCount);
	}
	else {
		System.out.println("Весь товар продан");
	}

    }
}
