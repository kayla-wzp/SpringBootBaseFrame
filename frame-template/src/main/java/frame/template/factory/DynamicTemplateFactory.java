package frame.template.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import frame.template.common.BusinessDataEchoObjectTypeEnum;
import frame.template.common.CharsetConst;
import frame.template.common.ManualInputFieldTypeConst;
import frame.template.common.PathConst;
import frame.template.common.util.CollectionUtil;
import frame.template.common.util.PdfExportUtil;
import frame.template.common.util.StrUtil;
import frame.template.factory.component.DateBoxComponent;
import frame.template.factory.component.EnumSelectComponent;
import frame.template.factory.component.HtmlComponentFactory;
import frame.template.factory.component.InputComponent;
import frame.template.factory.component.SqlSelectComponent;
import frame.template.dao.BusinessDataEchoConfigDao;
import frame.template.dao.ManualInputFieldConfigDao;
import frame.template.dao.ManualInputTempDataDao;
import frame.template.service.DynamicSqlService;
import frame.template.vo.BusinessDataEchoConfig;
import frame.template.vo.ManualInputFieldConfig;
import frame.template.vo.ManualInputTempData;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;

@Slf4j
@Service
@Scope("prototype")
public class DynamicTemplateFactory {
	/**
	 * 模板参数
	 */
	private Map<String, Object> templateParamMap = new HashMap<>();

	/**
	 * 表单list
	 */
	private List<String> divList = new ArrayList<>();
	/**
	 * 组件list
	 */
	private List<String> jsList = new ArrayList<>();
	/**
	 * 校验list
	 */
	private List<String> fillComponentsJsValidatorList = new ArrayList<>();
	private Set<String> templateEchoKeys = new HashSet<>();

	@Resource
	private ManualInputFieldConfigDao manualInputFieldConfigDao;
	@Resource
	private ManualInputTempDataDao manualInputTempDataDao;
	@Resource
	private BusinessDataEchoConfigDao businessDataEchoConfigDao;
	@Resource
	private DynamicSqlService dynamicSqlService;
	private static final String NEED_INPUT_DYNAMIC_TEMPLATE_PATH =
			"/config/template/dynamictemplate/needInputTemplate.html";
	private static final String NO_INPUT_DYNAMIC_TEMPLATE_PATH =
			"/config/template/dynamictemplate/noInputTemplate.html";

	// 检索以${开头 以}结尾的字符串
	private Pattern p = Pattern.compile("\\$\\{.*?\\}");

	/**
	 * 给templateParamMap、divList、jsList、fillComponentsJsValidatorList赋值
	 */
	class Builder {
		private String templateId;
		private Map<String, Object> enterParamMap;
		private Map<String, Object> manualInputSaveDataObject;

		public Builder(String businessId, String templateId, Map<String, Object> enterParamMap) {
			this.enterParamMap = enterParamMap;
			this.templateId = templateId;
			initManualInputSaveDataObject(businessId, templateId);
		}

		public void initTemplateParamMap() {
			List<BusinessDataEchoConfig> businessDataEchoConfigs =
					businessDataEchoConfigDao.selectByTemplateId(templateId);
			for (BusinessDataEchoConfig businessDataEchoConfig : businessDataEchoConfigs) {
				String querySql = businessDataEchoConfig.getQuerySql();
				String objectType = businessDataEchoConfig.getObjectType();
				String objectAttributeName = businessDataEchoConfig.getObjectAttributeName();
				if (BusinessDataEchoObjectTypeEnum.OBJECT_LIST_MAP.getObjectType().equals(objectType)) {
					List<Map<String, Object>> listMap = dynamicSqlService.getListMap(querySql, enterParamMap);
					for (Map<String, Object> map : listMap) {
						map.forEach((k, v) -> {
							map.put(k, v);
							templateEchoKeys.add(objectAttributeName + "." + k);
						});
					}
					templateParamMap.put(objectAttributeName, listMap);
				} else if (BusinessDataEchoObjectTypeEnum.OBJECT_MAP.getObjectType().equals(objectType)) {
					Map<String, Object> map = dynamicSqlService.getMap(querySql, enterParamMap);
					map.forEach((k, v) -> {
						map.put(k, v);
						templateEchoKeys.add(objectAttributeName + "." + k);
					});
					templateParamMap.put(objectAttributeName, map);
				} else if (BusinessDataEchoObjectTypeEnum.OBJECT_String.getObjectType().equals(objectType)) {
					String str = dynamicSqlService.getStr(querySql, enterParamMap);
					templateParamMap.put(objectAttributeName, str);
					templateEchoKeys.add(objectAttributeName);
				}
			}
		}

