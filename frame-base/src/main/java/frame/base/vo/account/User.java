package frame.base.vo.account;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
	private String userId;

	private String mobile;

	private String loginPassword;

	private String socialCreditCode;

	private Date loginLastTime;

	private Date loginPasswordErrorLastTime;

	private Long loginPasswordErrorTimes;

	private Date loginPasswordUpdateTime;

	private String enterpriseType;

	private String status;

	private Date createTime;

	private Date lastUpdateTime;

	private String loginNo;

	private String email;

	private Date lastLoginTime;

}
