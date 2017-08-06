package com.unionpay.shadower.example.dubbo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unionpay.shadower.example.api.UserService;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		context.start();

		UserService userService = (UserService) context.getBean("userService");

		for (int i = 0; i < 10; i++) {
			String name = userService.getUserName(i);
			logger.info(name);
		}
	}
}
