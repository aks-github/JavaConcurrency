package example.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorFramework {

	public static void main(String[] args) {

		// ExecutorCompletionService executor = Executors.newFixedThreadPool(2);
		ExecutorService executor = Executors.newFixedThreadPool(2);

		List<Future<String>> futureList = new ArrayList<Future<String>>();
		for (int i = 0; i < 10; i++) {
			TaskExecutor task = new TaskExecutor("Task:-" + i);
			futureList.add(executor.submit(task));
		}

		for (Future<String> future : futureList)
			try {
				if (future.get() != null) {
					System.out.println(future.get() + " Completed");
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		executor.shutdown();
	}
}

class TaskExecutor implements Callable<String> {
	private String name;

	public TaskExecutor(String name) {
		this.name = name;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executing " + name);
		Thread.sleep(2000);
		return name;
	}
}
