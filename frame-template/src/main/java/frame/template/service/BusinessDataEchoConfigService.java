package frame.template.service;

import frame.template.dao.BusinessDataEchoConfigDao;
import frame.template.dao.DynamicFileTemplateDao;
import frame.template.vo.BusinessDataEchoConfig;
import frame.template.vo.dto.BusinessDataEchoConfigDto;
import frame.template.vo.dto.DynamicTemplateConfigDeleteDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class BusinessDataEchoConfigService {
	@Resource
	private BusinessDataEchoConfigDao businessDataEchoConfigDao;
	@Resource
	private DynamicFileTemplateDao dynamicFileTemplateDao;

	public void addBusinessDataEchoConfig(BusinessDataEchoConfigDto dto) {
		validateTemplateId(dto);
		BusinessDataEchoConfig businessDataEchoConfig = buildBusinessDataEchoConfig(dto);
		businessDataEchoConfigDao.insertSelective(businessDataEchoConfig);
	}

	public void modifyBusinessDataEchoConfig(BusinessDataEchoConfigDto dto) {
		validateIdExist(dto.getBusinessDataEchoConfigId());
		validateTemplateId(dto);
		BusinessDataEchoConfig businessDataEchoConfig = buildBusinessDataEchoConfig(dto);
		businessDataEchoConfigDao.updateByPrimaryKey(businessDataEchoConfig);
	}

	public void deleteBusinessDataEchoConfig(DynamicTemplateConfigDeleteDto dto) {
		validateIdExist(dto.getBusinessDataEchoConfigId());
		businessDataEchoConfigDao.deleteByPrimaryKey(dto.getBusinessDataEchoConfigId());
	}

	private void validateTemplateId(BusinessDataEchoConfigDto dto) {
		if (dynamicFileTemplateDao.selectByPrimaryKey(dto.getTemplateId()) == null) {
			log.debug("ƒ£∞ÂID¥ÌŒÛ");
		}
	}

	private void validateIdExist(String businessDataEchoConfigId) {
		if (businessDataEchoConfigDao.selectByPrimaryKey(businessDataEchoConfigId) == null) {
			log.debug("ªÿœ‘≈‰÷√ID");
		}
	}

	private BusinessDataEchoConfig buildBusinessDataEchoConfig(BusinessDataEchoConfigDto dto) {
		BusinessDataEchoConfig businessDataEchoConfig = new BusinessDataEchoConfig();
		if (StringUtils.isBlank(dto.getBusinessDataEchoConfigId())) {
			businessDataEchoConfig.setId("–Ú¡–id");
		} else {
			businessDataEchoConfig.setId(dto.getBusinessDataEchoConfigId());
		}
		businessDataEchoConfig.setTemplatePrimaryKey(dto.getTemplateId());
		businessDataEchoConfig.setObjectAttributeName(dto.getObjectAttributeName());
		businessDataEchoConfig.setObjectType(dto.getObjectType());
		businessDataEchoConfig.setQuerySql(dto.getQuerySql());
		return businessDataEchoConfig;
	}

}
