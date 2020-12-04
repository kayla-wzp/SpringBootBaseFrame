package frame.template.service;

import asset.frame.util.PageUtil;
import cffs.manage.query.dao.dynamictemplate.QueryManualInputFieldConfigDao;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldConfigListReq;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldConfigListRes;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryManualInputFieldConfigService {
	@Resource
	private QueryManualInputFieldConfigDao queryManualInputFieldConfigDao;

	public List<QueryManualInputFieldConfigListRes> queryManualInputFieldConfigList(
			QueryManualInputFieldConfigListReq req) {
		PageUtil.setPaging(req.getCurrentPage(), req.getPageSize());
		return queryManualInputFieldConfigDao.queryManualInputFieldConfigList(req);
	}

	public QueryManualInputFieldConfigListRes querySingleManualInputFieldConfig(QueryManualInputFieldEnumListReq req) {
		return queryManualInputFieldConfigDao.querySingleManualInputFieldConfig(req.getManualInputFieldConfigId());
	}
}
