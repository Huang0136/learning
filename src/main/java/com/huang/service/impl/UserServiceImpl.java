package com.huang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huang.bean.User;
import com.huang.hibernate.dao.UserDao;
import com.huang.service.UserService;

/**
 * user实体
 * @author huanggh
 * @since 2016-04=18 19:41
 */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDaoImpl;
	
	public List<User> findAllUser() {
		return this.userDaoImpl.findAllUser();
	}

	public User findUserByUuid(String uuid) {
		return this.userDaoImpl.findUserByUuid(uuid);
	}

	public User findUserByUsername(String username) {
		return this.userDaoImpl.findUserByUsername(username);
	}

	@Transactional
	public void delUserByUserUuid(String uuid) {
		this.userDaoImpl.delUserByUserUuid(uuid);
	}

	@Transactional
	public void addUser(User user) {
		this.userDaoImpl.addUser(user);
	}
	
}
