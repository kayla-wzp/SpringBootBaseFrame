package frame.template.vo.reqres;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class QueryManualInputFieldConfigListRes{
	private String manualInputFieldConfigId;

	private String fieldName;

	private String fieldVariableName;

	private String fileTemplateId;

	private String fieldType;

	private String fieldTypeText;

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
