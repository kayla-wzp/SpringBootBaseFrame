package frame.template.vo.reqres;


import frame.template.common.FormatNotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ManualInputFieldConfigReq {
	@FormatNotNull("ģ��id")
	private String fileTemplateId;
	@FormatNotNull("�ֶ�����")
	private String fieldName;
	@FormatNotNull("�ֶ�������")
	private String fieldVariableName;
	@FormatNotNull("�ֶ�����")
	private String fieldType;

	private String querySql;

	private String formatRule;

	private String uppercaseAmountVariableName;
	@FormatNotNull("�Ƿ����")
	private String isRequired;
	private String defaultValue;
	private String checkRegex;
	private String displayOrder;
	private String remark;
	private String manualInputFieldConfigId;


}
