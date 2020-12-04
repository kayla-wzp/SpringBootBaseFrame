package frame.template.service;

import asset.common.file.common.errcode.ErrCodeFile;
import asset.common.util.date.TimeProvider;
import asset.common.validate.Validator;
import asset.frame.util.file.FileUtil;
import cffs.core.constant.db.SeqNormalConst;
import cffs.core.constant.errcode.ErrCodeCommon;
import cffs.core.service.SeqService;
import cffs.dao.dynamictemplate.DynamicFileTemplateDao;
import cffs.dao.resource.UploadFileDao;
import cffs.manage.dto.dynamictemplate.DeleteFileTemplateDto;
import cffs.manage.dto.dynamictemplate.DynamicFileTemplateDto;
import cffs.po.dynamictemplate.DynamicFileTemplate;
import cffs.po.resource.UploadFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DynamicFileTemplateService {

	@Resource
	private DynamicFileTemplateDao dynamicFileTemplateDao;
	@Resource
	private UploadFileDao uploadFileDao;
	@Resource
	private SeqService seqService;
	@Resource
	private TimeProvider timeProvider;

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
		Validator.assertNotNull(uploadFileDao.selectById(dto.getFileId()), ErrCodeCommon.CO_RECORD_IS_NOT_EXIST,
				"上传的文件");
	}

	private void saveFile(DynamicFileTemplateDto dto, String saveOrUpdate) {
		buildDynamicFileTemplate(dto, saveOrUpdate);
	}

	private void updateFile(DynamicFileTemplateDto dto) {
		buildDynamicFileTemplate(dto, UPDATE);
	}

	private void validateFileTemplateId(DeleteFileTemplateDto dto) {
		Validator.assertNotNull(dynamicFileTemplateDao.selectByPrimaryKey(dto.getFileTemplateId()),
				ErrCodeCommon.CO_RECORD_IS_NOT_EXIST, "模板文件ID");
	}

	private void buildDynamicFileTemplate(DynamicFileTemplateDto dto, String saveOrUpdate) {
		DynamicFileTemplate fileTemplate = new DynamicFileTemplate();
		UploadFile uploadFile = uploadFileDao.selectById(dto.getFileId());
		validateFileFormat(uploadFile);
		fileTemplate.setFileTemplateId(dto.getFileTemplateId());
		fileTemplate.setFileId(dto.getFileId());
		fileTemplate.setFileType(dto.getFileType());
		fileTemplate.setFileExtension(uploadFile.getFileExtension());
		fileTemplate.setBusinessType(dto.getBusinessType());
		fileTemplate.setFileTemplateName(dto.getFileTemplateName());
		if (SAVE.equals(saveOrUpdate)) {
			fileTemplate.setFileTemplateId(seqService.getSeq(SeqNormalConst.SEQ_DYNAMIC_FILE_TEMPLATE));
			fileTemplate.setCreateTime(timeProvider.getCurrentTime());
			fileTemplate.setLastUpdateTime(timeProvider.getCurrentTime());
			dynamicFileTemplateDao.insertSelective(fileTemplate);
		} else {
			fileTemplate.setLastUpdateTime(timeProvider.getCurrentTime());
			dynamicFileTemplateDao.updateByPrimaryKeySelective(fileTemplate);
		}
	}

	private void validateFileFormat(UploadFile uploadFile) {
		Validator.assertTrue(FileUtil.SUFFIX_HTML.equals(uploadFile.getFileExtension()),
				ErrCodeFile.CO_FILE_FORMAT_ERROR);
	}
}
