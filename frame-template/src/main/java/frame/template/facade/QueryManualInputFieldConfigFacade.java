package frame.template.facade;

import cffs.manage.query.frame.facade.QueryFacade;
import cffs.manage.query.frame.vo.QueryRes;
import cffs.manage.query.service.dynamictemplate.QueryManualInputFieldConfigService;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldConfigListReq;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldConfigListRes;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryManualInputFieldConfigFacade {
	@Resource
	private QueryManualInputFieldConfigService queryManualInputFieldConfigService;

	public QueryRes<QueryManualInputFieldConfigListRes> queryManualInputFieldConfigList(
			QueryManualInputFieldConfigListReq req) {
		List<QueryManualInputFieldConfigListRes> res =
				queryManualInputFieldConfigService.queryManualInputFieldConfigList(req);
		return super.buildRes(req, res);
	}

	public QueryRes<QueryManualInputFieldConfigListRes> querySingleManualInputFieldConfig(
			QueryManualInputFieldEnumListReq req) {
		QueryManualInputFieldConfigListRes res =
				queryManualInputFieldConfigService.querySingleManualInputFieldConfig(req);
		return super.buildRes(req, res);
	}
}
