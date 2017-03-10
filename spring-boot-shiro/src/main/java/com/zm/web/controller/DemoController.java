package com.zm.web.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.zm.web.configuration.mybatis.Page;
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
	@Qualifier(value = "taskExecutor")
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	TestService testService;
	
	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}


 
 
	@RequestMapping(value = "/redisOper4")
	public @ResponseBody String redisOper4() throws Exception {
		Thread.sleep(10L);
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
	@RequestMapping(value = "/add")
	@ResponseBody
	public int  add() {
		return testService.add();
	}
	
	/**
	 * mybatis 测试
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findPageTempCase")
	@ResponseBody
	public Page<WorkTUserInfo>  findPageTempCase() {
		return testService.findPageTempCase("1");
	}
	
	@RequestMapping(value = "/selectMapByOper")
	@ResponseBody
	public List<Map<String, Object>> selectMapByOper(){
		return testService.selectMapByOper();
	}
}
