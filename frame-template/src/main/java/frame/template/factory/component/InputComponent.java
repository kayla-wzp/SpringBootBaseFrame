package frame.template.factory.component;


import java.util.Map;

import frame.template.vo.ManualInputFieldConfig;
import org.apache.commons.lang3.StringUtils;


public class InputComponent extends HtmlComponentFactory {


	public InputComponent(ManualInputFieldConfig manualInputFieldConfig, String fieldVariableValue,
                          Map<String, Object> enterParamMap) {
		super(manualInputFieldConfig, fieldVariableValue, enterParamMap);
	}

	@Override
	public String getTemplateParamMapValue() {
		return "<span class='blue' " + prefixExtendName + fieldVariableName + ">" + fieldVariableValue + "</span>";
	}

	@Override
	public String getDiv() {
		return "<div class='form-group'> <label for='" + fieldVariableName + "'>" + fieldName
				+ "</label><input class='form-control' value='" + fieldVariableValue + "' id='" + fieldVariableName
				+ "' name='" + fieldVariableName + "' oninput=\"backfillContract('" + fieldVariableName
				+ "')\"/></div>";
	}

	@Override
	public String getJs() {
		return "";
	}

	@Override
	public String getFillComponentsJsValidator() {
		StringBuilder sb = new StringBuilder();
		sb.append(fieldVariableName).append(": {validators: {notEmpty: {message: '").append(fieldName)
				.append("����Ϊ��' }");
		if (checkRegex != null) {
			sb.append(",regexp: {regexp: ").append(checkRegex).append(",message: '").append(fieldName)
					.append("��ʽ����,����������'}");
		}
		sb.append("}}");
		return sb.toString();
	}

}
