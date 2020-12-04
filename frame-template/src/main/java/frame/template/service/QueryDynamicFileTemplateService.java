package frame.template.service;

import asset.frame.util.PageUtil;
import cffs.manage.query.dao.dynamictemplate.QueryDynamicFileTemplateDao;
import cffs.manage.query.vo.dynamictemplate.QueryDynamicFileTemplateReq;
import cffs.manage.query.vo.dynamictemplate.QueryDynamicFileTemplateRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QueryDynamicFileTemplateService {
	@Resource
	private QueryDynamicFileTemplateDao queryDynamicFileTemplateDao;

	public List<QueryDynamicFileTemplateRes> QueryDynamicFileTemplateList(QueryDynamicFileTemplateReq req) {
        PageUtil.setPaging(req.getCurrentPage(), req.getPageSize());
		return queryDynamicFileTemplateDao.QueryDynamicFileTemplateList(req);
	}
}
