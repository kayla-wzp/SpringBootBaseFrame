package frame.template.facade;

import frame.template.service.QueryBusinessDataEchoConfigService;
import frame.template.service.QueryDynamicFileTemplateService;
import frame.template.service.QueryManualInputFieldConfigService;
import frame.template.service.QueryManualInputFieldEnumService;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListReq;
import frame.template.vo.reqres.QueryBusinessDataEchoConfigListRes;
import frame.template.vo.reqres.QueryDynamicFileTemplateReq;
import frame.template.vo.reqres.QueryDynamicFileTemplateRes;
import frame.template.vo.reqres.QueryManualInputFieldConfigListReq;
import frame.template.vo.reqres.QueryManualInputFieldConfigListRes;
import frame.template.vo.reqres.QueryManualInputFieldEnumListReq;
import frame.template.vo.reqres.QueryManualInputFieldEnumListRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryTemplateConfigFacade {
	@Resource
	private QueryBusinessDataEchoConfigService queryBusinessDataEchoConfigService;
	@Resource
	private QueryManualInputFieldEnumService queryManualInputFieldEnumService;
	@Resource
	private QueryDynamicFileTemplateService queryDynamicFileTemplateService;
	@Resource
	private QueryManualInputFieldConfigService queryManualInputFieldConfigService;

	public List<QueryBusinessDataEchoConfigListRes> queryBusinessDataEchoConfigList(
			QueryBusinessDataEchoConfigListReq req) {
		return queryBusinessDataEchoConfigService.queryBusinessDataEchoConfigList(req);
	}

	public List<QueryDynamicFileTemplateRes> queryDynamicFileTemplateList(QueryDynamicFileTemplateReq req) {
		return queryDynamicFileTemplateService.QueryDynamicFileTemplateList(req);
	}

	public List<QueryManualInputFieldConfigListRes> queryManualInputFieldConfigList(
			QueryManualInputFieldConfigListReq req) {
		return queryManualInputFieldConfigService.queryManualInputFieldConfigList(req);
	}

	public QueryManualInputFieldConfigListRes querySingleManualInputFieldConfig(QueryManualInputFieldEnumListReq req) {
		return queryManualInputFieldConfigService.querySingleManualInputFieldConfig(req);
	}

	public List<QueryManualInputFieldEnumListRes> queryManualInputFieldEnumList(QueryManualInputFieldEnumListReq req) {
		return queryManualInputFieldEnumService.queryManualInputFieldEnumList(req);
	}
}
