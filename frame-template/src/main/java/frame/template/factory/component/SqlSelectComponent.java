package frame.template.factory.component;

import asset.common.component.BeanFactory;
import cffs.core.service.dynamictemplate.DynamicSqlService;
import cffs.po.dynamictemplate.ManualInputFieldConfig;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlSelectComponent extends HtmlComponentFactory {
	private Map<String, String> currentSelectedOption = new HashMap<>();
	/**
	 *
	 */
	private List<Map<String, Object>> optionList;

	public SqlSelectComponent(ManualInputFieldConfig manualInputFieldConfig, String fieldVariableValue,
			Map<String, Object> enterParamMap) {
		super(manualInputFieldConfig, fieldVariableValue, enterParamMap);
		DynamicSqlService dynamicSqlService = BeanFactory.getBean(DynamicSqlService.class);
		this.optionList = dynamicSqlService.getListMap(querySql, enterParamMap);
		if (StringUtils.isNotBlank(super.fieldVariableValue)) {
			currentSelectedOption = (Map<String, String>) JSON.parse(super.fieldVariableValue);
		}
	}

	@Override
	public Map<String, String> getTemplateParamMapValue() {
		Map<String, Object> map = this.optionList.get(0);
		Set<String> keys = map.keySet();
		Map<String, String> currentSelectedOptionMap = new HashMap<>();
		for (String key : keys) {
			String keyValue = currentSelectedOption.get(key);
			currentSelectedOptionMap.put(key,
					getDropDownBoxSQLTemplateStr(fieldVariableName, key, keyValue == null ? "" : keyValue));
		}
		return currentSelectedOptionMap;
	}

	@Override
	public String getDiv() {
		String currentSelected = currentSelectedOption.get("key");
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='form-group'><label for='" + super.fieldVariableName + "'>" + super.fieldName + "</label>"
				+ "<select class='form-control' id='" + super.fieldVariableName + "'name='" + super.fieldVariableName
				+ "'" + "        onchange=\"backfillContract('" + super.fieldVariableName + "','select')\">"
				+ "    <option value='null'>��ѡ��</option>");
		for (Map<String, Object> map : this.optionList) {
			String jsonString = JSON.toJSONString(map);
			sb.append("<option value='" + jsonString + "'");
			if (StringUtils.isNotBlank(currentSelected) && currentSelected.equals(map.get("key"))) {
				sb.append("selected='selected'");
			}
			sb.append(">" + map.get("key") + "</option>");
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
		return fieldVariableName + ": {validators: {notEmpty: {message: '��ѡ��'},callback: {message: '��ѡ��',"
				+ "callback: function (value, validator) {"
				+ "if (value === 'null') {return false;} else {return true;}}}}}";
	}

	private String getDropDownBoxSQLTemplateStr(String fieldVariableName, String fieldVariableValue, Object value) {
		return "<span class='blue' " + prefixExtendName + fieldVariableName + "='" + fieldVariableValue + "'>" + value
				+ "</span>";
	}

}
