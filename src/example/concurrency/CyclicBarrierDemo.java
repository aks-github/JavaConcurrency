package example.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	public static void main(String args[]) throws InterruptedException, BrokenBarrierException {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

		Task first = new Task(1000, cyclicBarrier, "TASK-THREAD-1");
		Task second = new Task(2000, cyclicBarrier, "TASK-THREAD-2");
		Task third = new Task(3000, cyclicBarrier, "TASK-THREAD-3");
		first.start();
		second.start();
		third.start();

		System.out.println(Thread.currentThread().getName() + " Waiting");
		cyclicBarrier.await();

		System.out.println(Thread.currentThread().getName() + " has finished");
	}
}

class Task extends Thread {
	private int delay;
	private CyclicBarrier cyclicBarrier;

	public Task(int delay, CyclicBarrier cyclicBarrier, String name) {
		super(name);
		this.delay = delay;
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delay);
			System.out.println(Thread.currentThread().getName() + " Waiting");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + " finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
