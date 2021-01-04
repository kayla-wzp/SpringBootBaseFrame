package frame.template.service;

import frame.template.dao.QueryManualInputFieldConfigDao;
import frame.template.vo.reqres.QueryManualInputFieldConfigListReq;
import frame.template.vo.reqres.QueryManualInputFieldConfigListRes;
import frame.template.vo.reqres.QueryManualInputFieldEnumListReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryManualInputFieldConfigService {
	@Resource
	private QueryManualInputFieldConfigDao queryManualInputFieldConfigDao;

	public List<QueryManualInputFieldConfigListRes> queryManualInputFieldConfigList(
			QueryManualInputFieldConfigListReq req) {
		return queryManualInputFieldConfigDao.queryManualInputFieldConfigList(req);
	}

	public QueryManualInputFieldConfigListRes querySingleManualInputFieldConfig(QueryManualInputFieldEnumListReq req) {
		return queryManualInputFieldConfigDao.querySingleManualInputFieldConfig(req.getManualInputFieldConfigId());
	}
}
