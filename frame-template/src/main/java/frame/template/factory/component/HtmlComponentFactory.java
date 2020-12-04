package frame.template.factory.component;

import java.util.Map;

import frame.template.vo.ManualInputFieldConfig;
import org.apache.commons.lang3.StringUtils;

public abstract class HtmlComponentFactory {
	protected String extendName = "1";
	protected String prefixExtendName = "t_";
	protected ManualInputFieldConfig manualInputFieldConfig;
	protected String fieldVariableValue;
	protected Map<String, Object> enterParamMap;


	protected String fieldVariableName;
	protected String querySql;
	protected String fieldName;
	protected String checkRegex;
	protected String manualInputFieldConfigId;

	public HtmlComponentFactory(ManualInputFieldConfig manualInputFieldConfig, String fieldVariableValue,
			Map<String, Object> enterParamMap) {
		this.manualInputFieldConfig = manualInputFieldConfig;
		this.fieldVariableValue = fieldVariableValue;
		this.enterParamMap = enterParamMap;
		this.fieldVariableName = manualInputFieldConfig.getFieldVariableName();
		this.querySql = manualInputFieldConfig.getQuerySql();
		this.fieldName = manualInputFieldConfig.getFieldName();
		this.checkRegex = manualInputFieldConfig.getCheckRegex();
		this.manualInputFieldConfigId = manualInputFieldConfig.getManualInputFieldConfigId();
		this.fieldVariableValue = StringUtils.isNotBlank(fieldVariableValue) ? fieldVariableValue : "";
	}
	public static String getEcho(String value) {
		return "<span class='blue'>" + value + "</span>";
	}
	public abstract Object getTemplateParamMapValue();

	public abstract String getDiv();

	public abstract String getJs();

	public abstract String getFillComponentsJsValidator();

}
