package frame.template.service;

import frame.template.vo.dto.DeleteFileTemplateDto;
import frame.template.vo.dto.DynamicFileTemplateDto;
import frame.template.dao.DynamicFileTemplateDao;
import frame.template.vo.DynamicFileTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class DynamicFileTemplateService {

	@Resource
	private DynamicFileTemplateDao dynamicFileTemplateDao;

	private static final String SAVE = "1";
	private static final String UPDATE = "2";

	@Transactional(rollbackFor = Exception.class)
	public void saveDynamicFileTemplate(DynamicFileTemplateDto dto) {
		validateFileId(dto);
		if (dto.getFileTemplateId() == null) {
			saveFile(dto, SAVE);
		} else {
			updateFile(dto);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteDynamicFileTemplate(DeleteFileTemplateDto dto) {
		validateFileTemplateId(dto);
		dynamicFileTemplateDao.deleteByPrimaryKey(dto.getFileTemplateId());
	}

	private void validateFileId(DynamicFileTemplateDto dto) {
		log.debug("У���ϴ����ļ�����Ϊ��");
	}

	private void saveFile(DynamicFileTemplateDto dto, String saveOrUpdate) {
		buildDynamicFileTemplate(dto, saveOrUpdate);
	}

	private void updateFile(DynamicFileTemplateDto dto) {
		buildDynamicFileTemplate(dto, UPDATE);
	}

	private void validateFileTemplateId(DeleteFileTemplateDto dto) {
		log.debug("ģ���ļ�����Ϊ��");
	}

	private void buildDynamicFileTemplate(DynamicFileTemplateDto dto, String saveOrUpdate) {
		DynamicFileTemplate fileTemplate = new DynamicFileTemplate();
		// TODO: 2021/1/4 ��ȡģ���ļ�
		fileTemplate.setFileTemplateId(dto.getFileTemplateId());
		fileTemplate.setFileId(dto.getFileId());
		fileTemplate.setFileType(dto.getFileType());
		fileTemplate.setFileExtension("");
		fileTemplate.setBusinessType(dto.getBusinessType());
		fileTemplate.setFileTemplateName(dto.getFileTemplateName());
		if (SAVE.equals(saveOrUpdate)) {
			fileTemplate.setFileTemplateId(UUID.randomUUID().toString());
			fileTemplate.setCreateTime(new Date());
			fileTemplate.setLastUpdateTime(new Date());
			dynamicFileTemplateDao.insertSelective(fileTemplate);
		} else {
			fileTemplate.setLastUpdateTime(new Date());
			dynamicFileTemplateDao.updateByPrimaryKeySelective(fileTemplate);
		}
	}

}
