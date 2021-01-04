package frame.template.service;

import frame.template.common.util.CollectionUtil;
import frame.template.dao.DynamicFileTemplateDao;
import frame.template.dao.ManualInputFieldConfigDao;
import frame.template.vo.dto.ManualInputFieldConfigDeleteDto;
import frame.template.vo.dto.ManualInputFieldConfigDto;
import frame.template.vo.ManualInputFieldConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManualInputFieldConfigService {
	@Resource
	private ManualInputFieldConfigDao manualInputFieldConfigDao;
	@Resource
	private DynamicFileTemplateDao dynamicFileTemplateDao;

	public void addManualInputFieldConfig(ManualInputFieldConfigDto dto) {
		validateParam(dto);
		ManualInputFieldConfig manualInputFieldConfig = buildManualInputFieldConfig(dto);
		manualInputFieldConfigDao.insert(manualInputFieldConfig);
	}

	public void modifyManualInputFieldConfig(ManualInputFieldConfigDto dto) {
		validateParam(dto);
		ManualInputFieldConfig manualInputFieldConfig = buildManualInputFieldConfig(dto);
		manualInputFieldConfigDao.updateByPrimaryKey(manualInputFieldConfig);
	}

	public void deleteManualInputFieldConfig(ManualInputFieldConfigDeleteDto dto) {
		manualInputFieldConfigDao.deleteByPrimaryKey(dto.getManualInputFieldConfigId());
	}

	private void validateParam(ManualInputFieldConfigDto dto) {
		validateTemplateIsExist(dto);
		validateFieldVariableNameRepeat(dto);
	}

	private void validateTemplateIsExist(ManualInputFieldConfigDto dto) {
		if (dynamicFileTemplateDao.selectByPrimaryKey(dto.getFileTemplateId()) == null) {
			log.debug("该模板不存在");
		}
	}

	private void validateFieldVariableNameRepeat(ManualInputFieldConfigDto dto) {
		List<ManualInputFieldConfig> manualInputFieldConfigs =
				manualInputFieldConfigDao.selectByTemplateId(dto.getFileTemplateId());
		if (CollectionUtil.isNotEmpty(manualInputFieldConfigs)) {
			List<ManualInputFieldConfig> sameFieldVariableNameConfigs = manualInputFieldConfigs.stream()
					.filter(manualInputFieldConfig -> StringUtils.equals(dto.getFieldVariableName(),
							manualInputFieldConfig.getFieldVariableName())
							&& !StringUtils.equals(manualInputFieldConfig.getManualInputFieldConfigId(),
									dto.getManualInputFieldConfigId())

					).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(sameFieldVariableNameConfigs)) {
				log.debug("该配置参数已存在");
			}
		}
	}

	private ManualInputFieldConfig buildManualInputFieldConfig(ManualInputFieldConfigDto dto) {
		ManualInputFieldConfig manualInputFieldConfig = new ManualInputFieldConfig();
		String manualInputFieldConfigId =
				StringUtils.isBlank(dto.getManualInputFieldConfigId()) ? UUID.randomUUID().toString()
						: dto.getManualInputFieldConfigId();
		manualInputFieldConfig.setManualInputFieldConfigId(manualInputFieldConfigId);
		manualInputFieldConfig.setFieldName(dto.getFieldName());
		manualInputFieldConfig.setFieldVariableName(dto.getFieldVariableName());
		manualInputFieldConfig.setFileTemplateId(dto.getFileTemplateId());
		manualInputFieldConfig.setFieldType(dto.getFieldType());
		manualInputFieldConfig.setQuerySql(dto.getQuerySql());
		manualInputFieldConfig.setFormatRule(dto.getFormatRule());
		manualInputFieldConfig.setUppercaseAmountVariableName(dto.getUppercaseAmountVariableName());
		manualInputFieldConfig.setDefaultValue(dto.getDefaultValue());
		manualInputFieldConfig.setRelatedFieldId(null);
		manualInputFieldConfig.setCheckRegex(dto.getCheckRegex());
		manualInputFieldConfig.setIsRequired(dto.getIsRequired());
		manualInputFieldConfig.setDisplayOrder(dto.getDisplayOrder());
		manualInputFieldConfig.setRemark(dto.getRemark());
		return manualInputFieldConfig;
	}

}
