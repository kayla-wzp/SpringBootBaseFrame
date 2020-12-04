package frame.template.service;

import cffs.manage.query.dao.dynamictemplate.QueryManualInputFieldEnumDao;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListReq;
import cffs.manage.query.vo.dynamictemplate.QueryManualInputFieldEnumListRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryManualInputFieldEnumService {
	@Resource
	private QueryManualInputFieldEnumDao queryManualInputFieldEnumDao;

	public List<QueryManualInputFieldEnumListRes> queryManualInputFieldEnumList(QueryManualInputFieldEnumListReq req) {
		return queryManualInputFieldEnumDao.queryManualInputFieldEnumList(req.getManualInputFieldConfigId());
	}

}
