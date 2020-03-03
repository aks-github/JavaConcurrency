package example.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {

	public static void main(String[] args) {
		BlockingQueue<Integer> sharedList = new LinkedBlockingQueue<Integer>(2);
		Thread cThread = new Thread(new ConsumerDemo(sharedList));
		Thread pThread = new Thread(new ProducerDemo(sharedList));
		cThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pThread.start();
	}

}

class ProducerDemo implements Runnable {

	BlockingQueue<Integer> sharedList;

	public ProducerDemo(BlockingQueue<Integer> sharedList) {
		this.sharedList = sharedList;
	}

	@Override
	public void run() {
		int value = 0;
		while (true) {
			try {
				produce(value++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void produce(int value) throws InterruptedException {
		System.out.println("Producer produced-" + value);
		sharedList.put(value);
		Thread.sleep(500);
	}
}

class ConsumerDemo implements Runnable {

	BlockingQueue<Integer> sharedList;

	public ConsumerDemo(BlockingQueue<Integer> sharedList) {
		this.sharedList = sharedList;
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void consume() throws InterruptedException {
		System.out.println("Consumer consumed-" + sharedList.take());
		Thread.sleep(100);
	}
}
