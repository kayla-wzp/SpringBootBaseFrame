package frame.base.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import frame.base.vo.account.User;

import java.util.List;

@Mapper
public interface UserDao {
	List<User> getUserBySocialCreditCode(@Param("socialCreditCode") String socialCreditCode);

	User getUserBySocialCreditCodeAndLoginNo(@Param("socialCreditCode") String socialCreditCode, @Param("loginNo") String loginNo);
}