		public void addTemplateParamMap(ManualInputFieldConfig manualInputFieldConfig) {
			HtmlComponentFactory htmlComponent = getHtmlComponentFactory(manualInputFieldConfig);
			templateParamMap.put(manualInputFieldConfig.getFieldVariableName(),
					htmlComponent.getTemplateParamMapValue());
		}

		public void addComponent(ManualInputFieldConfig manualInputFieldConfig) {

			HtmlComponentFactory htmlComponent = getHtmlComponentFactory(manualInputFieldConfig);
			templateParamMap.put(manualInputFieldConfig.getFieldVariableName(),
					htmlComponent.getTemplateParamMapValue());
			divList.add(htmlComponent.getDiv());
			jsList.add(htmlComponent.getJs());
			fillComponentsJsValidatorList.add(htmlComponent.getFillComponentsJsValidator());
		}

		private HtmlComponentFactory getHtmlComponentFactory(ManualInputFieldConfig manualInputFieldConfig) {
			String fieldVariableName = manualInputFieldConfig.getFieldVariableName();
			String fieldVariableValue = "";
			if (manualInputSaveDataObject != null) {
				fieldVariableValue = (String) manualInputSaveDataObject.get(fieldVariableName);
			}
			HtmlComponentFactory htmlComponent = null;
			String fieldType = manualInputFieldConfig.getFieldType();
			if (ManualInputFieldTypeConst.DATE_BOX.equals(fieldType)) {
				htmlComponent = new DateBoxComponent(manualInputFieldConfig, fieldVariableValue, enterParamMap);
			} else if (ManualInputFieldTypeConst.ENUM_SELECT.equals(fieldType)) {
				htmlComponent = new EnumSelectComponent(manualInputFieldConfig, fieldVariableValue, enterParamMap);
			} else if (ManualInputFieldTypeConst.INPUT.equals(fieldType)) {
				htmlComponent = new InputComponent(manualInputFieldConfig, fieldVariableValue, enterParamMap);
			} else if (ManualInputFieldTypeConst.SQL_SELECT.equals(fieldType)) {
				htmlComponent = new SqlSelectComponent(manualInputFieldConfig, fieldVariableValue, enterParamMap);
			} else {
				log.debug("不存在的输入框类型");
			}
			return htmlComponent;
		}

		private void initManualInputSaveDataObject(String businessId, String templateId) {
			ManualInputTempData manualInputTempData =
					manualInputTempDataDao.selectByBusinessIdAndTemplateId(businessId, templateId);
			if (manualInputTempData != null) {
				String jsonStr = manualInputTempData.getJsonStr();
				manualInputSaveDataObject = (Map) JSON.parse(jsonStr);
			} else {
				manualInputSaveDataObject = new HashMap<>();
			}
		}
	}

