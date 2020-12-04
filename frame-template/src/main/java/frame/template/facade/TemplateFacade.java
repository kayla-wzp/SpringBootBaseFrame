package frame.template.facade;

import frame.template.common.ExportRes;
import frame.template.common.HttpContentTypeConst;
import frame.template.service.DynamicTemplateApiService;
import frame.template.vo.TemplateSaveDto;
import frame.template.vo.reqres.TemplateHtmlViewRes;
import frame.template.vo.reqres.TemplateReq;
import frame.template.vo.reqres.TemplateSaveReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;


@Service
public class TemplateFacade {
	@Resource
	private DynamicTemplateApiService dynamicTemplateApiService;
	public static final String TEST_CONTRACT_TEMPLATE_PATH = "/config/template/dynamictemplate/test_contract_template";

	private static final String PDF = "pdf";
	private static final String WORD = "doc";

	public TemplateHtmlViewRes getHtmlViewStr(TemplateReq req) {
		TemplateHtmlViewRes res = new TemplateHtmlViewRes();
		InputStream inputStream =
				this.getClass().getResourceAsStream(TEST_CONTRACT_TEMPLATE_PATH + req.getTemplateId() + ".html");

		String templateId = req.getTemplateId();
		String businessId = req.getBusinessId();
		HashMap<String, Object> enterParamMap = new HashMap<>();
		enterParamMap.put("factoringReqId", req.getBusinessId());
		enterParamMap.put("socialCreditCode", "9144030034270389XD");
		String htmlViewStr = dynamicTemplateApiService.getHtmlViewStr(inputStream, businessId, templateId, enterParamMap);
		res.setHtmlViewStr(htmlViewStr);
		return res;
	}

	public void saveFormDate(TemplateSaveReq req) {
		TemplateSaveDto dto = covertToDto(req);
		dynamicTemplateApiService.saveFormData(dto);
	}

	private TemplateSaveDto covertToDto(TemplateSaveReq req) {
		TemplateSaveDto dto = new TemplateSaveDto();
//		TransformUtil.fillBaseField(req, dto);
		dto.setTemplateId(req.getTemplateId());
		dto.setBusinessId(req.getBusinessId());
		dto.setParamStr(req.getParamStr());
		return dto;
	}

	public ExportRes generateContract(TemplateReq req) {
		InputStream inputStream = null;
		inputStream = this.getClass().getResourceAsStream(TEST_CONTRACT_TEMPLATE_PATH + req.getTemplateId() + ".html");
		String templateId = req.getTemplateId();
		String businessId = req.getBusinessId();
		HashMap<String, Object> enterParamMap = new HashMap<>();
		enterParamMap.put("factoringReqId", businessId);
		enterParamMap.put("socialCreditCode", "9144030034270389XD");
		InputStream resultInputStream =
				dynamicTemplateApiService.generateContract(inputStream, businessId, templateId, enterParamMap);
		return buildExportRes(resultInputStream, "file.pdf");
	}

	private ExportRes buildExportRes(InputStream inputStream, String fileName) {
		ExportRes exportRes = new ExportRes();
		exportRes.setCharset("utf-8");
		exportRes.setContentType(getContentType(fileName));
		exportRes.setFileName(fileName);
		exportRes.setInputStream(inputStream);
		return exportRes;
	}

	private String getContentType(String fileName) {
		int i = fileName.lastIndexOf(".");
		String extention = fileName.substring(i + 1);
		switch (extention) {
			case PDF:
				return HttpContentTypeConst.PDF;
			case WORD:
				return HttpContentTypeConst.WORD;
			default:
				return HttpContentTypeConst.STREAM;
		}
	}
}

