package org.shadower.example.dubbo.web;

import org.shadower.example.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/index", "/index.html", "/index.htm" })
	@ResponseBody
	private String index() {
		return userService.getUserName(1);
	}
}