	public String getHtmlViewStr(String templateId, String businessId, Map<String, Object> enterParamMap,
			InputStream inputStream) {
		inputStream = changeInputStream(inputStream);
		create(templateId, businessId, enterParamMap);
		Template template = PdfExportUtil.initFreemarkerTemplate("templateFileName", CharsetConst.UTF8, inputStream);
		String htmlContent = PdfExportUtil.freeMarkerRender(template, CharsetConst.UTF8, templateParamMap);
		Map<String, Object> resultParamMap = new HashMap<>();
		InputStream io;
		if (CollectionUtil.isEmpty(divList)) {
			resultParamMap.put("templateStr", htmlContent);
			io = this.getClass().getResourceAsStream(NO_INPUT_DYNAMIC_TEMPLATE_PATH);
		} else {
			String divStr = StrUtil.joinWith("", divList);
			String jsStr = StrUtil.joinWith("", jsList);
			String fillComponentsJsValidator = StrUtil.joinWith(",", fillComponentsJsValidatorList);
			resultParamMap.put("templateStr", htmlContent);
			resultParamMap.put("fillComponents", divStr);
			resultParamMap.put("fillComponentsJs", jsStr);
			resultParamMap.put("fillComponentsJsValidator", fillComponentsJsValidator);
			io = this.getClass().getResourceAsStream(NEED_INPUT_DYNAMIC_TEMPLATE_PATH);
		}

		template = PdfExportUtil.initFreemarkerTemplate("templateFileName", CharsetConst.UTF8, io);
		htmlContent = PdfExportUtil.freeMarkerRender(template, CharsetConst.UTF8, resultParamMap);
		return htmlContent;
	}

	private InputStream changeInputStream(InputStream inputStream) {
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, CharsetConst.UTF8);
			String str = writer.toString();
			StringBuffer sb = new StringBuffer();
			Matcher m = p.matcher(str);
			while (m.find()) {
				String tmp = m.group();
				String v = HtmlComponentFactory.getEcho(tmp);
				// 注意，在替换字符串中使用反斜线 (\) 和美元符号 ($) 可能导致与作为字面值替换字符串时所产生的结果不同。
				// 美元符号可视为到如上所述已捕获子序列的引用，反斜线可用于转义替换字符串中的字面值字符。
				v = v.replace("\\", "\\\\").replace("$", "\\$");
				// 替换掉查找到的字符串
				m.appendReplacement(sb, v);
			}
			// 别忘了加上最后一点
			m.appendTail(sb);
			inputStream = new ByteArrayInputStream(sb.toString().getBytes(CharsetConst.UTF8));
		} catch (IOException e) {
			log.error("修改模板流异常==>{}", e);
		}
		return inputStream;
	}

	public InputStream getTemplateInputStream(String templateId, String businessId, Map<String, Object> enterParamMap,
			InputStream inputStream) {
		// ===手动输入dataMap
		List<ManualInputFieldConfig> manualInputFieldConfigs = manualInputFieldConfigDao.selectByTemplateId(templateId);
		// ===初始化dataMap
		Builder builder = new Builder(businessId, templateId, enterParamMap);
		builder.initTemplateParamMap();
		for (ManualInputFieldConfig manualInputFieldConfig : manualInputFieldConfigs) {
			builder.addTemplateParamMap(manualInputFieldConfig);
		}
		// 根据模板流生成pdf流
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) PdfExportUtil.exportPdfByHtml("模板",
				inputStream, CharsetConst.UTF8, templateParamMap, PathConst.SIMSUN_FONT_PATH, "", null);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	/**
	 *
	 * @param templateId
	 * @param businessId
	 * @param enterParamMap 入参
	 */
	private void create(String templateId, String businessId, Map<String, Object> enterParamMap) {
		// 填充业务数据
		Builder builder = new Builder(businessId, templateId, enterParamMap);
		builder.initTemplateParamMap();
		// 获取保存的填充数据用于回显，如果未保存过就返回一个空的map
		List<ManualInputFieldConfig> manualInputFieldConfigs = manualInputFieldConfigDao.selectByTemplateId(templateId);
		for (ManualInputFieldConfig manualInputFieldConfig : manualInputFieldConfigs) {
			builder.addComponent(manualInputFieldConfig);
		}
	}



}
