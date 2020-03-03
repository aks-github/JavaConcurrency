package example.concurrency;

import java.util.LinkedList;

public class ProducerConsumer {

	public static void main(String[] args) {
		LinkedList<Integer> sharedList = new LinkedList<Integer>();
		int capacity = 2;
		Thread cThread = new Thread(new Consumer(sharedList, capacity));
		Thread pThread = new Thread(new Producer(sharedList, capacity));
		cThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pThread.start();
	}

}

class Producer implements Runnable {

	LinkedList<Integer> sharedList;
	int capacity;

	public Producer(LinkedList<Integer> sharedList, int capacity) {
		this.sharedList = sharedList;
		this.capacity = capacity;
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
		synchronized (sharedList) {
			while (sharedList.size() == capacity) {
				System.out.println("Producer waiting");
				sharedList.wait();
			}

			System.out.println("Producer produced-" + value);
			sharedList.add(value);
			sharedList.notifyAll();
			Thread.sleep(2000);
		}
	}

}

class Consumer implements Runnable {

	LinkedList<Integer> sharedList;
	int capacity;

	public Consumer(LinkedList<Integer> sharedList, int capacity) {
		this.sharedList = sharedList;
		this.capacity = capacity;
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
		synchronized (sharedList) {
			while (sharedList.size() == 0) {
				System.out.println("Consumer waiting");
				sharedList.wait();
			}
			System.out.println("Consumer consumed-" + sharedList.removeFirst());
			sharedList.notifyAll();
		}
	}

}