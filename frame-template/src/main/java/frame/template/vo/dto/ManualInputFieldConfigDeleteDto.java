package frame.template.vo.dto;

import frame.template.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ManualInputFieldConfigDeleteDto extends BaseDto {
	private String manualInputFieldConfigId;

}
