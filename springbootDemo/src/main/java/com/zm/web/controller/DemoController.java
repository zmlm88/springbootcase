package com.zm.web.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.runner.Result;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.zm.web.configuration.redis.ThreadExec;
import com.zm.web.configuration.threadpool.ThreadPoolConfig;
import com.zm.web.db.model.WorkTUserInfo;
import com.zm.web.service.TestService;
import com.zm.web.vo.ResultVo;

/**
 * 
 * @author zhumin
 *
 */
@RestController
@RequestMapping("/redis")
public class DemoController {

	Logger log = LoggerFactory.getLogger(DemoController.class);
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	RedissonClient redissonClient;

	@Autowired
	@Qualifier(value = "taskExecutor")
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	TestService testService;

	@RequestMapping(value = "/redisOper1", method = RequestMethod.GET)
	public void redisOper1() {
		log.info("===232323===");
		ValueOperations<String, String> value = stringRedisTemplate.opsForValue();
		value.set("zhumin", "zhumin1212");
		log.info(value.get("zhumin"));
		log.info("====Over===");
	}

	@RequestMapping(value = "/redisOper2")
	public @ResponseBody String redisOper2() throws Exception {

		RLock lock = redissonClient.getLock("zhumin1");
		lock.lock();
		try {
			log.info("====redisOper2=== 正在處理");
			Thread.sleep(100000L);
			System.out.println("hagogrgr");
		} finally {
			lock.unlock();
		}
		return "redisOper2===处理成功";
	}

	@RequestMapping(value = "/redisOper3")
	public @ResponseBody String redisOper3() {

		RLock lock = redissonClient.getLock("zhumin1");
		lock.lock();
		try {
			System.out.println("redisOper3===處理完成");
		} finally {
			lock.unlock();
		}

		return "redisOper3===处理成功";
	}

	@RequestMapping(value = "/redisOper4")
	public @ResponseBody String redisOper4() throws Exception {
		Thread.sleep(10L);
		return "redisOper4===处理成功";
	}

	@RequestMapping(value = "/redisOper5")
	public @ResponseBody String redisOper5() throws Exception {
		for (int i = 0; i <= 500; i++) {
			Future<String> resultFuture = taskExecutor.submit(new ThreadExec(redissonClient, i + "", i + ""));
			ThreadPoolConfig.listStore.add(resultFuture);
		}
		return "redisOper4===处理成功";
	}

	/**
	 * 异步处理线程SpringMvc
	 */
	@RequestMapping(value = "/asyRequest")
	public WebAsyncTask<Result> asyRequest() {

		Callable<ResultVo> callable = new Callable<ResultVo>() {
			public ResultVo call() throws Exception {
				System.out.println("请求处理中");
				Thread.sleep(30000); // 假设是一些长时间任务
				ResultVo mav = new ResultVo(true, "longtimetask");
				System.out.println("执行成功 thread id is : " + Thread.currentThread().getId());
				return mav;
			}
		};

		Callable<ResultVo> callableTimeout = new Callable<ResultVo>() {
			public ResultVo call() throws Exception {
				ResultVo mav = new ResultVo(true, "超時處理");
				return mav;
			}
		};

		WebAsyncTask webAsyncTask = new WebAsyncTask(3000, callable);
		webAsyncTask.onTimeout(callableTimeout);
		return webAsyncTask;
	}

	/**
	 * mybatis 测试
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectByPrimaryKey")
	@ResponseBody
	public String selectByPrimaryKey() {
		WorkTUserInfo workTUserInfo = testService.selectByPrimaryKey("1");
		return "ok";
	}

	/**
	 * mybatis 测试
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findPage")
	@ResponseBody
	public String findPage() {
		testService.findPage("1");
		testService.selectByPrimaryKey("1");
		return "ok";
	}

}
