package frame.template.factory.component;


import cffs.po.dynamictemplate.ManualInputFieldConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class DateBoxComponent extends HtmlComponentFactory {


	public DateBoxComponent(ManualInputFieldConfig manualInputFieldConfig, String fieldVariableValue,
			Map<String, Object> enterParamMap) {
		super(manualInputFieldConfig, fieldVariableValue, enterParamMap);
	}

	@Override
	public String getTemplateParamMapValue() {
		return "<span class='blue' " + prefixExtendName + fieldVariableName + ">" + fieldVariableValue + "</span>";
	}

	@Override
	public String getDiv() {
		return "<div class='form-group'><label>" + fieldName + "��</label> <div class='input-group date' id='"
				+ fieldVariableName + extendName + "'><span class='input-group-addon'>"
				+ "        <span class='glyphicon glyphicon-calendar'></span></span>" + "   <input type='text' value='"
				+ fieldVariableValue + "' class='form-control' name='" + fieldVariableName + "' id='"
				+ fieldVariableName + "' autocomplete='off' onchange=\"backfillContract('" + fieldVariableName
				+ "')\"/></div></div>";
	}

	@Override
	public String getJs() {
		return "$('#" + fieldVariableName + extendName + "').datetimepicker({ format: 'yyyy-mm-dd',"
				+ "language: 'zh-CN', autoclose: true, weekStart: 1, todayBtn: 1, autoclose: 1,"
				+ "todayHighlight: 1, startView: 2, minView: 2, forceParse: 0 "
				+ "}).on('changeDate', function () { const data = $('#contractForm').data('bootstrapValidator');"
				+ "data.validateField('" + fieldVariableName + "') });";
	}

	@Override
	public String getFillComponentsJsValidator() {
		return fieldVariableName + ": {trigger: 'change',validators: { notEmpty: {message: '��ѡ������' },"
				+ "date: {format: 'YYYY-MM-DD',message: '��ѡ����ȷ������'}}}";
	}

}
