package frame.template.service;

import frame.template.dao.QueryDynamicFileTemplateDao;
import frame.template.vo.reqres.QueryDynamicFileTemplateReq;
import frame.template.vo.reqres.QueryDynamicFileTemplateRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryDynamicFileTemplateService {
	@Resource
	private QueryDynamicFileTemplateDao queryDynamicFileTemplateDao;

	public List<QueryDynamicFileTemplateRes> QueryDynamicFileTemplateList(QueryDynamicFileTemplateReq req) {
		return queryDynamicFileTemplateDao.QueryDynamicFileTemplateList(req);
	}
}
