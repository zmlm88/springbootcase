package com.zm.web.configuration.redis;

import java.util.concurrent.Callable;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 线程池callable
 * @author zmlm88@126.com
 *
 */
public class ThreadExec implements Callable<String> {
	RedissonClient redissonClient;
	String param;
	String id;

	public ThreadExec(RedissonClient redissonClient, String param, String id) {
		this.redissonClient = redissonClient;
		this.param = param;
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		RLock lock = redissonClient.getLock("zhumin1");
		lock.lock();
		Thread.sleep(1000L);
		System.out.println("处理信息" + param);
		lock.unlock();
		return "处理完成"+id;
	}

}
