package com.huang.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huang.annotation.Content;
import com.huang.bean.User;
import com.huang.constants.Constants;
import com.huang.service.UserService;

/**
 * 用户以及登录控制器
 * 
 * @author huanggh
 * @since 2016-04-19 18:37
 */
@Controller
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userServiceImpl;
	
	@Content(value = "访问登录页面")
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest req) {
		System.out.println(Constants.SIMPLE_DATE_FORMAT.format(new Date()));;
		
		User user = this.userServiceImpl.findUserByUsername("hgh");
		
		System.out.println(user == null);
		
		if(user!=null) {
			System.out.println(user.getUsername()+" ,"+user.getAddress());
		}
		
		return "common/login";
	}

	@Content(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(String username, String password,
			HttpServletRequest req, HttpServletResponse resp) {
		logger.info("login Controller...");

		return "index";
	}

	@Content(value = "登出")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		logger.info("logout func...");

		return "index";
	}

}
