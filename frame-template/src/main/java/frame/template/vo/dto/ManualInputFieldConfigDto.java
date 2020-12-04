package frame.template.vo.dto;

import cffs.core.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManualInputFieldConfigDto extends BaseDto {
	private String fileTemplateId;
	private String fieldName;
	private String fieldVariableName;
	private String fieldType;
	private String querySql;
	private String formatRule;
	private String uppercaseAmountVariableName;
	private String isRequired;
	private String defaultValue;
	private String checkRegex;
	private String displayOrder;
	private String remark;
	private String manualInputFieldConfigId;
}
