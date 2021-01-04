package frame.template.service;

import frame.template.dao.QueryManualInputFieldEnumDao;
import frame.template.vo.reqres.QueryManualInputFieldEnumListReq;
import frame.template.vo.reqres.QueryManualInputFieldEnumListRes;
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
