package frame.template.vo.dto;

import cffs.core.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DynamicTemplateConfigDeleteDto extends BaseDto {
	private String businessDataEchoConfigId;

}
