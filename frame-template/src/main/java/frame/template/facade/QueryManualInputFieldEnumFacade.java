package frame.template.facade;

import cffs.manage.query.frame.facade.QueryFacade;
import cffs.manage.query.frame.vo.QueryRes;
import cffs.manage.query.service.dynamictemplate.QueryManualInputFieldEnumService;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListReq;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryManualInputFieldEnumFacade {
	@Resource
	private QueryManualInputFieldEnumService queryManualInputFieldEnumService;

	public QueryRes<QueryManualInputFieldEnumListRes> queryManualInputFieldEnumList(
			QueryManualInputFieldEnumListReq req) {
		List<QueryManualInputFieldEnumListRes> res =
				queryManualInputFieldEnumService.queryManualInputFieldEnumList(req);
		return super.buildRes(req, res);
	}
}
