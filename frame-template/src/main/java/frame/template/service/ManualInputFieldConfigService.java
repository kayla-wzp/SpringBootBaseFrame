package frame.template.service;

import asset.common.util.collection.CollectionUtil;
import asset.common.validate.Validator;
import cffs.core.constant.db.SeqTimeConst;
import cffs.core.constant.errcode.ErrCodeTsign;
import cffs.core.service.SeqService;
import cffs.dao.dynamictemplate.DynamicFileTemplateDao;
import cffs.dao.dynamictemplate.ManualInputFieldConfigDao;
import cffs.manage.dto.dynamictemplate.ManualInputFieldConfigDeleteDto;
import cffs.manage.dto.dynamictemplate.ManualInputFieldConfigDto;
import cffs.po.dynamictemplate.ManualInputFieldConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManualInputFieldConfigService {
	@Resource
	private ManualInputFieldConfigDao manualInputFieldConfigDao;
	@Resource
	private DynamicFileTemplateDao dynamicFileTemplateDao;
	@Resource
	private SeqService seqService;

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
		Validator.assertNotNull(dynamicFileTemplateDao.selectByPrimaryKey(dto.getFileTemplateId()),
				ErrCodeTsign.VALADATE_NOT_PASS, "该模板不存在");
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
			Validator.assertTrue(CollectionUtil.isEmpty(sameFieldVariableNameConfigs), ErrCodeTsign.VALADATE_NOT_PASS,
					"该配置参数已存在");
		}
	}

	private ManualInputFieldConfig buildManualInputFieldConfig(ManualInputFieldConfigDto dto) {
		ManualInputFieldConfig manualInputFieldConfig = new ManualInputFieldConfig();
		String manualInputFieldConfigId = StringUtils.isBlank(dto.getManualInputFieldConfigId())
				? seqService.getSeq(SeqTimeConst.SEQ_MANUAL_INPUT_FIELD_CONFIG)
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
