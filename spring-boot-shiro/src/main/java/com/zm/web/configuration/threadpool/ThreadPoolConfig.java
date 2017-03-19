package com.zm.web.configuration.threadpool;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池设置
 * 
 * @author zmlm88@126.com
 *
 */
@Configuration
@EnableAsync
@ConfigurationProperties
public class ThreadPoolConfig {

	public static List<Future<?>> listStore = new CopyOnWriteArrayList<Future<?>>();

	@Value("${thread.pool.corePoolSize}")
	private int corePoolSize;
	@Value("${thread.pool.maxPoolSize}")
	private int maxPoolSize;
	@Value("${thread.pool.queueCapacity}")
	private int queueCapacity;

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor myAsync() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("MyExecutor-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

}
