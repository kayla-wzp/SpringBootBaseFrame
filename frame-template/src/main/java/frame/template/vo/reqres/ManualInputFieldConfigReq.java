package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ManualInputFieldConfigReq {
	@FormatNotNull("模板id")
	private String fileTemplateId;
	@FormatNotNull("字段名称")
	private String fieldName;
	@FormatNotNull("字段属性名")
	private String fieldVariableName;
	@FormatNotNull("字段类型")
	private String fieldType;

	private String querySql;

	private String formatRule;

	private String uppercaseAmountVariableName;
	@FormatNotNull("是否必填")
	private String isRequired;
	private String defaultValue;
	private String checkRegex;
	private String displayOrder;
	private String remark;
	private String manualInputFieldConfigId;


}
