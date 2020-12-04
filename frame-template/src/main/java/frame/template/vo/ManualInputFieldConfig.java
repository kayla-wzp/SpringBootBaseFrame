package frame.template.vo;

import lombok.Data;

@Data
public class ManualInputFieldConfig {
	private String manualInputFieldConfigId;

	private String fieldName;

	private String fieldVariableName;

	private String fileTemplateId;

	private String fieldType;

	private String querySql;

	private String formatRule;

	private String uppercaseAmountVariableName;

	private String defaultValue;

	private String relatedFieldId;

	private String checkRegex;

	private String isRequired;

	private String displayOrder;

	private String remark;

}
