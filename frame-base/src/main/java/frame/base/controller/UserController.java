package frame.base.controller;

import frame.base.service.account.UserService;
import frame.base.vo.account.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("�û�")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/getUserByLoginNo", method = RequestMethod.GET)
	@ApiOperation(value = "��ȡ�����û�")
	public User getAllUser(@RequestParam("socialCreditCode") String socialCreditCode,
			@RequestParam("loginNo") String loginNo) {
		return userService.getUserByLoginNo(socialCreditCode, loginNo);
	}

	@RequestMapping(value = "/getAllUserBySocialCreditCode", method = RequestMethod.GET)
	@ApiOperation(value = "��ȡ�����û�")
	public List<User> getAllUserBySocialCreditCode(@RequestParam("socialCreditCode") String socialCreditCode) {
		return userService.getAllUserBySocialCreditCode(socialCreditCode);
	}
}
