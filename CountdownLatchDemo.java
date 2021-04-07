package com.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchDemo {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(3);
		Thread cacheService = new Thread(new Service("CacheService", 1000, latch));
		Thread alertService = new Thread(new Service("alertService", 1000, latch));
		Thread validationService = new Thread(new Service("validationService", 1000, latch));

		cacheService.start();
		alertService.start();
		validationService.start();

		try {
			latch.await();
			System.out.println("All services are up. Application is starting now");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}

class Service implements Runnable {

	private String name;
	private int timeToStart;
	private CountDownLatch latch;

	public Service(String name, int timeToStart, CountDownLatch latch) {
		super();
		this.name = name;
		this.timeToStart = timeToStart;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(timeToStart);
		} catch (InterruptedException ex) {

			System.out.println(Service.class.getName() + ex);

		}
		System.out.println(name + " is Up");
		latch.countDown();

	}

}
