package frame.template.vo.dto;

import frame.template.common.BaseDto;
import frame.template.vo.ManualInputFieldEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManualInputFieldEnumDto extends BaseDto {

	private String manualInputFieldConfigId;

	private String enumJsonStr;

	private List<ManualInputFieldEnum> enumList;

}
