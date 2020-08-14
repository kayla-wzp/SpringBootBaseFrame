package frame.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import frame.base.service.account.UserService;
import frame.base.vo.account.User;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/index")
@Api(tags = "索引")
public class IndexController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	@ApiOperation(value = "查找所有记录")
	public String index() {
		return "hello world!!!!!!!!!";
	}

	@RequestMapping(value = "/getBook",method = RequestMethod.GET)
	@ApiOperation(value = "获取所有书籍")
	public List<User> getBook() {
		List<User> user = userService.getUser();
		System.out.println(1/0);
		return user;
	}
}
