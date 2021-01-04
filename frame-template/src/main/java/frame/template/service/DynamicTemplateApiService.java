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
	 * ͨ��ָ��ģ������html�ַ���
	 *
	 * @param inputStream ģ��IO
	 * @param businessId ҵ��id ��ʼ��ʱ���Բ�������������ʱ��Ҫ��
	 * @param templateId ģ��id
	 * @param enterParamMap ����sql�����õ����в���
	 * @return
	 */
	public String getHtmlViewStr(InputStream inputStream, String businessId, String templateId,
			Map<String, Object> enterParamMap) {
		DynamicTemplateFactory dynamicTemplateFactory = BeanFactory.getBean(DynamicTemplateFactory.class);
		return dynamicTemplateFactory.getHtmlViewStr(templateId, businessId, enterParamMap, inputStream);
	}

	/**
	 * ������
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
		data.setId("����");
		data.setBusinessId(dto.getBusinessId());
		data.setTemplatePrimaryKey(dto.getTemplateId());
		return data;
	}

	/**
	 * ���ɺ�ͬ
	 *
	 * @param inputStream ģ��IO
	 * @param businessId ҵ��id
	 * @param templateId ģ��id
	 * @param enterParamMap ��ʼ��sql��Ҫ�Ĳ���
	 * @return �������õ��ļ���
	 */
	public InputStream generateContract(InputStream inputStream, String businessId, String templateId,
			Map<String, Object> enterParamMap) {
		DynamicTemplateFactory dynamicTemplateFactory = BeanFactory.getBean(DynamicTemplateFactory.class);
		return dynamicTemplateFactory.getTemplateInputStream(templateId, businessId, enterParamMap, inputStream);
	}

}
