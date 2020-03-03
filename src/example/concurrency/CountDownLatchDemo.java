package example.concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
	public static void main(String args[]) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(4);

		WorkerThread firstThead = new WorkerThread(1000, latch, "WORKER-THREAD-1");
		WorkerThread secondThead = new WorkerThread(2000, latch, "WORKER-THREAD-2");
		WorkerThread thirdThead = new WorkerThread(3000, latch, "WORKER-THREAD-3");
		WorkerThread fourthThead = new WorkerThread(4000, latch, "WORKER-THREAD-4");
		firstThead.start();
		secondThead.start();
		thirdThead.start();
		fourthThead.start();

		latch.await();

		System.out.println(Thread.currentThread().getName() + " has finished");
	}
}

class WorkerThread extends Thread {
	private int delay;
	private CountDownLatch latch;

	public WorkerThread(int delay, CountDownLatch latch, String name) {
		super(name);
		this.delay = delay;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delay);
			latch.countDown();
			System.out.println(Thread.currentThread().getName() + " finished working");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
