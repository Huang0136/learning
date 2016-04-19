package com.huang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户控制器
 * @author huanggh
 * @since 2016-04-19 18:37
 */
@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(String username, String password,
			HttpServletRequest req, HttpServletResponse resp) {
		logger.info("login Controller...");
		
		return "";
	}
	
	public String logout(HttpServletRequest req) {
		
		return "";
	}
	
}
