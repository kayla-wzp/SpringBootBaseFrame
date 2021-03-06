package frame.template.vo.dto;

import frame.template.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BusinessDataEchoConfigDto extends BaseDto {
	private String businessDataEchoConfigId;
	private String templateId;
	private String objectAttributeName;
	private String objectType;
	private String querySql;

}
