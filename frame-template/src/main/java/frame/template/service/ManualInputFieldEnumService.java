package frame.template.service;

import frame.template.common.ManualInputFieldTypeConst;
import frame.template.common.util.CollectionUtil;
import frame.template.dao.ManualInputFieldConfigDao;
import frame.template.dao.ManualInputFieldEnumDao;
import frame.template.vo.dto.ManualInputFieldEnumDto;
import frame.template.vo.ManualInputFieldConfig;
import frame.template.vo.ManualInputFieldEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ManualInputFieldEnumService {
	@Resource
	private ManualInputFieldEnumDao manualInputFieldEnumDao;
	@Resource
	private ManualInputFieldConfigDao manualInputFieldConfigDao;

	public void addManualInputFieldEnum(ManualInputFieldEnumDto dto) {
		validateManualInputFieldConfig(dto);
		manualInputFieldEnumDao.deleteByManualInputFieldConfigId(dto.getManualInputFieldConfigId());
		List<ManualInputFieldEnum> manualInputFieldEnumList = dto.getEnumList();
		if (CollectionUtil.isNotEmpty(manualInputFieldEnumList)) {
			manualInputFieldEnumList.forEach(manualInputFieldEnum -> {
				manualInputFieldEnum.setId(UUID.randomUUID().toString());
				manualInputFieldEnum.setManualInputFieldConfigId(dto.getManualInputFieldConfigId());
			});
		}
		manualInputFieldEnumDao.batchInsert(manualInputFieldEnumList);
	}

	private void validateManualInputFieldConfig(ManualInputFieldEnumDto dto) {
		ManualInputFieldConfig manualInputFieldConfig =
				manualInputFieldConfigDao.selectByPrimaryKey(dto.getManualInputFieldConfigId());
		log.debug("校验该配置是否存在");
		if (!ManualInputFieldTypeConst.ENUM_SELECT.equals(manualInputFieldConfig.getFieldType())) {
			log.debug("该配置不可以配置枚举");
		}
	}
}
