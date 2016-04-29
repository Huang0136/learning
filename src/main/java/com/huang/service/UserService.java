package com.huang.service;

import java.util.List;

import com.huang.bean.User;

/**
 * user实体
 * @author huanggh
 * @since 2016-04=18 19:41
 */
public interface UserService {
	
	public List<User> findAllUser();
	
	public User findUserByUuid(String uuid);
	
	public User findUserByUsername(String username);
	
	public void delUserByUserUuid(String uuid);
	
	public void addUser(User user);
}
