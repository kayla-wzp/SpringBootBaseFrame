package frame.template.common;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
	private String token;
	private String saleSystem;
	private String clientType;
	private String tradeWay;
	private String clientIp;
	private String stationId;
	private Date businessTime;

	private String userId;
	private String operator;
	private String socialCreditCode;
}
