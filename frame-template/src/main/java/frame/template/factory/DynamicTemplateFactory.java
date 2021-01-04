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
	 * ģ�����
	 */
	private Map<String, Object> templateParamMap = new HashMap<>();

	/**
	 * ��list
	 */
	private List<String> divList = new ArrayList<>();
	/**
	 * ���list
	 */
	private List<String> jsList = new ArrayList<>();
	/**
	 * У��list
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

	// ������${��ͷ ��}��β���ַ���
	private Pattern p = Pattern.compile("\\$\\{.*?\\}");

	/**
	 * ��templateParamMap��divList��jsList��fillComponentsJsValidatorList��ֵ
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
				log.debug("�����ڵ����������");
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
				// ע�⣬���滻�ַ�����ʹ�÷�б�� (\) ����Ԫ���� ($) ���ܵ�������Ϊ����ֵ�滻�ַ���ʱ�������Ľ����ͬ��
				// ��Ԫ���ſ���Ϊ�����������Ѳ��������е����ã���б�߿�����ת���滻�ַ����е�����ֵ�ַ���
				v = v.replace("\\", "\\\\").replace("$", "\\$");
				// �滻�����ҵ����ַ���
				m.appendReplacement(sb, v);
			}
			// �����˼������һ��
			m.appendTail(sb);
			inputStream = new ByteArrayInputStream(sb.toString().getBytes(CharsetConst.UTF8));
		} catch (IOException e) {
			log.error("�޸�ģ�����쳣==>{}", e);
		}
		return inputStream;
	}

	public InputStream getTemplateInputStream(String templateId, String businessId, Map<String, Object> enterParamMap,
			InputStream inputStream) {
		// ===�ֶ�����dataMap
		List<ManualInputFieldConfig> manualInputFieldConfigs = manualInputFieldConfigDao.selectByTemplateId(templateId);
		// ===��ʼ��dataMap
		Builder builder = new Builder(businessId, templateId, enterParamMap);
		builder.initTemplateParamMap();
		for (ManualInputFieldConfig manualInputFieldConfig : manualInputFieldConfigs) {
			builder.addTemplateParamMap(manualInputFieldConfig);
		}
		// ����ģ��������pdf��
		ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) PdfExportUtil.exportPdfByHtml("ģ��",
				inputStream, CharsetConst.UTF8, templateParamMap, PathConst.SIMSUN_FONT_PATH, "", null);
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

	/**
	 *
	 * @param templateId
	 * @param businessId
	 * @param enterParamMap ���
	 */
	private void create(String templateId, String businessId, Map<String, Object> enterParamMap) {
		// ���ҵ������
		Builder builder = new Builder(businessId, templateId, enterParamMap);
		builder.initTemplateParamMap();
		// ��ȡ���������������ڻ��ԣ����δ������ͷ���һ���յ�map
		List<ManualInputFieldConfig> manualInputFieldConfigs = manualInputFieldConfigDao.selectByTemplateId(templateId);
		for (ManualInputFieldConfig manualInputFieldConfig : manualInputFieldConfigs) {
			builder.addComponent(manualInputFieldConfig);
		}
	}



}
