package frame.template.factory.component;

import frame.template.common.BeanFactory;
import frame.template.dao.ManualInputFieldEnumDao;
import frame.template.vo.ManualInputFieldConfig;
import frame.template.vo.ManualInputFieldEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class EnumSelectComponent extends HtmlComponentFactory {
	private ManualInputFieldEnum currentSelectedEnum;
	/**
	 *
	 */
	private List<ManualInputFieldEnum> optionList;

	public EnumSelectComponent(ManualInputFieldConfig manualInputFieldConfig, String fieldVariableValue,
			Map<String, Object> enterParamMap) {
		super(manualInputFieldConfig, fieldVariableValue, enterParamMap);
		ManualInputFieldEnumDao manualInputFieldEnumDao = BeanFactory.getBean(ManualInputFieldEnumDao.class);
		this.optionList = manualInputFieldEnumDao.selectByManualInputFieldConfigId(manualInputFieldConfigId);
		// currentSelected=fieldVariableValue
		this.currentSelectedEnum = this.optionList.stream()
				.filter(enumEntity -> StringUtils.equals(fieldVariableValue, enumEntity.getEnumKey())).findFirst()
				.orElse(new ManualInputFieldEnum());
	}

	@Override
	public String getTemplateParamMapValue() {
		String enumValue =
				StringUtils.isNotBlank(currentSelectedEnum.getEnumValue()) ? currentSelectedEnum.getEnumValue() : "";
		return "<span class='blue' " + prefixExtendName + fieldVariableName + ">" + enumValue + "</span>";
	}

	@Override
	public String getDiv() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group'><label for='" + fieldVariableName + "'>" + fieldName + "</label>"
				+ "<select class='form-control' id='" + fieldVariableName + "'name='" + fieldVariableName + "'"
				+ "        onchange=\"backfillContract('" + fieldVariableName + "','select')\">"
				+ "    <option value='null'>请选择</option>");
		for (ManualInputFieldEnum manualInputFieldEnum : optionList) {
			sb.append("<option value='" + manualInputFieldEnum.getEnumKey() + "'");
			if (StringUtils.isNotBlank(fieldVariableValue)
					&& fieldVariableValue.equals(manualInputFieldEnum.getEnumKey())) {
				sb.append("selected='selected'");
			}
			sb.append(">" + manualInputFieldEnum.getEnumValue() + "</option>");
		}
		sb.append("</select></div>");
		return sb.toString();
	}

	@Override
	public String getJs() {
		return "";
	}

	@Override
	public String getFillComponentsJsValidator() {
		return fieldVariableName + ": {validators: {notEmpty: {message: '请选择'},callback: {message: '请选择',"
				+ "callback: function (value, validator) {"
				+ "if (value === 'null') {return false;} else {return true;}}}}}";
	}
}
