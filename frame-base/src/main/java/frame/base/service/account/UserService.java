package frame.base.service.account;


import frame.base.dao.UserDao;
import frame.base.vo.account.AdminUserDetails;
import frame.base.vo.account.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private RedisTemplate redisTemplate;

	public List<User> getUser() {
		List<User> userList = userDao.getUserBySocialCreditCode("9144030034270389XD");
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set("hhah", "9144030034270389XD");
		redisTemplate.opsForList().rightPushAll("oowwoo", userList);

		List<User> oowwoo = redisTemplate.opsForList().range("oowwoo", 0, -1);
		System.out.println(oowwoo);
		return userList;
	}

	public User getUserByLoginNo(String loginNo) {
		return userDao.getUserByLoginNo(loginNo);
	}

	public List<User> getAllUserBySocialCreditCode(String socialCreditCode) {
		return userDao.getUserBySocialCreditCode(socialCreditCode);

	}

	public int saveUser(User user) {
		return 0;
	}

	public UserDetails loadUserByUsername(String username) {
		User user = getUserByLoginNo(username);
		return new AdminUserDetails(user,new ArrayList<Permission>());
	}
}
