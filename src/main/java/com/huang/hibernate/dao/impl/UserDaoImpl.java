package com.huang.hibernate.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.huang.bean.User;
import com.huang.hibernate.dao.UserDao;

/**
 * user实体
 * @author huanggh
 * @since 2016-04=18 19:41
 */
@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sf;
	
	private Session getSession() {
		return sf.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		Query query = this.getSession().createQuery("FROM User U");

		return (List<User>) query.list();
	}

	public User findUserByUuid(String uuid) {
		return (User)this.getSession().get(User.class, uuid);
	}

	@SuppressWarnings("unchecked")
	public User findUserByUsername(String username) {
		Query query = this.getSession().createQuery("FROM User U WHERE U.username = :username ");

		query.setString("username", username);

		List<User> users = (List<User>) query.list();

		return (users != null && users.size() > 0) ? users.get(0) : null;
	}

	public void delUserByUserUuid(String uuid) {
		User user = (User)this.getSession().get(User.class, uuid);
		this.getSession().delete(user);
	}

	public void addUser(User user) {
		this.getSession().save(user);
	}
	
}
