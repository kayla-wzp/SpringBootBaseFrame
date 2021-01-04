package frame.template.service;

import frame.template.common.BeanFactory;
import frame.template.dao.ManualInputTempDataDao;
import frame.template.factory.DynamicTemplateFactory;
import frame.template.vo.ManualInputTempData;
import frame.template.vo.TemplateSaveDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Map;

@Service
public class DynamicTemplateApiService {

	@Resource
	private ManualInputTempDataDao manualInputTempDataDao;

	/**
	 * 通过指定模板生成html字符串
	 *
	 * @param inputStream 模板IO
	 * @param businessId 业务id 初始化时可以不传，填充过回显时需要传
	 * @param templateId 模板id
	 * @param enterParamMap 配置sql中所用的所有参数
	 * @return
	 */
	public String getHtmlViewStr(InputStream inputStream, String businessId, String templateId,
			Map<String, Object> enterParamMap) {
		DynamicTemplateFactory dynamicTemplateFactory = BeanFactory.getBean(DynamicTemplateFactory.class);
		return dynamicTemplateFactory.getHtmlViewStr(templateId, businessId, enterParamMap, inputStream);
	}

	/**
	 * 表单保存
	 *
	 * @param dto
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveFormData(TemplateSaveDto dto) {
		validateBeforeSave(dto);
		ManualInputTempData data = buildManualInputTempData(dto);
		manualInputTempDataDao.deleteBusinessIdAndTemplateId(dto.getBusinessId(), dto.getTemplateId());
		manualInputTempDataDao.insertSelective(data);
	}

	private void validateBeforeSave(TemplateSaveDto dto) {

	}

	private ManualInputTempData buildManualInputTempData(TemplateSaveDto dto) {
		ManualInputTempData data = new ManualInputTempData(dto.getParamStr());
		data.setId("序列");
		data.setBusinessId(dto.getBusinessId());
		data.setTemplatePrimaryKey(dto.getTemplateId());
		return data;
	}

	/**
	 * 生成合同
	 *
	 * @param inputStream 模板IO
	 * @param businessId 业务id
	 * @param templateId 模板id
	 * @param enterParamMap 初始化sql需要的参数
	 * @return 返回填充好的文件流
	 */
	public InputStream generateContract(InputStream inputStream, String businessId, String templateId,
			Map<String, Object> enterParamMap) {
		DynamicTemplateFactory dynamicTemplateFactory = BeanFactory.getBean(DynamicTemplateFactory.class);
		return dynamicTemplateFactory.getTemplateInputStream(templateId, businessId, enterParamMap, inputStream);
	}

}
