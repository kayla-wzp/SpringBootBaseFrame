package frame.template.vo.dto;

import cffs.core.dto.BaseDto;
import cffs.po.dynamictemplate.ManualInputFieldEnum;
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
