package com.demo.springbootlatest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompletableFutureEx {

	public void methodCompletableFuture(String apiInput) {

		try {
			CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
				return method1(apiInput);
			});

			CompletableFuture<String> result2 = CompletableFuture.supplyAsync(() -> {
				try {
					return method2(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result3 = CompletableFuture.supplyAsync(() -> {
				try {
					return method3(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture.runAsync(() -> {
				// Combine the results and perform any additional processing if needed
				combineMethods(result1.join(), result2.join(), result3.join());
				System.out.println("Final combined result: ");
			});

//			CompletableFuture<String> finalResult =CompletableFuture.allOf(result1, result2, result3)
//					.thenApplyAsync(ignored -> combineMethods(result1.join(), result2.join(), result3.join()));

		} catch (Exception e) {
			log.error("message", e);
		}

	}

	// Independent method 1
	public String method1(String input1) {
		System.out.println("started ... m1--->");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("m1--->");
		return "Result from method 1 " + input1;
	}

	// Independent method 2
	public String method2(String input2) throws InterruptedException {
		System.out.println("started ... m2--->");
		Thread.sleep(5000);
		System.out.println("m2--->");
		return "Result from method 2 " + input2;
	}

	// Independent method 3
	public String method3(String input3) throws InterruptedException {
		System.out.println("started ... m3--->");
		Thread.sleep(6000);
		System.out.println("m3--->");
		return "Result from method 3 " + input3;
	}

	// Independent method 4
	public String method4(String input) throws InterruptedException {
		System.out.println("started ... m4--->");
		Thread.sleep(6000);
		System.out.println("m4--->");
		return "Result from method 4 " + input;
	}

	// Independent method 5
	public String method5(String input) throws InterruptedException {
		System.out.println("started ... m5--->");
		Thread.sleep(6000);
		System.out.println("m5--->");
		return "Result from method 5 " + input;
	}

	// Dependent method combining results of method1, method2, and method3
	public void combineMethods(String result1, String result2, String result3) {
		System.out.println(result1 + " + " + result2 + " + " + result3);
//		return result1 + " + " + result2 + " + " + result3;
	}

//	public String combineMethods(String result1, String result2, String result3) {
//		System.out.println(result1 + " + " + result2 + " + " + result3);
//		return result1 + " + " + result2 + " + " + result3;
//	}

	/*-----------------------*/

	public Map<Long, String> testReturn(String apiInput) {
		Map<Long, String> result = new HashMap<>();
		try {
			CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
				log.info(">>Thread Name: " + Thread.currentThread());
				return method1(apiInput);
			});

			CompletableFuture<String> result2 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method2(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result3 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method3(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result4 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method4(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<String> result5 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method5(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(result1, result2, result3, result4, result5);
			allOfFuture.get();
			System.out.println(">>>>>>>>>: All Completed.....");
			result.put(1L, result1.get());
			result.put(2L, result2.get());
			result.put(3L, result3.get());
			result.put(4L, result4.get());
			result.put(5L, result5.get());

		} catch (Exception e) {
			log.error("Error in methodCompletableFuture," + e);
		}
		return result;
	}

	/** ====== With Exeguter Service */

	public Map<Long, String> testReturnExeguter(String apiInput) {
		Map<Long, String> result = new HashMap<>();
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
		try {

			CompletableFuture<String> result1 = CompletableFuture.supplyAsync(() -> {
				log.info(">>Thread Name: " + Thread.currentThread());
				return method1(apiInput);
			}, executor);

			CompletableFuture<String> result2 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method2(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, executor);

			CompletableFuture<String> result3 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method3(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, executor);

			CompletableFuture<String> result4 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method4(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, executor);

			CompletableFuture<String> result5 = CompletableFuture.supplyAsync(() -> {
				try {
					log.info(">>Thread Name: " + Thread.currentThread());
					return method5(apiInput);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, executor);

			CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(result1, result2, result3, result4, result5);
			allOfFuture.get();
			System.out.println(">>>>>>>>>: All Completed.....");
			result.put(1L, result1.get());
			result.put(2L, result2.get());
			result.put(3L, result3.get());
			result.put(4L, result4.get());
			result.put(5L, result5.get());

		} catch (Exception e) {
			log.error("Error in methodCompletableFuture," + e);
		} finally {
			executor.shutdown();
		}
		return result;
	}
}
