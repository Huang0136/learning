package com.huang.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huang.service.UserService;

/**
 * Index控制器
 * @author huanggh
 * @since 2016-04-18 19:01
 */
@Controller
public class IndexController {
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private UserService userServiceImpl;
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping(value={"/","","/index"}, method = RequestMethod.GET)
	public String index() {
		
		logger.info("indexCnotroller...");
		
		return "index";
	}
}
