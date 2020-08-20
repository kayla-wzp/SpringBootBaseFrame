package frame.base.service.account;


import frame.base.dao.UserDao;
import frame.base.vo.account.AdminUserDetails;
import frame.base.vo.account.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private PasswordEncoder passwordEncoder;

	public List<User> getUser() {
		List<User> userList = userDao.getUserBySocialCreditCode("9144030034270389XD");
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set("hhah", "9144030034270389XD");
		redisTemplate.opsForList().rightPushAll("oowwoo", userList);

		List<User> oowwoo = redisTemplate.opsForList().range("oowwoo", 0, -1);
		System.out.println(oowwoo);
		return userList;
	}

	public List<User> getUserByLoginNo(String loginNo) {
		return userDao.getUserByLoginNo(loginNo);
	}

	public List<User> getAllUserBySocialCreditCode(String socialCreditCode) {
		return userDao.getUserBySocialCreditCode(socialCreditCode);

	}

	public int saveUser(User user) {
		return userDao.insert(user);
	}

	public UserDetails loadUserByUsername(String username) {
		List<User> userList = getUserByLoginNo(username);
		return new AdminUserDetails(userList.get(0), new ArrayList<Permission>());
	}

	public User register(User user) {
		user.setCreateTime(new Date());
		user.setStatus("1");
		List<User> userList = userDao.getUserByLoginNo(user.getLoginNo());
		if (userList.size() > 0) {
			return null;
		}
		// 将密码进行加密操作
		String encodePassword = passwordEncoder.encode(user.getLoginPassword());
		user.setLoginPassword(encodePassword);
		userDao.insert(user);
		return user;
	}
}
