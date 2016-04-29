package com.huang.utils;

import org.apache.log4j.Logger;

/**
 * 基于redis的分布式锁
 * @author huanggh
 * @createDate 2016-04-29 9:52
 */
public class DistributedLock {
	
	private static final Logger logger = Logger.getLogger(DistributedLock.class);
	
	
	public void lock(Object object) {
		
	}
	
	public void unLock(Object object) {
		
	}
	
	public static void main(String[] args) {
		logger.info("using redis lock...");
		
		
	}

}
