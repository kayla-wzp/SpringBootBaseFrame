package frame.base.controller;

import frame.base.config.api.CommonResult;
import frame.base.service.account.UserService;
import frame.base.vo.account.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@Slf4j
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/getUserByLoginNo", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有用户")
	public User getAllUser(@RequestParam("socialCreditCode") String socialCreditCode,
			@RequestParam("loginNo") String loginNo) {
		log.info(socialCreditCode);
		return userService.getUserByLoginNo(loginNo);
	}

	@RequestMapping(value = "/getAllUserBySocialCreditCode", method = RequestMethod.GET)
	@ApiOperation(value = "获取所有用户")
	public List<User> getAllUserBySocialCreditCode(@RequestParam("socialCreditCode") String socialCreditCode) {
		return userService.getAllUserBySocialCreditCode(socialCreditCode);
	}

	@ResponseBody
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ApiOperation(value = "新增用户")
	public CommonResult saveUser(@RequestBody User user) {
		int count = userService.saveUser(user);
		if (count > 0) {
			return CommonResult.success(count);
		} else {
			return CommonResult.failed();
		}
	}
}
