package frame.template.service;


import asset.common.util.collection.CollectionUtil;
import asset.common.validate.Validator;
import cffs.core.constant.db.SeqTimeConst;
import cffs.core.constant.dynamictemplate.ManualInputFieldTypeConst;
import cffs.core.constant.errcode.ErrCodeTsign;
import cffs.core.service.SeqService;
import cffs.dao.dynamictemplate.ManualInputFieldConfigDao;
import cffs.dao.dynamictemplate.ManualInputFieldEnumDao;
import cffs.manage.dto.dynamictemplate.ManualInputFieldEnumDto;
import cffs.po.dynamictemplate.ManualInputFieldConfig;
import cffs.po.dynamictemplate.ManualInputFieldEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManualInputFieldEnumService {
	@Resource
	private ManualInputFieldEnumDao manualInputFieldEnumDao;
	@Resource
	private ManualInputFieldConfigDao manualInputFieldConfigDao;
	@Resource
	private SeqService seqService;

	public void addManualInputFieldEnum(ManualInputFieldEnumDto dto) {
		validateManualInputFieldConfig(dto);
		manualInputFieldEnumDao.deleteByManualInputFieldConfigId(dto.getManualInputFieldConfigId());
		List<ManualInputFieldEnum> manualInputFieldEnumList = dto.getEnumList();
		if (CollectionUtil.isNotEmpty(manualInputFieldEnumList)) {
			manualInputFieldEnumList.forEach(manualInputFieldEnum -> {
				manualInputFieldEnum.setId(seqService.getSeq(SeqTimeConst.SEQ_MANUAL_INPUT_FIELD_ENUM));
				manualInputFieldEnum.setManualInputFieldConfigId(dto.getManualInputFieldConfigId());
			});
		}
		manualInputFieldEnumDao.batchInsert(manualInputFieldEnumList);
	}

	private void validateManualInputFieldConfig(ManualInputFieldEnumDto dto) {
		ManualInputFieldConfig manualInputFieldConfig =
				manualInputFieldConfigDao.selectByPrimaryKey(dto.getManualInputFieldConfigId());
		Validator.assertNotNull(manualInputFieldConfig, ErrCodeTsign.VALADATE_NOT_PASS, "该配置不存在");
		if (!ManualInputFieldTypeConst.ENUM_SELECT.equals(manualInputFieldConfig.getFieldType())) {
			Validator.assertFalse(true, ErrCodeTsign.VALADATE_NOT_PASS, "该配置不可以配置枚举");
		}
	}
}
